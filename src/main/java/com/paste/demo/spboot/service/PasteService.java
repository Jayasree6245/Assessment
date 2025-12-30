package com.paste.demo.spboot.service;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paste.demo.spboot.model.Paste;
import com.paste.demo.spboot.repository.PasteRepository;

@Service
public class PasteService {
	private final PasteRepository repository;
	private final DatabaseReference db=
			FirebaseDatabase.getInstance().getReference("pastes");
	public PasteService(PasteRepository repository) {
		this.repository=repository;
	}
	public Paste createPaste(String content, Integer expiresInMinutes, Integer maxViews) {
		Paste paste=new Paste();
		paste.setId(UUID.randomUUID().toString().substring(0, 8));
		paste.setContent(content);
		paste.setMaxViews(maxViews);
		if(expiresInMinutes!=null) {
			paste.setExpiresAt(
			System.currentTimeMillis()+expiresInMinutes*60*1000
			);
		}
		db.child(paste.getId()).setValueAsync(paste);
		return paste;
	}
	public CompletableFuture<Paste> getPaste(String id){
		CompletableFuture<Paste> future= new CompletableFuture<>();
		db.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
			
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				if(!snapshot.exists()) {
					future.complete(null);
					return;
				}
				Paste paste=snapshot.getValue(Paste.class);
				long now=System.currentTimeMillis();
				boolean expired=
						(paste.getExpiresAt()!=null && now>paste.getExpiresAt()) ||
						(paste.getMaxViews()!=null &&
						paste.getViewCount() >= paste.getMaxViews());
				if(expired) {
					future.complete(null);
					return;
				}
				paste.setViewCount(paste.getViewCount()+1);
				db.child(id).setValueAsync(paste);
				future.complete(paste);
				
			}
			
			@Override
			public void onCancelled(DatabaseError error) {
				future.complete(null);
				
			}
		});
		return future;
	}
}

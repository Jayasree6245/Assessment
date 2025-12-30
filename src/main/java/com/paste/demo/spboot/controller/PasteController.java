package com.paste.demo.spboot.controller;

import java.util.UUID;
import org.springframework.web.bind.annotation.*;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.paste.demo.spboot.model.Paste;
import com.paste.demo.spboot.repository.PasteRepository;


@RestController
@RequestMapping("/paste")
public class PasteController {
	
    private final PasteRepository repo;
    
	public PasteController( PasteRepository repo) {
		
		this.repo = repo;
		}
	
	@PostMapping
	public String createPaste(@RequestBody String content) {
		String pasteId=UUID.randomUUID().toString();
		DatabaseReference ref=FirebaseDatabase.getInstance()
				.getReference("pastes")
				.child(pasteId);
		ref.setValueAsync(content);
		Paste paste=new Paste();
		paste.setId(pasteId);
		paste.setMaxViews(0);
		repo.save(paste);
		return pasteId;
		
	}
	@GetMapping("/{id}")
	public String getPaste(@PathVariable String id) {
		Paste paste=repo.findById(id).orElseThrow();
		paste.setMaxViews(paste.getMaxViews()+1);
		repo.save(paste);
		return "Paste fetched. View count updated.";
	}
	
}

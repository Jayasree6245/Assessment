package com.paste.demo.spboot.config;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import jakarta.annotation.PostConstruct;

@Configuration
public class FirebaseConfig {
	@Value("${firebase.database-url}")
	private String databasrUrl;
	@Value("${firebase.config.path}")
	private Resource serviceAccount;
	@PostConstruct
	public void init() {
		try {
			InputStream serviceAccountStream=serviceAccount.getInputStream();
			FirebaseOptions options=FirebaseOptions.builder()
					 .setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
					  .setDatabaseUrl(databasrUrl)
					  .build();
			if(FirebaseApp.getApps().isEmpty()) {
				FirebaseApp.initializeApp(options);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

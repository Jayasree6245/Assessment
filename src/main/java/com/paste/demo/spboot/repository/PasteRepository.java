package com.paste.demo.spboot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paste.demo.spboot.model.Paste;

@Repository
public interface PasteRepository extends JpaRepository<Paste, String> {
	Optional<Paste> findById(String id);

}

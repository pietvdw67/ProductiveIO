package com.infinity.ProductiveIO.note.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.infinity.ProductiveIO.note.model.NoteItem;
import com.infinity.ProductiveIO.note.repository.NoteRepository;

@RestController
public class NoteResource {
	
	@Autowired
	NoteRepository repository;
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/note/v1/{id}")
	public NoteItem getNoteById(@PathVariable String id) {
		
		return repository.findById(Long.parseLong(id)).orElse(null);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/note/v1")
	public NoteItem addNote(@RequestBody NoteItem noteItem) {
		
		return repository.save(noteItem);
		
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping("/note/v1/{id}")
	public void delteNote(@PathVariable String id) {
		
		repository.deleteById(Long.parseLong(id));
		
	}

}

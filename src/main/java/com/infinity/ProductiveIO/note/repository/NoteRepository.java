package com.infinity.ProductiveIO.note.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infinity.ProductiveIO.note.model.NoteItem;

public interface NoteRepository extends JpaRepository<NoteItem,Long>{

}

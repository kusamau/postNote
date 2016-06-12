package org.postnote.service;

import java.util.Calendar;
import java.util.List;

import org.postnote.entity.PostNote;
import org.postnote.exception.repository.CannotInsertException;
import org.postnote.exception.repository.NotExistException;
import org.postnote.repository.PostNoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service to access the PostNoteRepository (that is plays the business logic role)
 *
 * @version 1.0 
 * @since 2014-06-12
 * 
 * @author Maurizio Nagni
 **/
@Service
public class PostNoteService {

	@Autowired
	private PostNoteRepository postnoteRepo;

	/**
	 * Retrieves all the notes in the database
	 * @return a List of PostNote 
	 **/
	public List<PostNote> findAllNotes() {
		return postnoteRepo.findAll();
	}

	/**
	 * Deletes all the notes in the database 
	 **/
	public void deleteAllNotes() {
		postnoteRepo.deleteAll();
	}
	
	/**
	 * Verifies if a PostNote exists
	 * @param id the noteId find.
	 * @return true if the post exists, false otherwise.
	 **/
	public boolean noteExists(Long id) {
		return postnoteRepo.exists(id);
	}
	
	
	/**
	 * Delete one note in the database
	 * @param id the noteId to delete.
	 * @throws NotExistException if the note to delete does not exists 
	 **/
	public void deleteNote(Long id) throws NotExistException {
		if (!noteExists(id)) {
			throw new NotExistException(String.format("Cannot delete post: $1%s as it does not exist", id));
		}
		postnoteRepo.delete(id);
	}
	
	/**
	 * Finds one note from the database
	 * @param id the noteId to find.
	 * @return a PostNote
	 * @throws NotExistException if the note to delete does not exists 
	 **/
	public PostNote findNote(Long id) throws NotExistException {
		PostNote note = postnoteRepo.findOne(id);
		if (note == null) {
			throw new NotExistException("Cannot find the required Post");
		}
		return note;
	}
	
	/**
	 * Inserts a new PostNote in the database
	 * @param note The PostNode to insert
	 * @return the inserted PostNote with filled PostNote.id 
	 * @throws CannotInsertException if <pre>note</pre> exists 
	 **/
	public PostNote insertNote(PostNote note) throws CannotInsertException {
		/* PostNote must have not pre-defined ID */
		if (note.getId() != null) {
			throw new CannotInsertException("Cannot insert as it already exists.");
		}
		if (note.getCreationDate() == null) {
			note.setCreationDate(Calendar.getInstance().getTime());
		}
		return saveNote(note);
	}
	
	/**
	 * Updates a new PostNote in the database.
	 * @param note The PostNode to update
	 * @return the updated PostNote 
	 * @throws NotExistException if <pre>note</pre> does not exist 
	 **/
	public PostNote updateNote(PostNote note) throws NotExistException {
		/* Postnote must exists */
		if (note.getId() == null || !postnoteRepo.exists(note.getId())) {
			throw new NotExistException("Cannot update as it does not exist.");
		}
		
		return saveNote(note);
	}
	
	/**
	 * Inserts a new PostNote in the database
	 * @param note The PostNode to insert
	 * @return the inserted PostNote with filled PostNote.id 
	 **/
	private PostNote saveNote(PostNote note) {
		return postnoteRepo.save(note);
	}
}

package org.postnote.controller;

import java.util.List;

import org.postnote.PostNoteApplication;
import org.postnote.entity.PostNote;
import org.postnote.exception.repository.CannotInsertException;
import org.postnote.exception.repository.CannotUpdateException;
import org.postnote.exception.repository.NotExistException;
import org.postnote.service.PostNoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes a REST interface to access the PostNote applicaition (plays a Controller role)
 * to create, retrieve, update or delete {@link PostNote} items.
 * 
 * @version 1.0 
 * @since 2014-06-12
 * 
 * @author Maurizio Nagni
 **/
@RestController
@SpringApplicationConfiguration(classes = PostNoteApplication.class)
@CrossOrigin(origins = "*")
public class PostNoteRestController {

	final static Logger logger = LoggerFactory.getLogger(PostNoteRestController.class);

	@Autowired
	private PostNoteService service;

	/**
	 * Retrieves all the notes
	 * @return a list of all the existing postnotes
	 **/
	@RequestMapping(value = "/notes", method = RequestMethod.GET)
	public List<PostNote> getAllNotes() {
		// putNote(createPost());
		logger.debug(String.format("Retrieved %1$s notes", service.findAllNotes().size()));
		return service.findAllNotes();
	}

	/**
	 * Finds one note.
	 * @param id the note id tp retreive.
	 * @return the created {@link PostNote}
	 * @throws NotExistException if the note does not exist
	 **/
	@RequestMapping(value = "/notes/{id}", method = RequestMethod.GET)
	public PostNote findNote(@PathVariable Long id) throws NotExistException {
		logger.debug(String.format("Finding note id:%1$s", id));
		return service.findNote(id);
	}

	
	/**
	 * Inserts a new note in the database.
	 * @param note a note to save.
	 * @return the newly inserted note filled with <pre>id</pre>
	 * @throws CannotInsertException if note <pre>id</pre> already exist
	 **/
	@RequestMapping(value = "/notes", method = RequestMethod.POST)
	public PostNote insertNote(@RequestBody PostNote note) throws CannotInsertException {
		logger.debug(String.format("Inserting note %1$s", note));
		return service.insertNote(note);
	}

	/**
	 * Updates an existing note in the database. 
	 * @param note a PostNote to update.
	 * @return a Postnote
	 * @throws NotExistException if <pre>note</pre> does not exist.
	 **/
	@RequestMapping(value = "/notes", method = RequestMethod.PUT)
	@ResponseStatus( HttpStatus.OK )
	public PostNote updateNote(@RequestBody PostNote note) throws NotExistException {
		logger.debug(String.format("Updating note id:%1$s", note.getId()));
		return service.updateNote(note);
	}

	/**
	 * Delete one note
	 * @param id The PostNote id to delete
	 * @throws NotExistException if note <pre>id</pre> does not exist
	 **/
	@RequestMapping(value = "/notes/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.OK)
	public void deleteNote(@PathVariable Long id) throws NotExistException {
		logger.debug(String.format("Deleting note id:%1$s", id));
		service.deleteNote(id);
	}

	/** 
	 * Intercepts a {@link CannotUpdateException} and {@link CannotInsertException} setting HTTPResponse.status to HttpStatus.CONFLICT Status
	 * @param e the thrown exception
	 **/
	@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Data integrity violation") // 409
	@ExceptionHandler({CannotUpdateException.class, CannotInsertException.class})
	public void conflict(CannotUpdateException e) {
		logger.warn(e.getMessage());
	}
	
	/** 
	 * Intercepts a {@link NotExistException} setting HTTPResponse.status to HttpStatus.NOT_FOUND Status
	 * @param e the thrown exception
	 **/
	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Item not Found") // 404
	@ExceptionHandler({NotExistException.class})
	public void conflict(NotExistException e) {
		logger.warn(e.getMessage());
	}
}

package org.postnote.test.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.postnote.entity.PostNote;
import org.postnote.exception.repository.CannotInsertException;
import org.postnote.exception.repository.NotExistException;
import org.postnote.service.PostNoteService;
import org.postnote.test.RepositoryConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonProcessingException;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@PropertySource("classpath:application-test.properties")
@SpringApplicationConfiguration(classes = { RepositoryConfiguration.class })
public class PostNoteServiceTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	final static Logger logger = LoggerFactory.getLogger(PostNoteServiceTest.class);

	@Autowired
	private PostNoteService noteService;

	@After
	public void cleanup() throws JsonProcessingException, IOException {
		noteService.deleteAllNotes();
	}

	@Before
	public void checkDBClean() throws Exception {

	}

	private PostNote createPostNote(Date date) {
		PostNote note = new PostNote();
		note.setNote("Test Note1");
		note.setColor("Yellow");
		note.setCreationDate(date);
		return note;
	}

	@Test
	public void testInsertGetNoteWithoutDate() throws CannotInsertException {
		PostNote note = createPostNote(null);
		note = noteService.insertNote(note);

		Assert.assertNotNull(note.getId());
		Assert.assertNotNull(note.getCreationDate());
		Assert.assertNotNull(note.getColor());
		Assert.assertNotNull(note.getNote());
	}

	@Test
	public void testInsertNoteWithDate() throws CannotInsertException {
		Date postDate = Calendar.getInstance().getTime();
		PostNote note = createPostNote(postDate);
		note = noteService.insertNote(note);

		Assert.assertEquals(postDate, note.getCreationDate());
	}

	@Test
	public void testInsertNoteTwice() throws CannotInsertException {
		Date postDate = Calendar.getInstance().getTime();
		PostNote note = createPostNote(postDate);
		note = noteService.insertNote(note);

		exception.expect(CannotInsertException.class);
		// Inserts again
		note = noteService.insertNote(note);
	}

	@Test
	public void testDeleteNoteNotExistingNote() throws NotExistException {
		exception.expect(NotExistException.class);
		noteService.deleteNote(123456L);
	}

	@Test
	public void testDeleteNoteExistingNote() {
		PostNote note = createPostNote(null);

		try {
			note = noteService.insertNote(note);
		} catch (CannotInsertException e1) {
			Assert.fail(e1.getMessage());
		}

		try {
			noteService.deleteNote(note.getId());
		} catch (NotExistException e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testUpdateNotExistingNote() throws NotExistException {
		PostNote note = createPostNote(null);
		note.setId(123456L);

		exception.expect(NotExistException.class);
		noteService.updateNote(note);
	}

	@Test
	public void testUpdateExistingNote() {
		PostNote note = createPostNote(null);
		try {
			note = noteService.insertNote(note);
		} catch (CannotInsertException e1) {
			Assert.fail(e1.getMessage());
		}

		try {
			String new_color = "Green";
			note.setColor(new_color);
			noteService.updateNote(note);

			Assert.assertEquals(new_color, noteService.findNote(note.getId()).getColor());
		} catch (NotExistException e) {
			Assert.fail(e.getMessage());
		}
	}
}

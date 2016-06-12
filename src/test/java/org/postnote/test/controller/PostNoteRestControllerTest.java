package org.postnote.test.controller;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.JAXBException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.postnote.entity.PostNote;
import org.postnote.exception.repository.CannotInsertException;
import org.postnote.test.RepositoryConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { RepositoryConfiguration.class })
@WebIntegrationTest("server.port:0")
public class PostNoteRestControllerTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	final static Logger logger = LoggerFactory.getLogger(PostNoteRestControllerTest.class);

	@Value("${local.server.port}")
	private int port;

	private URL base;
	private RestTemplate template;

	@After
	public void cleanup() {
		PostNote[] notes = template.getForObject(base + "/notes", PostNote[].class);
		for (PostNote note: notes) {
			template.delete(base + "/notes/" + note.getId());
		}
	}

	@Before
	public void checkDBClean() throws Exception {
		template = new TestRestTemplate();
		base = new URL("http://localhost:" + port);
	}

	private String createJsonPostNote(Date date) throws JAXBException, IOException {
		PostNote note = new PostNote();
		note.setNote("Test Note1");
		note.setColor("Yellow");
		note.setCreationDate(date);
		return ControllerTestHelper.marshall(note);
	}

	private HttpEntity<String> createHttpEntity(String jsonNote) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<String>(jsonNote, headers);
	}

	/**
	 * Utility method tested in
	 * {@link PostNoteRestControllerTest#testInsertNoteWithoutDate()} and
	 * {@link PostNoteRestControllerTest#testInsertNoteWithDate()}.
	 **/
	private PostNote insertNote(Date date) throws JAXBException, IOException {
		String jsonNote = createJsonPostNote(date);
		HttpEntity<String> entity = createHttpEntity(jsonNote);
		ResponseEntity<String> actual = template.postForEntity(base + "/notes", entity, String.class);
		Assert.assertEquals(HttpStatus.OK, actual.getStatusCode());
		PostNote postNote = (PostNote) ControllerTestHelper.unmarshall(PostNote.class, new StringReader(actual.getBody()));
		Assert.assertNotNull(postNote.getId());
		Assert.assertNotNull(postNote.getCreationDate());
		Assert.assertNotNull(postNote.getColor());
		Assert.assertNotNull(postNote.getNote());
		return postNote;
	}

	/**
	 * Utility method tested in
	 * {@link PostNoteRestControllerTest#testInsertNoteWithoutDate()}.
	 **/
	private PostNote getNote(Long id, HttpStatus responseStatus) throws JAXBException, IOException {
		ResponseEntity<String> actual = template.getForEntity(base + "/notes/" + id, String.class);
		Assert.assertEquals(responseStatus, actual.getStatusCode());
		// We are interested only when return something.
		if (responseStatus.equals(HttpStatus.OK)) {
			return (PostNote) ControllerTestHelper.unmarshall(PostNote.class, new StringReader(actual.getBody()));
		}
		return null;
	}

	@Test
	public void testInsertGetNoteWithoutDate() throws JAXBException, IOException {
		PostNote createdNote = insertNote(null);
		PostNote retrievedNote = getNote(createdNote.getId(), HttpStatus.OK);

		Assert.assertEquals(createdNote.getId(), retrievedNote.getId());
		Assert.assertEquals(createdNote.getCreationDate(), retrievedNote.getCreationDate());
		Assert.assertEquals(createdNote.getColor(), retrievedNote.getColor());
		Assert.assertEquals(createdNote.getNote(), retrievedNote.getNote());
	}

	@Test
	public void testInsertNoteWithDate() throws JAXBException, IOException {
		Date postDate = Calendar.getInstance().getTime();
		PostNote postNote = insertNote(postDate);

		Assert.assertEquals(postDate, postNote.getCreationDate());
	}

	@Test
	public void testDeleteNoteNotExistingNote() {
		template.delete(base + "/notes/123456");
	}

	@Test
	public void testDeleteNoteExistingNote() throws JAXBException, IOException {
		PostNote createdNote = insertNote(null);
		template.delete(base + "/notes/" + createdNote.getId());

		// Should return nothing
		getNote(createdNote.getId(), HttpStatus.NOT_FOUND);
	}

	@Test
	public void testUpdateNotExistingNote() throws JAXBException, IOException {
		PostNote note = new PostNote();
		note.setId(123456L);
		String jsonNote = ControllerTestHelper.marshall(note);
		HttpEntity<String> entity = createHttpEntity(jsonNote);
		template.put(base + "/notes", entity);
		getNote(note.getId(), HttpStatus.NOT_FOUND);
	}
	
	
	@Test
	public void testUpdateExistingNote() throws JAXBException, IOException {
		Date postDate = Calendar.getInstance().getTime();
		PostNote postNote = insertNote(postDate);
		String new_color = "Green";
		postNote.setColor(new_color);
		String jsonNote = ControllerTestHelper.marshall(postNote);
		HttpEntity<String> entity = createHttpEntity(jsonNote);
		template.put(base + "/notes", entity);
		
		PostNote updateNote = getNote(postNote.getId(), HttpStatus.OK);
		Assert.assertEquals(new_color, updateNote.getColor());
	}
}

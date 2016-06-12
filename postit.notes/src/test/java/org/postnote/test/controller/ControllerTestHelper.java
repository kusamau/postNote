package org.postnote.test.controller;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXBException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ControllerTestHelper {

	public static <T> String marshall(T element) throws JAXBException, IOException {
		Writer writer = new StringWriter();
		new ObjectMapper().writeValue(writer, element);
		writer.close();
		return writer.toString();
	}

	public static <T> Object unmarshall(Class<T> objectClass, Reader reader)
			throws JsonParseException, JsonMappingException, IOException {
		return (T) new ObjectMapper().readValue(reader, objectClass);
	}
}

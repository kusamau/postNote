package org.postnote.controller;

import java.util.List;
import java.util.Locale;

import org.postnote.PostNoteApplication;
import org.postnote.entity.DictionaryItem;
import org.postnote.service.DictionaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * Exposes a REST interface to access the PostNote dictionary applicaition (plays a Controller role)
 * to create, retrieve, update or delete {@link DictionaryItem} items.
 * 
 * @version 1.0 
 * @since 2014-06-12
 * 
 * @author Maurizio Nagni
 **/
@RestController
@SpringApplicationConfiguration(classes = PostNoteApplication.class)
@CrossOrigin(origins = "*")
public class I18NController {

	@Autowired
	DictionaryService service;
	
	final static Logger logger = LoggerFactory.getLogger(I18NController.class);

	/**
	 * Returns a Dictionary for the required Locale
	 * @param locale the dictionary to retrieve.
	 * @return a list of items for the required locale
	 **/
	@RequestMapping(value = "/i18n", method = RequestMethod.GET)
	public List<DictionaryItem> getDictionary(@RequestParam(value = "lang", defaultValue = "de") Locale locale) {
		logger.info(String.format("Requested locale: %1$s", locale));
		logger.debug(String.format("Retrieved %1$s items", service.getDictionary(locale).size()));
		return service.getDictionary(locale); //(new ArrayList<DictionaryItem>()); //
	}
}
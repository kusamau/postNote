package org.postnote.service;

import java.util.List;
import java.util.Locale;

import org.postnote.entity.DictionaryItem;
import org.postnote.repository.DictionaryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service to access the I18NRepository (that is plays the business logic role)
 *
 * @version 1.0 
 * @since 2014-06-12
 * 
 * @author Maurizio Nagni
 **/
@Service
public class DictionaryService {

	final static Logger logger = LoggerFactory.getLogger(DictionaryService.class);
	
	@Autowired
	private DictionaryRepository dictionaryRepo;

	/**
	 * Retrieves a dictionary for a specifc language
	 * @param locale the required language
	 * @return a List of Sentence
	 **/
	public List<DictionaryItem> getDictionary(Locale locale) {
		logger.debug(String.format("Requested locale: %1$s", locale));
		return dictionaryRepo.findByLocale(locale);
	}

}

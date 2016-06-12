package org.postnote.repository;

import java.util.List;
import java.util.Locale;

import org.postnote.entity.DictionaryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Accesses the I18NRepository (that is plays the model access role)
 * 
 * @version 1.0 
 * @since 2014-06-12
 * 
 * @author Maurizio Nagni
 **/
@Repository
public interface DictionaryRepository extends JpaRepository<DictionaryItem, Long> {
	
	/**
	 * Retrieves all the {@link DictionaryItem} belonging to a given Locale.
	 * 
	 * @param locale The laguage dictionaty to retrieve
	 * @return a list of items for the required language.
	 **/
	List<DictionaryItem> findByLocale(Locale locale);
}

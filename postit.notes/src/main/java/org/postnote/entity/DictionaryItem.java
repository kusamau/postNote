package org.postnote.entity;

import java.io.Serializable;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

/**
 * Represents a dictionary entity used to create a language dictionary.
 * <p>A key:value pair is associated to a specific language code.
 * 
 * @version 1.0 
 * @since 2014-06-12
 * 
 * @author Maurizio Nagni
 */
@Entity(name="DICTIONARY")
@IdClass(DictionaryItemPK.class)
public class DictionaryItem implements Serializable {

	private static final long serialVersionUID = 3262249257342220803L;

	@Id
    @Column(nullable = false)
    private Locale locale;
	
	@Id
    @Column(nullable = false)
    private String key;
    
    @Column(nullable = false)
    private String value;

    
	/**
     * Gets the language code for this sentence
     * @return the language code
     */
    public Locale getLocale() {
		return locale;
	}

    /**
     * Sets the language Locale for this sentence.
     * @param locale the language code
     */	
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	/**
     * Gets the sentence
     * @return the sentence
     */	
	public String getValue() {
		return value;
	}

    /**
     * Sets the sentence for the specified code
     * @param value the sentence text
     */	
	public void setValue(String value) {
		this.value = value;
	}
	
	/**
     * Gets the key value
     * @return the key value
     */
	public String getKey() {
		return key;
	}
	
    /**
     * Sets the sentence key
     * @param key the sentence key
     */		
	public void setKey(String key) {
		this.key = key;
	}

}

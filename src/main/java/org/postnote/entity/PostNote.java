package org.postnote.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Represents a single note.
 * <p>As JPA Entity it belong to the <i>POST_NOTE</i> table using {@link PostNote#id} as primary (generated) key. 
 * None of the field is nullable.
 * <p>Processed by JAXB, generate an XML document using <i>postnote</i> as root node.
 * 
 * @version 1.0 
 * @since 2014-06-12
 * 
 * @author Maurizio Nagni
 */
@XmlRootElement(name = "postnote")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity(name="POST_NOTE")
public class PostNote implements Serializable {

	private static final long serialVersionUID = 3262249257342220803L;
	
	/**
	 * Pattern for Data object when converted to string (before/after json marshall/unmarshall)  
	 **/
	public final static String DATA_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS a z";
	
	@Id
    @GeneratedValue
    private Long id;
    
    @Column(nullable = false)
    private String note;
    
    @Column(nullable = false)
    private String color;
    
    @Column(nullable = false)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern=DATA_PATTERN)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    
    /**
     * Gets the post ID
     * @return the post primary key
     */
	public Long getId() {
		return id;
	}

    /**
     * Sets the post ID
     * @param id the post primary key
     */	
	public void setId(Long id) {
		this.id = id;
	}

    /**
     * Gets the post text
     * @return the post text
     */
	public String getNote() {
		return note;
	}

    /**
     * Sets the post text
     * @param note the post text
     */	
	public void setNote(String note) {
		this.note = note;
	}
	
    /**
     * Gets the post color
     * @return the post color
     */
	public String getColor() {
		return color;
	}

    /**
     * Sets the post color
     * @param color the post color
     */	
	public void setColor(String color) {
		this.color = color;
	}

    /**
     * Gets the post creation date
     * @return the post creation date
     */	
	public Date getCreationDate() {
		return creationDate;
	}

    /**
     * Sets the post creation date
     * @param creationDate the post creation date
     */	
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
}

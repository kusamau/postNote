package org.postnote.repository;

import org.postnote.entity.PostNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Manages access to PostNote table.
 * 
 * @version 1.0 
 * @since 2014-06-12
 * 
 * @author Maurizio Nagni
 **/
@Repository
public interface PostNoteRepository extends JpaRepository<PostNote, Long> {
	
}

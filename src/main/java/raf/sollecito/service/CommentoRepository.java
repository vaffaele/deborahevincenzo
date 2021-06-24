package raf.sollecito.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import raf.sollecito.model.Commento;
import raf.sollecito.model.Post;

public interface CommentoRepository extends CrudRepository<Commento, Integer>{

	@Transactional
	@Modifying
	@Query(
			  value = "DELETE FROM COMMENTO WHERE post_id = :id", 
			  nativeQuery = true)
	public void deleteCommenti(@Param("id") Integer id);
}

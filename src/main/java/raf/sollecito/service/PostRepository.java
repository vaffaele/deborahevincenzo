package raf.sollecito.service;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.hibernate.annotations.NamedQuery;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.PageRequest;
import raf.sollecito.model.Post;

public interface PostRepository extends PagingAndSortingRepository<Post, Integer>{

	@Query(
			  value = "SELECT * FROM post p order by p.id desc", 
			  nativeQuery = true)
	public Page<Post> findPostWithOrderedCommets(PageRequest firstPageWithTwoElements);
	
	
	@Transactional
	@Modifying
	@Query(
			  value = "DELETE FROM post WHERE id = :id", 
			  nativeQuery = true)
	public void deletePost(@Param("id") Integer id);
}



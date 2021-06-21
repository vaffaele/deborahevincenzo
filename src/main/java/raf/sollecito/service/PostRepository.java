package raf.sollecito.service;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.hibernate.annotations.NamedQuery;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.PageRequest;
import raf.sollecito.model.Post;

public interface PostRepository extends PagingAndSortingRepository<Post, Integer>{

	@Query(
			  value = "SELECT * FROM post p order by p.id desc", 
			  nativeQuery = true)
	public Page<Post> findPostWithOrderedCommets(PageRequest firstPageWithTwoElements);
}



package raf.sollecito.service;
import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import raf.sollecito.model.Post;

public interface PostRepository extends PagingAndSortingRepository<Post, Integer>{

	
}



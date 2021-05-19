package raf.sollecito.service;

import org.springframework.data.repository.CrudRepository;

import raf.sollecito.model.Commento;
import raf.sollecito.model.Post;

public interface CommentoRepository extends CrudRepository<Commento, Integer>{

}

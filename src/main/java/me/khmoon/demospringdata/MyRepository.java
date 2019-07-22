package me.khmoon.demospringdata;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.lang.Nullable;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface MyRepository<T, ID extends Serializable> extends Repository<T, ID> {

  <E extends T> E save(@Nullable E entity);

  List<T> findAll();

  long count();

  @Nullable
  <E extends T> Optional<E> findById(Id id);
}

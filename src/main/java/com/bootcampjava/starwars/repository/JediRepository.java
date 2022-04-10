package com.bootcampjava.starwars.repository;

import com.bootcampjava.starwars.model.Jedi;

import java.util.List;
import java.util.Optional;

public interface JediRepository {

    Optional<Jedi> findById(Integer id);

    List<Jedi> findAll();

    boolean update(Jedi jedi);

    Jedi save(Jedi jedi);

    boolean delete(Integer id);
}

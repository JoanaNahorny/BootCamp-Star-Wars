package com.bootcampjava.starwars.service;

import com.bootcampjava.starwars.model.Jedi;
import com.bootcampjava.starwars.repository.JediRepositorylmpl;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class JediService {

    private static final Logger logger = logManager.getLogger(JediService.class);

    // repository

    private final JediRepositorylmpl jediRepositorylmpl;

    public JediService(JediRepositorylmpl jediRepositorylmpl) { this.jediRepositorylmpl = jediRepositorylmpl};

    public Optional<Jedi> findbyId(int id){

        logger.info("Find by Jedi with id: {}", id);

        return jediRepositorylmpl.findById(id);
    }

    public List<Jedi> findAll(){

        logger.info("Bring all the jedis from the Galaxy");

        return jediRepositorylmpl.findAll();
    }

    public Jedi save(Jedi jedi) {
        jedi.setVersion(1);

        logger.info("Update Jedi from system");

        return jediRepositorylmpl.save(jedi);
    }

    public boolean update (Jedi jedi) {
        boolean updated = false;

        Jedi savedJedi = this.save(jedi);

        if (savedJedi != null) updated = true;

        return updated;
    }

    public boolean delete (int id) { return true;}
 }

package com.bootcampjava.starwars.controller;

import com.bootcampjava.starwars.model.Jedi;
import com.bootcampjava.starwars.service.JediService;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.LogManager;

@RestController
public class JediController {
    private static final Logger logger = LogManager.getLogManager(JediController.class);

    private final JediService jediService;

    public JediController(JediService jediService) { this.jediService = jediService; }

    @GetMapping("/jedi/{id}")
    public ResponseEntity<?> getJedi(@PathVariable int id) {

        return jediService.findbyId(id)
                .map(jedi -> {
                    try {
                        return ResponseEntity
                                .ok()
                                //tipo de versionamento
                                .eTag(Integer.toString(jedi.getVersion()))
                                .location(new URI("/jedi/" + jedi.getId()))
                                .body(jedi);
                    } catch (URISyntaxException e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/jedi")
    public ResponseEntity<Jedi> savejedi(@RequestBody Jedi jedi) {

        //cenário mapeado para ter o comportamento
        Jedi newJedi = jediService.save(jedi);

        try{
            return  ResponseEntity
                    .created(new URI("/jedi/" + newJedi.getId()))
                    .eTag(Integer.toString(newJedi.getVersion()))
                    .body(newJedi);
        } catch (URISyntaxException){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
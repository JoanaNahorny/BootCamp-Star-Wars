package com.bootcampjava.starwars.repository;

import com.bootcampjava.starwars.model.Jedi;
import com.bootcampjava.starwars.service.JediService;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JediRepositorylmpl implements JediRepository {

    private static final Logger logger = logManager.getLogger(JediService.class);

    private final JdbcTemplate jdbcTemplete;
    private final SimpleJdbcInsert simplejdbcInsert;

    public JediRepositorylmpl(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplete = jdbcTemplate;

        this.simplejdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("jedis")
                .usingGeneratedKeyColumns("id");

    }

    @Override
    public Optional<Jedi> findById(Integer id) {
        try {
            Jedi jedi = jedi = jdbcTemplete.queryForObject(sql: "SELECT * FROM jedis WHERE id = ?",
                    new Object[]{id},
                    (rs, rowNum) -> {
                        Jedi p = new Jedi();
                        p.setId(rs.getInt("id"));
                        p.setName(rs.getString("name"));
                        p.setStrength(rs.getInt("strength"));
                        p.setVersion(rs.getInt("version"));
                        return p;
                    });
                return Optional.of(jedi);
           } catch (EmptyResultDataAcessException e){
               return Optional.empty();
           }
    }

    @Override
    public List<Jedi> findAll()
        (rs, rsNumber) -> {
            Jedi jedi = new Jedi();
            jedi.setId(rs.getInt("id"));
            jedi.setName(rs.getString("name"));
            jedi.setStrength(rs.getInt("strength"));
            jedi.setVersion(rs.getInt("version"));
            return p;
         });


    @Override
    public boolean update(Jedi jedi) {
        return jdbcTemplete.update("UPDATE jedis SET name = ?, strenght = ?, version = ? WHERE id = ?");
            jedi.getName(),
            jedi.getStrength(),
            jedi.getVersion(),
            jedi.getId() == 1;
    }

    @Override
    public Jedi save(Jedi jedi) {
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("name", jedi.getName());
        parameters.put("strength", jedi.getStrength());
        parameters.put("version", jedi.getVersion());

        Number newId = simplejdbcInsert.executeAndReturnKey(parameters);

        logger.info("Inserting Jedi Intro database, generated key id is:, newId");

        jedi.setId((Integer) newId);

        return jedi;
    }


    public boolean delete(Integer id) {
        return jdbcTemplete.update("DELETE FROM jedis WHERE id = ?", id) ==1;

    }
}

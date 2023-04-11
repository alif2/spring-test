package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Wrapping the database calls is a good idea if we're not using an ORM because of mocking. ORMs are wrappers already,
 * so if we choose to use one, we mock the ORM calls.
 *
 * Should we mock the database? No.
 * Instead, we can mock the wrapper. We should not mock what we don't own, so a wrapper class is how we avoid that.
 *
 * Ideally, wrappers should also wrap any objects to avoid leaky abstractions, though sometimes this creates a lot of
 * redundant classes and may not always be worth doing, but when calling an external service, it can be very helpful.
 */

@Repository
public class DemoDatabase implements DemoDatabaseWrapper {
    private final NamedParameterJdbcTemplate template;

    @Autowired
    public DemoDatabase(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Person getPersonById(int id) {
        String sql = "select * from person where Id = :id";
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("id", id);
        return template.queryForObject(sql, map, BeanPropertyRowMapper.newInstance(Person.class));
    }
}

package ua1.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua1.models.Person;

import java.util.List;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("select * from information_schema.person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        return jdbcTemplate.query("select * from information_schema.person where id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);

    }

    public void save(Person person) {
        jdbcTemplate.update("insert into information_schema.person values(1, ?, ?, ?)", person.getName(), person.getAge(), person.getEmail());
    }

    public void update(int id, Person person) {
        jdbcTemplate.update("update information_schema.person set name=?, age=?, email=? where id=?", person.getName(), person.getAge(), person.getEmail(), id);

    }

    public void delete(int id) {
        jdbcTemplate.update("delete from information_schema.person where id=?", id);
    }
}

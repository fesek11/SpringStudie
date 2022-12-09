package ua1.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua1.models.Book;
import ua1.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("select * from public.person", new BeanPropertyRowMapper<>(Person.class));
    }
    public Optional<Person> getFullName(String name) {
        return jdbcTemplate.query("select name from public.person where name = ?",new Object[]{name}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public Person show(int id) {
        return jdbcTemplate.query("select * from public.person where id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);

    }

    public void save(Person person) {
        jdbcTemplate.update("insert into public.person(name, age) values(?, ?)", person.getName(), person.getAge());
    }

    public void update(int id, Person person) {
        jdbcTemplate.update("update public.person set name=?, age=? where id=? ", person.getName(), person.getAge(), id);

    }

    public void delete(int id) {
        jdbcTemplate.update("delete from public.person where id=?", id);
    }

    public List<Book> getBookOwning(int id) {
        return jdbcTemplate.query("SELECT name, author FROM public.book WHERE person_id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class)).stream().toList();
    }
}

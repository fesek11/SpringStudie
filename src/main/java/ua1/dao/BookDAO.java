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
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("select * from public.book", new BeanPropertyRowMapper<>(Book.class));
    }

    public void save(Book book) {
        jdbcTemplate.update("insert into public.book(name, year, author) values(?,?,?)", book.getName(), book.getYear(), book.getAuthor());

    }

    public Book show(int id) {
        return jdbcTemplate.query("select * from public.book where book_id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

    public void update(int id, Book book) {
        jdbcTemplate.update("update public.book set name=?, year=?, author=? where book_id=?", book.getName(), book.getYear(), book.getAuthor(), id);

    }

    public void delete(int id) {
        jdbcTemplate.update("delete from public.book where book_id=?", id);
    }

    public void appoint(int id, int idPerson) {
        jdbcTemplate.update("update public.book set person_id=? where book_id=?", idPerson, id);
    }

    public Optional<Person> getBookOwner(int id) {
        return jdbcTemplate.query("SELECT public.person.* FROM public.book JOIN public.person ON public.book.person_id = Person_id WHERE Book_id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public void relies(int id) {
        jdbcTemplate.update("update public.book set person_id = null where book_id=?", id);
    }

}

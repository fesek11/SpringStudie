package ua1.dao;

import org.springframework.jdbc.core.RowMapper;
import ua1.models.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        book.setBook_id(rs.getInt("book_id"));
        book.setName(rs.getString("name"));
        book.setYear(rs.getInt("year"));
        book.setAuthor(rs.getString("author"));
        return book;
    }
}

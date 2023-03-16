package ua1.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua1.models.Book;
import ua1.models.Person;
import ua1.repositories.BookRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll(boolean sortByYear) {
        if (sortByYear) {
            return bookRepository.findAll(Sort.by("year"));
        } return bookRepository.findAll();
    }
    public List<Book> findWithPagination(int page, int booksPerPage, boolean sortByYear) {
        if (sortByYear) {
            return bookRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
        } return bookRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }

    public List<Book> findByBookName(String bookName) {
        return bookRepository.findByName(bookName);
    }


    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    public Book show(int id) {
        Optional<Book> bookShow = bookRepository.findById(id);

        bookShow.ifPresent(book -> {
            if (book.getTook_at() != null) {
                long currentExpireState = Math.abs(book.getTook_at().getTime() - new Date().getTime());
                if (currentExpireState > 1200) {
                    book.setExpired(true);
                }
            } else book.setExpired(false);
        });

        return bookShow.orElse(null);
    }

    public Person getBookOwner(int id) {
        return bookRepository.findById(id).map(Book::getOwner).orElse(null);
    }

    @Transactional
    public void relies(int id) {
        bookRepository.findById(id).ifPresent(book -> {
            book.setOwner(null);
            book.setTook_at(null);
            book.setExpired(false);
        });
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public void update(int id, Book book) {
        book.setBook_id(id);
        bookRepository.save(book);
    }

    @Transactional
    public void appoint(int id, Person newOwner) {
        bookRepository.findById(id).ifPresent(book -> {
                    book.setOwner(newOwner);
                    book.setTook_at(new Date());
                    book.setExpired(false);
                }
        );
    }

    public List<Book> search(String keyword) {
        return bookRepository.findByNameStartingWith(keyword);
    }
}

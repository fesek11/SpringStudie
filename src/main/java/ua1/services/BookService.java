package ua1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua1.models.Book;
import ua1.models.Person;
import ua1.repositories.BookRepository;
import ua1.repositories.PeopleRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public BookService(BookRepository bookRepository, PeopleRepository peopleRepository) {
        this.bookRepository = bookRepository;
        this.peopleRepository = peopleRepository;
    }

    @Transactional
    public List<Book> findByBookName(String bookName) {
        return bookRepository.findByName(bookName);
    }

    @Transactional
    public List<Book> findByOwner(Person owner) {
        return bookRepository.findByOwner(owner.getId());
    }

    @Transactional
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = false)
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public Optional<Book> show(int id) {
        return bookRepository.findById(id);
    }

    @Transactional
    public Optional<Person> getBookOwner(int id) {
        return bookRepository.findByOwnerId(id);
    }

    @Transactional(readOnly = false)
    public void relies(int id) {
        bookRepository.findById(id).get().setOwner(null);
    }

    @Transactional(readOnly = false)
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    @Transactional(readOnly = false)
    public void update(int id, Book book) {
        book.setBook_id(id);
        bookRepository.save(book);
    }

    @Transactional(readOnly = false)
    public void appoint(int id, int owner) {
        Optional<Person> byId = peopleRepository.findById(owner);
        bookRepository.findById(id).get().setOwner(byId.get());
    }
}

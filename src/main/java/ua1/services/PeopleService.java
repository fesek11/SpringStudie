package ua1.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua1.models.Book;
import ua1.models.Person;
import ua1.models.Status;
import ua1.repositories.BookRepository;
import ua1.repositories.PeopleRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;
    private final BookRepository bookRepository;

    public PeopleService(PeopleRepository repository, BookRepository bookRepository) {
        this.peopleRepository = repository;
        this.bookRepository = bookRepository;
    }

    @Transactional(readOnly = false)
    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    @Transactional(readOnly = false)
    public Person findOne(int id) {
        Optional<Person> byId = peopleRepository.findById(id);
        return byId.orElse(null);
    }

    @Transactional
    public void save(Person person) {
        person.setCreatedAt(new Date());
        person.setStatus(Status.Active);
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person person) {
        person.setId(id);
        peopleRepository.save(person);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    @Transactional
    public List<Book> getBookOwning(int id) {
        return bookRepository.findByOwner(id);
    }
}
package ua1.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua1.models.Person;
import ua1.repositories.PeopleRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository repository;

    public PeopleService(PeopleRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = false)
    public List<Person> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = false)
    public Person findOne(int id) {
        Optional<Person> byId = repository.findById(id);
        return byId.orElse(null);
    }

    @Transactional
    public void save(Person person) {
        repository.save(person);
    }

    @Transactional
    public void update(int id, Person person) {
        person.setId(id);
        repository.save(person);
    }

    @Transactional
    public void delete(int id) {
        repository.deleteById(id);
    }

}
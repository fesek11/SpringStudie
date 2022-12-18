package ua1.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua1.models.Book;
import ua1.models.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public PersonDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Person> index() {
        Session session = sessionFactory.getCurrentSession();

        List<Person> list = session.createQuery("select p from Person p", Person.class).getResultList();
        return list;
    }

    @Transactional(readOnly = true)
    public Optional<Person> getFullName(String name) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.createQuery("select p from Person p where p.name =: personName", Person.class)
                .setParameter("personName", name).getSingleResult());

    }
    @Transactional(readOnly = true)
    public Person show(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Person.class, id);
    }

    @Transactional(readOnly = false)
    public void save(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.save(person);
    }
    @Transactional(readOnly = false)
    public void update(int id, Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.update(person);
    }

    @Transactional(readOnly = false)
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(session.get(Person.class, id));
    }

    @Transactional(readOnly = true)
    public List<Book> getBookOwning(int id) {

        return new ArrayList<>(Collections.singleton(new Book( "Test", "Test", 1902)));
    }
}

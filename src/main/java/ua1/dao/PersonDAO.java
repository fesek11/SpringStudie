package ua1.dao;

import org.springframework.stereotype.Component;
import ua1.models.Person;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static int PEOPLE_COUNT;
    private final List<Person> people;

    {
        people = new ArrayList<>();
        people.add(new Person(++PEOPLE_COUNT, "Dima", 18, "ema@ff"));
        people.add(new Person(++PEOPLE_COUNT, "Stas", 14, "dsd@b"));
        people.add(new Person(++PEOPLE_COUNT, "Sasha", 19, "a@gmail.com"));
    }

    public List<Person> index() {
        return people;
    }

    public Person show(int id) {
        return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
    }

    public void save(Person person) {
        person.setId(++PEOPLE_COUNT);
        people.add(person);
    }

    public void update(int id, Person person) {
        Person personToBeUpdated = show(id);
        personToBeUpdated.setAge(person.getAge());
        personToBeUpdated.setEmail(person.getEmail());
        personToBeUpdated.setName(person.getName());
    }

    public void delete(int id) {
        people.removeIf(p -> p.getId() == id);
        PEOPLE_COUNT--;
    }
}

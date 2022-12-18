package ua1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua1.models.Book;
import ua1.models.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByName(String bookName);

//    List<Book> findByOwner(Person owner);

    List<Book> findByOwner(Integer id);

    Optional<Person> findByOwnerId(int id);
}

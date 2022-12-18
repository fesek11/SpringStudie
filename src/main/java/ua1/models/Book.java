package ua1.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book")
public class Book {
    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int book_id;
    @Column(name = "name")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String name;
    //    @Pattern(regexp = "[A-Z]\\w+\\s[A-Z]\\w+\\s[A-Z]\\w+", message = "Enter author name surname and ???")
    @Column(name = "author")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String author;
    @Min(value = 1500, message = "Year should be greater than 1500")
    private int year;
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    public Book(String name, String author, int year) {
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public Book(String name, String author, int year, Person owner) {
        this.name = name;
        this.author = author;
        this.year = year;
        this.owner = owner;
    }

    public String getFullName() {
        return getAuthor() + ", " + getName() + ", " + getYear();
    }
}

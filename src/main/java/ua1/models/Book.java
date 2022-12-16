package ua1.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private int book_id;

//    @Pattern(regexp = ".*", message = "Enter book name")
    private String name;
//    @Pattern(regexp = "[A-Z]\\w+\\s[A-Z]\\w+\\s[A-Z]\\w+", message = "Enter author name surname and ???")
    private String author;

    @Min(value = 0, message = "Year should be greater than 0")
    private int year;


    public String getFullName() {
        return getAuthor() + ", " + getName() + ", " + getYear();
    }
}

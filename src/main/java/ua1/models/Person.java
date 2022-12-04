package ua1.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    private int id;
    @Valid
    @Pattern(regexp = "[A-Z]\\w+\\s[A-Z]\\w+\\s[A-Z]\\w+", message = "Enter right your name surname and ???")
    private String name;
    public String getFullName() {
        return getName();
    }
//    @Min(value = 1,message = "Age should be greater than 1")
//    private int age;
//    @NotEmpty(message = "Email should be not empty")
//    @Email(message = "Email should be valid")
//    private String email;
}
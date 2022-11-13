package ua1.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    private int id;
    @NotEmpty(message = "Name should be not empty")
    @Size(min = 2, max = 30)
    private String name;
    @Min(value = 1,message = "Age should be greater than 1")
    private int age;
    @NotEmpty(message = "Email should be not empty")
    @Email(message = "Email should be valid")
    private String email;
}
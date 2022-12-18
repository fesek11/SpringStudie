package ua1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua1.models.Book;
import ua1.models.Person;
import ua1.services.BookService;
import ua1.services.PeopleService;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;
    private final PeopleService peopleService;

    @Autowired
    public BookController(BookService bookService, PeopleService peopleService) {
        this.bookService = bookService;
        this.peopleService = peopleService;
    }


    @GetMapping()
    public String index(Model model) {
        model.addAttribute("book", bookService.findAll());
        return "book/index";
    }

    @GetMapping("/new")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        return "book/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "book/new";
        }
        bookService.save(book);
        return "redirect:/book";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookService.show(id));

        Optional<Person> personOwner = bookService.getBookOwner(id);
        if (personOwner.isPresent()) {
            model.addAttribute("owner", personOwner.get());
        } else {
            model.addAttribute("people", peopleService.findAll());
        }
        return "book/show";
    }

    @PostMapping("/relies/{id}")
    public String relies(@PathVariable("id") int id) {
        bookService.relies(id);
        return "redirect:/book/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/book";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookService.show(id));
        return "book/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "book/edit";
        }
        bookService.update(id, book);
        return "redirect:/book";
    }

    @PatchMapping("/appoint/{id}")
    public String appoint(@ModelAttribute(value = "person") Person person, @PathVariable(value = "id") int id) {
//        bookService.appoint(id, person);
        bookService.appoint(id, person.getId());
        return "redirect:/book/" + id;
    }

}

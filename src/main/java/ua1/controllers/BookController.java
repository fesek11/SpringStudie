package ua1.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua1.dao.BookDAO;
import ua1.dao.PersonDAO;
import ua1.models.Book;
import ua1.models.Person;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/book")
@AllArgsConstructor
public class BookController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("book", bookDAO.index());
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
        bookDAO.save(book);
        return "redirect:/book";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookDAO.show(id));

        Optional<Person> personOwner = bookDAO.getBookOwner(id);
        if (personOwner.isPresent()) {
            model.addAttribute("owner", personOwner.get());
        } else {
            model.addAttribute("people", personDAO.index());
        }
        return "book/show";
    }

    @PostMapping("/relies/{id}")
    public String relies(@PathVariable("id") int id) {
        bookDAO.relies(id);
        return "redirect:/book";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/book";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookDAO.show(id));
        return "book/edit";
    }

    @PatchMapping("/appoint/{id}")
    public String appoint(@ModelAttribute(value = "person") Person person, @PathVariable(value = "id") int id) {
        bookDAO.appoint(id, person.getId());
        return "redirect:/book/" + id;
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "book/edit";
        }
        bookDAO.update(id, book);
        return "redirect:/book";
    }

}

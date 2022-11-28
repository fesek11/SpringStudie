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
    public String show(@ModelAttribute("person") Person person, @PathVariable("id") int id, Model model) {
        if (!bookDAO.booked(id)) {
            System.out.println("1");
        } else {
            System.out.println("0");
        }
        model.addAttribute("book", bookDAO.show(id));
        model.addAttribute("people", personDAO.index());
        return "book/show";
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

    @PatchMapping("/appoint")
    public String appoint(@PathVariable("id") int id, @PathVariable("person") int idPerson) {
        bookDAO.appoint(id, idPerson);
        return "book/show";
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

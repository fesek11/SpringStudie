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
import java.util.List;
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
    public String index(Model model, @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
                        @RequestParam(value = "sort_by_year", required = false) boolean sortByYear) {
        if (page == null || booksPerPage == null) {
            model.addAttribute("book", bookService.findAll(sortByYear));
        } else {
            model.addAttribute("book", bookService.findWithPagination(page, booksPerPage, sortByYear));
        }
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

       Person personOwner = bookService.getBookOwner(id);
        if (personOwner!=null) {
            model.addAttribute("owner", personOwner);
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

    @PatchMapping("/{id}/appoint")
    public String appoint(@ModelAttribute(value = "person") Person person, @PathVariable(value = "id") int id) {
        bookService.appoint(id,person);
        return "redirect:/book/" + id;
    }

    @PostMapping("/search")
    public String searchWithKeyWord(Model model, @RequestParam("keyword") String keyword) {
        model.addAttribute("book", bookService.search(keyword));
        return "book/search";
    }
    @GetMapping("/search")
    public String searchPage() {
        return "book/search";
    }


}

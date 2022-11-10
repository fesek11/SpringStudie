package ua1.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {

    @GetMapping("/hello-world")
    public String hello(@RequestParam(value = "first", required = false) int first,
                        @RequestParam(value = "second", required = false) int second,
                        @RequestParam(value = "action", required = false) String action,
                        Model model) {

        int calc = 0;
        switch (action) {
            case "sum" :
                calc = first + second;
                break;
            case "div":
                calc = first / second;
                break;
            case "mult":
                calc = first * second;
                break;
            case "sub":
                calc = first - second;
                break;
            default:
                calc = 0;
                break;
        }
        model.addAttribute("calc", calc);
        return "/people/hello_world";
    }

    @GetMapping("/index")
    public String index() {
        return "/people/index";
    }

    @GetMapping("/new")
    public String newPerson() {
        return "/people/new";
    }

}

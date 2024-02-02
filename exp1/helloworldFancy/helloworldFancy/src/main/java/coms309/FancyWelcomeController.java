package coms309;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
class WelcomeController {

    @GetMapping("/")
    public String welcome() {
        return "<i style=\"font-family: Arial;\">Hello and welcome to <b>COMS 309</b></i>";
    }

    @GetMapping("/{name}")
    public String welcome(@PathVariable String name) {
        return "<i style=\"font-family: Arial;\">Hello and welcome to <b>COMS 309</b>: " + name + "</i>";
    }
}

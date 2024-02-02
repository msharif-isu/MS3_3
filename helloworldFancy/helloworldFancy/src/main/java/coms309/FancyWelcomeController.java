package coms309;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
class WelcomeController {
    //Modified messages, font, and format.
    @GetMapping("/")
    public String welcome() {
        return "<i style=\"font-family: 'Lucida Handwriting';\">Welcome to <b>COMS 309</b>, enjoy your stay!</i>";
    }

    @GetMapping("/{name}")
    public String welcome(@PathVariable String name) {
        return "<i style=\"font-family: 'Lucida Handwriting'\">Welcome to <b>COMS 309</b>: " + name + ", enjoy your stay!</i>";
    }
}

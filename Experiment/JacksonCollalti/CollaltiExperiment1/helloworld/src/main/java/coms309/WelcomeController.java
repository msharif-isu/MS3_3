package coms309;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
class WelcomeController {

    //modified messages of the first two
    @GetMapping("/")
    public String welcome() {
        return "Jackson welcomes you to COMS 309";
    }

    @GetMapping("/{name}")
    public String welcome(@PathVariable String name) {
        return "Jackson welcomes " + name + " to CS309";
    }

    //Two new mappings
    @GetMapping("/whatIs")
    public String whatIs() {
        return "COMS 309 is a project class at ISU";
    }

    @GetMapping("/whatIs/{name}")
    public String whatIsName(@PathVariable String name) {
        return name + " is curious about CS309";
    }
}

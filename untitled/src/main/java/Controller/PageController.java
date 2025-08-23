package Controller;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/enter-numbers")
    public String page() {
        return "enter-numbers";
    }
}

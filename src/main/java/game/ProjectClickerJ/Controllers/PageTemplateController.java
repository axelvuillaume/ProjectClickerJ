package game.ProjectClickerJ.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class PageTemplateController {
    @GetMapping("/weaponTemplate")
    public String getConnexionPage(Model model) {
        return "weaponTemplate";
    }
}

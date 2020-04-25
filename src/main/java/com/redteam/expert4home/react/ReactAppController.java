package com.redteam.expert4home.react;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReactAppController {
    @RequestMapping(value={"/app/*"})
    public String AppPage() {
        return "index";
    }
}

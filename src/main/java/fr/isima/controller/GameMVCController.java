package fr.isima.controller;

import fr.isima.data.PlayerBean;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/play")
public class GameMVCController {

    @PostMapping
    public ModelAndView play(@Param("playerSelected") PlayerBean playerSelected) {
        return new ModelAndView("game");
    }
}

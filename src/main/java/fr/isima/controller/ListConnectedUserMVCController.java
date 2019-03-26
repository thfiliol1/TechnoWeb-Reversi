package fr.isima.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("list-connected-users")
public class ListConnectedUserMVCController {

    public ModelAndView getConnectedUsers(String viewName) {
        return new ModelAndView(viewName);
    }
}

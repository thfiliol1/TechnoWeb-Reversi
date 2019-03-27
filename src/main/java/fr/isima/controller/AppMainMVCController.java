package fr.isima.controller;

import fr.isima.business.MyUserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/main")
public class AppMainMVCController {

    private ListConnectedUserMVCController listConnectedUserMVCController;

    @Autowired
    public AppMainMVCController(ListConnectedUserMVCController listConnectedUserMVCController) {
        this.listConnectedUserMVCController = listConnectedUserMVCController;
    }

    @GetMapping
    public ModelAndView main() {
        return this.listConnectedUserMVCController.getConnectedUsers("apps-main");
    }
}

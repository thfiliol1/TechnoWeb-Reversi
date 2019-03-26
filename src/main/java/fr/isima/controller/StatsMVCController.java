package fr.isima.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Provider;

@Controller
@RequestMapping("/stats")
public class StatsMVCController {

    private final Provider<StatsViewBean> statsViewBean;

    @Autowired
    public StatsMVCController(Provider<StatsViewBean> statsViewBean) {
        this.statsViewBean = statsViewBean;
    }

    @GetMapping
    public ModelAndView stats() {
        return new ModelAndView("statsPage","stats", this.statsViewBean.get());
    }
}

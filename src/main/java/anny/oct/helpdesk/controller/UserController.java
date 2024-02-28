package anny.oct.helpdesk.controller;

import anny.oct.helpdesk.model.User;
import anny.oct.helpdesk.model.security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.security.RolesAllowed;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class UserController {

    Logger logger
            = Logger.getLogger(
            UserController.class.getName());

    @GetMapping("/login")     //for own login page
    public String getLoginPage(@ModelAttribute("user") User user) {
                return "user/login";
    }

    @RolesAllowed("ROLE_ADMIN")  //to delete
    @GetMapping("/success")
    public ModelAndView getSuccess(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        final CustomUserDetails principal = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logger.log(Level.INFO, "principal.getUser()  "+principal.getUser());
        final ModelAndView modelAndView = new ModelAndView("success");
        modelAndView.addObject("username", customUserDetails.getUser().getEmail());
        return modelAndView;
    }

}

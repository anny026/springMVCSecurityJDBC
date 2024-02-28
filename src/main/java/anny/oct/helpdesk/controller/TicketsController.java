package anny.oct.helpdesk.controller;

import anny.oct.helpdesk.dao.CategoryDAO;
import anny.oct.helpdesk.dao.CommentDAO;
import anny.oct.helpdesk.dao.HistoryDAO;
import anny.oct.helpdesk.dao.TicketDAO;
import anny.oct.helpdesk.model.*;
import anny.oct.helpdesk.model.security.CustomUserDetails;
import anny.oct.helpdesk.service.ticket.TicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/tickets")
public class TicketsController {
    private final TicketDAO ticketDAO;
    private final CategoryDAO categoryDAO;
    private final TicketService ticketService;
    private final CommentDAO commentDAO;
    private final HistoryDAO historyDAO;

    Logger logger
            = Logger.getLogger(
            TicketsController.class.getName());

    @Autowired
    public TicketsController(TicketDAO ticketDAO, CategoryDAO categoryDAO, TicketService ticketService, CommentDAO commentDAO, HistoryDAO historyDAO) {
        this.ticketDAO=ticketDAO;
        this.categoryDAO=categoryDAO;
        this.ticketService = ticketService;
        this.commentDAO = commentDAO;
        this.historyDAO = historyDAO;
    }

    @GetMapping()
    public String allTickets(Model model,
                             @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        final CustomUserDetails principal = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("username", customUserDetails.getUser().getFirstName());
        model.addAttribute("user", customUserDetails.getUser());
        User user = customUserDetails.getUser();  // или передавать principal??
//        model.addAttribute("tickets", ticketDAO.getAllTickets());  //попробовать через сервис
        if (Role.values()[user.getRoleId()-1]==Role.ROLE_EMPLOYEE) {
        logger.log(Level.INFO, Role.values()[user.getRoleId()-1]+"==========="+Role.ROLE_EMPLOYEE);
        model.addAttribute("tickets", ticketService.getTicketEmployee(user));}
        else if (Role.values()[user.getRoleId()-1]==Role.ROLE_MANAGER) {
        logger.log(Level.INFO, Role.values()[user.getRoleId()-1]+"==========="+Role.ROLE_MANAGER);
            model.addAttribute("tickets", ticketService.getTicketManager(user));}
        else {
        logger.log(Level.INFO,   Role.values()[user.getRoleId()-1]+"==========="+Role.ROLE_ENGINEER);
            model.addAttribute("tickets", ticketService.getTicketEngineer(user));
        }
        logger.log(Level.INFO, "This is principal   "+principal.getUser());
        logger.log(Level.INFO, "This is customUserDetails   "+customUserDetails.getUser());
        return "tickets/allTickets";
    }

  //  @Secured({ "ROLE_EMPLOYEE", "ROLE_MANAGER" })
    @GetMapping("/create")
    public String getLoginPage(Model model,
                               @ModelAttribute("ticket") Ticket ticket) {
        model.addAttribute("categories", categoryDAO.getCategories());
        return "tickets/createTicket";
    }

   // @Secured({ "ROLE_EMPLOYEE", "ROLE_MANAGER" })
    @PostMapping(params = "submit")
    public String createNew(@ModelAttribute("ticket") @Valid Ticket ticket,
                            BindingResult bindingResult,
                            Model model,
//                            @ModelAttribute("text") @Valid Comment comment,
                            @RequestParam(value = "text", required = false) String comment,
                            @AuthenticationPrincipal CustomUserDetails customUserDetails
                            ) {
        model.addAttribute("categories", categoryDAO.getCategories());//лишнее?
        if (bindingResult.hasErrors())
            return "redirect:/tickets/create";
        User user = customUserDetails.getUser();
        logger.log(Level.INFO, "This is ticket in createNew  "+ticket);
        logger.log(Level.INFO, "This is user in createNew   "+user);
        logger.log(Level.INFO, "Comment:     ....... "+comment);
        int id_state=2;
        int idNewTicket=ticketDAO.save(ticket, id_state, user);
        logger.log(Level.INFO, "ID saved ticket................"+idNewTicket);
        commentDAO.saveComment(comment, idNewTicket, user);
        historyDAO.saveHistory("Ticket is created", "Ticket is created", idNewTicket, user);
        return "redirect:/tickets";
    }

    @PostMapping(params = "save as draft")
    public String createDaft(@ModelAttribute("ticket") @Valid Ticket ticket,
                             BindingResult bindingResult,
                             @RequestParam(value = "text", required = false) String comment,
                             @AuthenticationPrincipal CustomUserDetails customUserDetails
                           //  @ModelAttribute("user") User user,
                         ) {
        if (bindingResult.hasErrors())
            return "redirect:/tickets/create";
        User user = customUserDetails.getUser();
        logger.log(Level.INFO, "ticket................"+ticket);
        int id_state=1;
        int idNewTicket=ticketDAO.save(ticket, id_state, user);
        logger.log(Level.INFO, "ID saved as draft ticket................"+idNewTicket);
        commentDAO.saveComment(comment, idNewTicket, user);
        historyDAO.saveHistory("Ticket is created", "Ticket is created", idNewTicket, user);
        return "redirect:/tickets";
    }


    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("ticket", ticketDAO.getTicket(id));
        model.addAttribute("categories", categoryDAO.getCategories());
        return "tickets/editTicket";
    }


//    @PatchMapping("/{id}")  //перестал работать
    @PostMapping("/{id}")
    public String update(@ModelAttribute("ticket") @Valid Ticket new_Ticket, BindingResult bindingResult,
                         @PathVariable("id") int id_updatedTicket,
                         @RequestParam(value = "save edit") String param,
                         @RequestParam(value = "text", required = false) String comment,
                         @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        if (bindingResult.hasErrors())
            return "tickets/editTicket";  //работает не корректно?
        User user = customUserDetails.getUser();
        int id_status;
        logger.log(Level.INFO, "RequestParam    "+param);
        if (param.equals("Draft")) {
            id_status = 1;
        } else id_status=2;
        if (ticketDAO.getTicket(id_updatedTicket).getState_id()==1) {
        ticketDAO.update(id_updatedTicket, new_Ticket, id_status);}
        commentDAO.saveComment(comment, id_updatedTicket, user);
        historyDAO.saveHistory("Ticket is edited", "Ticket is edited", id_updatedTicket, user);
        return "redirect:/tickets";
    }

  //  @RequestMapping(value="/action/{id}", method=RequestMethod.PATCH)  //Исправить!!!!
  //  @PatchMapping( "/action/{id}")   //Исправить!!
  @PostMapping( "/action/{id}")     //Заменить на PATCH!!??
    public String updateStatus(@ModelAttribute("ticket") @Valid Ticket ticket, BindingResult bindingResult,
                               @AuthenticationPrincipal CustomUserDetails customUserDetails,
                               @PathVariable("id") int id,
                               @RequestParam ("action") String action)
                          {
//        if (bindingResult.hasErrors())
//            return "tickets/editTicket";
        User user = customUserDetails.getUser();
        int id_status=1;
                              System.out.println(action); // test
                              System.out.println("ticket   "+ticket);  //test
        switch (action) {
            case("Submit"):
                id_status = 2;
                break;
            case("Approve"):
                id_status = 3;
                break;
            case("Decline"):
                id_status = 4;
                break;
            case("Cancel"):
                id_status = 7;
                break;
            case("Assign to Me"):
                id_status = 5;
                break;
            case("Done"):
                id_status = 6;
                break;
            case("Leave Feedback"):
                id_status = 0;
                return "feedback/leaveFeedback";
            case("View Feedback"):
                id_status = 0;
                return "feedback/viewFeedback";
        }
        if (id_status!=0) {
//        ticketDAO.updateStatus(id, id_status);//заменим на сервис
            String stateBefore= State.values()[(ticketDAO.getTicket(id).getState_id())-1].getName();
            logger.log(Level.INFO, "stateBefore   ***********  "+ stateBefore);
            String stateAfter = State.values()[id_status-1].getName();
 //???перенести историю в ticketService
            logger.log(Level.INFO, "stateAfter   ***********  "+ stateAfter);
//            historyDAO.saveHistory("Ticket Status is changed",
//           "Ticket Status is changed from " + stateBefore+ " to "+stateAfter, id, user);
            ticketService.updateStatusTicket(id, id_status, user, stateBefore, stateAfter);
        }
        return "redirect:/tickets";
    }

    @GetMapping("enamUrgency")
    public String enamUrgency(Model model){
        model.addAttribute("urgency", Urgency.values());
        return "tickets/createTicket";
    }

    @GetMapping("/{id}/overView")
    public String overView(Model model, @PathVariable("id") int id) {
        model.addAttribute("ticket", ticketDAO.getTicket(id));
        model.addAttribute("categories", categoryDAO.getCategories());
        model.addAttribute("category", categoryDAO.getCategory(ticketDAO.getTicket(id).getCategory_id()));
        model.addAttribute("histories", historyDAO.getHistory(id));
        return "tickets/ticketOverview";
    }

    @GetMapping("/{id}/overView/comments")
    public String getComment(Model model, @PathVariable("id") int id) {
        model.addAttribute("ticket", ticketDAO.getTicket(id));
        model.addAttribute("categories", categoryDAO.getCategories());
        model.addAttribute("category", categoryDAO.getCategory(ticketDAO.getTicket(id).getCategory_id()));
        logger.log(Level.INFO, "id для comment............."+id);
        model.addAttribute("comments", commentDAO.getComments(id));
        return "tickets/comments";
    }

    @PostMapping("/{id}/overView/comments")
    public String addComment(@ModelAttribute("ticket") @Valid Ticket new_Ticket, BindingResult bindingResult,
                             @RequestParam(value = "text", required = false) String comment,
                         @PathVariable("id") int id_updatedTicket,
                         @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
//        if (bindingResult.hasErrors())
//            return "tickets/editTicket";
        User user = customUserDetails.getUser();
        logger.log(Level.INFO, "RequestParam    "+comment);
        commentDAO.saveComment(comment, id_updatedTicket, user);
        return "redirect:/tickets/{id}/overView/comments";
    }

    @GetMapping("/{id}/overView/history")
    public String getHistory(Model model, @PathVariable("id") int id) {
        model.addAttribute("ticket", ticketDAO.getTicket(id));
        model.addAttribute("categories", categoryDAO.getCategories());
        model.addAttribute("category", categoryDAO.getCategory(ticketDAO.getTicket(id).getCategory_id()));
        logger.log(Level.INFO, "id для history............."+id);
        model.addAttribute("histories", historyDAO.getHistory(id));
        return "tickets/history";
    }





}


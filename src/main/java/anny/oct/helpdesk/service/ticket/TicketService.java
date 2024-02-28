package anny.oct.helpdesk.service.ticket;

import anny.oct.helpdesk.controller.TicketsController;
import anny.oct.helpdesk.dao.HistoryDAO;
import anny.oct.helpdesk.dao.TicketDAO;
import anny.oct.helpdesk.model.Ticket;
import anny.oct.helpdesk.model.User;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class TicketService {
    private final TicketDAO ticketDAO;
    private final HistoryDAO historyDAO;

    public TicketService(TicketDAO ticketDAO, HistoryDAO historyDAO) {
        this.ticketDAO = ticketDAO;
        this.historyDAO = historyDAO;
    }

    Logger logger
            = Logger.getLogger(
            TicketsController.class.getName());

    @Secured("ROLE_EMPLOYEE")
    public List<Ticket> getTicketEmployee(User user){
        logger.log(Level.INFO, "TicketService, before method getTicketEmployee***********************");
        List<Ticket> ticketList= ticketDAO.getAllTicketsEmployee(user);
        logger.log(Level.INFO, "TicketService, in method getTicketEmployee***********************");
        return ticketList;
    }

    @Secured("ROLE_MANAGER")   //не нужно?
    public List<Ticket> getTicketManager(User user){
        logger.log(Level.INFO, "TicketService, before method getTicketManager***********************");
        List<Ticket> ticketList= ticketDAO.getAllTicketsManager(user);
        logger.log(Level.INFO, "TicketService, in method getTicketManager***********************");
        return ticketList;
    }

    public List <Ticket> getTicketEngineer (User user) {
        logger.log(Level.INFO, "TicketService, before method getTicketEngineer***********************");
        List<Ticket> ticketList= ticketDAO.getAllTicketsEngineer (user);
        logger.log(Level.INFO, "TicketService, in method getTicketEngineer***********************");
        return ticketList;
    }

    public void updateStatusTicket(int id, int id_state, User user, String stateBefore, String stateAfter) {
        logger.log(Level.INFO, "TicketService, before method updateStatusEmployee***********************");
        logger.log(Level.INFO, "TicketService, user.getRoleId() "+user.getRoleId());
        logger.log(Level.INFO, "TicketService, ticketDAO.getTicket(id).getState_id() "+ticketDAO.getTicket(id).getState_id());
        logger.log(Level.INFO, "TicketService, id_state "+id_state);
        if (user.getRoleId()==1) {
            int state_id_now = ticketDAO.getTicket(id).getState_id();
            if ((state_id_now==1|state_id_now==4)
                    &(id_state==2|id_state==7)){
                logger.log(Level.INFO, "TicketService, id_state "+id_state);
                historyDAO.saveHistory("Ticket Status is changed",
                        "Ticket Status is changed from " + stateBefore+ " to "+stateAfter, id, user);
            ticketDAO.updateStatusOwnTicket(id, id_state); }
            logger.log(Level.INFO, "TicketService, in method updateStatusEmployee*********************** ");
        }
        if (user.getRoleId()==2) {
            int state_id_now = ticketDAO.getTicket(id).getState_id();
            if (ticketDAO.getTicket(id).getOwner_id()==user.getId()) {  //for tickets created by him
                if ((state_id_now==1|state_id_now==4)
                        &(id_state==2|id_state==7)){
                    logger.log(Level.INFO, "TicketService, id_state "+id_state);
                     historyDAO.saveHistory("Ticket Status is changed",
                            "Ticket Status is changed from " + stateBefore+ " to "+stateAfter, id, user);
                    ticketDAO.updateStatusOwnTicket(id, id_state); }
                logger.log(Level.INFO, "TicketService, 1 in method updateStatusManager*********************** ");
               } else if ((state_id_now==2)    //for tickets created by Employee
                    &(id_state==3|id_state==4|id_state==7)){
                logger.log(Level.INFO, "TicketService, id_state "+id_state);
                historyDAO.saveHistory("Ticket Status is changed",
                        "Ticket Status is changed from " + stateBefore+ " to "+stateAfter, id, user);
                ticketDAO.updateStatusByManager(id, id_state, user.getId()); }
            logger.log(Level.INFO, "TicketService, 2 in method updateStatusManager*********************** ");
            }
        if (user.getRoleId()==3) {
            int state_id_now = ticketDAO.getTicket(id).getState_id();
            if ((state_id_now==3)
                &(id_state==5|id_state==4)){
                logger.log(Level.INFO, "TicketService, id_state "+id_state);
                historyDAO.saveHistory("Ticket Status is changed",
                        "Ticket Status is changed from " + stateBefore+ " to "+stateAfter, id, user);
                ticketDAO.updateStatusByEngineer(id, id_state, user.getId());
            } else if ((state_id_now==5)
                    &(id_state==6)){
                logger.log(Level.INFO, "TicketService, id_state "+id_state);
                historyDAO.saveHistory("Ticket Status is changed",
                        "Ticket Status is changed from " + stateBefore+ " to "+stateAfter, id, user);
                ticketDAO.updateStatusByEngineer(id, id_state, user.getId());}
            logger.log(Level.INFO, "TicketService, in method updateStatusEngineer*********************** ");
        }
                    }
    }



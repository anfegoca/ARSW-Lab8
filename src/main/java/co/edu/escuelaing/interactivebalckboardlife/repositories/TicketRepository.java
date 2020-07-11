package co.edu.escuelaing.interactivebalckboardlife.repositories;

import java.util.ArrayList;
import java.util.List;
//import java.util.concurrent;
import java.util.Random;

public class TicketRepository {

    private List<String> tickets;

    private TicketRepository() {
        tickets = new ArrayList<>();
    }

    private static class helper {
        private static final TicketRepository INSTANCE = new TicketRepository();
    }

    public static TicketRepository getInstance() {
        return helper.INSTANCE;
    }
    public String getTicket(){
        Random r = new Random();
        int rand = r.hashCode();
        String ticket = Integer.toString(rand);
        tickets.add(ticket);
        return ticket;
    }
    public boolean checkTicket(String t){
        return tickets.remove(t);
    }

}
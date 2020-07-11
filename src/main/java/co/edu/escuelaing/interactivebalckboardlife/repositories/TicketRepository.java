package co.edu.escuelaing.interactivebalckboardlife.repositories;



import java.util.Random;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class TicketRepository {

    @Autowired
    private StringRedisTemplate template;

    // inject the template as ListOperations
    @Resource(name = "stringRedisTemplate")
    private ListOperations<String, String> listTickets;

    //private List<String> tickets;

    public TicketRepository() {
        //ickets = new ArrayList<>();
    }

    public synchronized String getTicket() {
        Random r = new Random();
        int rand = r.hashCode();
        String ticket = Integer.toString(rand);
        //tickets.add(ticket);
        listTickets.leftPush("ticketStore",ticket);
        return ticket;
    }

    public boolean checkTicket(String t) {
        Long isValid = listTickets.getOperations().boundListOps("ticketStore").remove(0, t);
        return (isValid > 0l);
    }

}
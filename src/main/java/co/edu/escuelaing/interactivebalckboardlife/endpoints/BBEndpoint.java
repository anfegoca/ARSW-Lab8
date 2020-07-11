/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.escuelaing.interactivebalckboardlife.endpoints;

import java.io.IOException;
import java.util.logging.Level;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

import co.edu.escuelaing.interactivebalckboardlife.configurator.BBApplicationContextAware;
import co.edu.escuelaing.interactivebalckboardlife.repositories.SessionRepo;
import co.edu.escuelaing.interactivebalckboardlife.repositories.TicketRepository;

@Component
@ServerEndpoint("/bbService")
public class BBEndpoint {

    private static final Logger logger = Logger.getLogger(BBEndpoint.class.getName());
    /* Queue for all open WebSocket sessions */
    static Queue<Session> queue = new ConcurrentLinkedQueue<>();

    TicketRepository ticketRepo =(TicketRepository)BBApplicationContextAware.getApplicationContext().getBean("ticketRepository");
    //SessionRepo sessionRepo =(SessionRepo)BBApplicationContextAware.getApplicationContext().getBean("sessionRepo");

    Session ownSession = null;
    boolean conectado = false;

    /* Call this method to send a message to all clients */
    public void send(String msg) {
        try {
            /* Send updates to all open WebSocket sessions */
            for (Session session : queue) {
                if (!session.equals(this.ownSession)) {
                    session.getBasicRemote().sendText(msg);
                }
                logger.log(Level.INFO, "Sent: {0}", msg);
            }
        } catch (IOException e) {
            logger.log(Level.INFO, e.toString());
        }
    }

    @OnMessage
    public void processPoint(String message, Session session) {
        //System.out.println("MENSAJE "+message+" "+conectado);
        if (conectado) {
            logger.log(Level.INFO, "Point received:" + message + ". From session: " + session);
            this.send(message);
        }else{
            if(ticketRepo.checkTicket(message)){
                //System.out.println("JEG");
                conectado=true;
            }else{
                //System.out.println("Muere");
                try {
                    queue.remove(session);
                    //sessionRepo.removeSession(session);
                    session.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    @OnOpen
    public void openConnection(Session session) {
        /* Register this connection in the queue */
        //System.out.println("SESSIONREPO "+sessionRepo+"Session "+session);
        //sessionRepo.addSession(session);
        queue.add(session);
        ownSession = session;
        //System.out.println("JOHANN ES RE GURRERO");
        logger.log(Level.INFO, "Connection opened.");
        try {
            
            session.getBasicRemote().sendText("Connection established.");

        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    @OnClose
    public void closedConnection(Session session) {
        /* Remove this connection from the queue */
        //sessionRepo.removeSession(session);
        queue.remove(session);
        logger.log(Level.INFO, "Connection closed for session " + session);
    }

    @OnError
    public void error(Session session, Throwable t) {
        /* Remove this connection from the queue */
        //sessionRepo.removeSession(session);
        queue.remove();
        logger.log(Level.INFO, t.toString());
        logger.log(Level.INFO, "Connection error.");
    }
}
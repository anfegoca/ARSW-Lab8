package co.edu.escuelaing.interactivebalckboardlife.repositories;

import java.util.List;

import javax.annotation.Resource;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.collections.DefaultRedisList;
import org.springframework.stereotype.Component;

@Component
public class SessionRepo {

    //@Autowired
    //private RedisTemplate template;

    //private Queue<Session> queue = new ConcurrentLinkedQueue<>();

    public SessionRepo(){};
    // inject the template as ListOperations
    @Resource(name = "redisTemplate")
    private ListOperations<String,Session> listSessions;


    public List<Session> getSessions(){
        
        return listSessions.range("SessionStore", 0l, listSessions.size("SessionStore"));
    }
    public void addSession(Session session){
        
        listSessions.leftPush("SessionStore", session);
        


    }
    public void removeSession(Session session){
        
        listSessions.getOperations().boundListOps("SessionStore").remove(0, session);

    }
    
}
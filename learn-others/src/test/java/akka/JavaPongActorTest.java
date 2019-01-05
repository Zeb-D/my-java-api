package akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;
import com.yd.akka.actor.hello.JavaPongActor;
import org.junit.Test;

/**
 * @author Yd on  2018-02-06
 * @description
 **/
public class JavaPongActorTest {
    ActorSystem actorSystem = ActorSystem.create();

    @Test
    public void test(){
        ActorRef actor = actorSystem.actorOf(Props.create(JavaPongActor.class));
        actor.tell("Ping",ActorRef.noSender());
        TestActorRef<JavaPongActor> actorTestActor =TestActorRef.create(actorSystem,Props.create(JavaPongActor.class));
        actorTestActor.receive("Ping");
        actorTestActor.tell("Ping",ActorRef.noSender());
        JavaPongActor javaPongActor = actorTestActor.underlyingActor();
        javaPongActor.receive();
    }

}

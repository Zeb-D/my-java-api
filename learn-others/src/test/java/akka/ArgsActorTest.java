//package akka;
//
//import akka.actor.ActorRef;
//import akka.actor.ActorSelection;
//import akka.actor.ActorSystem;
//import com.yd.akka.actor.factory.ActorFactory;
//import com.yd.akka.actor.hello.ArgsActor;
//import org.junit.Test;
//
///**
// * @author Yd on  2018-02-07
// * @description
// **/
//public class ArgsActorTest {
//    ActorSystem system = ActorSystem.create();
//
//    @Test
//    public void Test() {
//        ActorRef actor = system.actorOf(ActorFactory.props(ArgsActor.class, "Pong"));
//        actor.tell("ping", ActorRef.noSender());
//    }
//
//    @Test
//    public void testRemote() {
//        ActorSelection selection = system.actorSelection("akka.tcp://actorSystem@host.jason-goodwin.com:5678/user/KeanuReeves");
//        selection.pathString();
//    }
//
//}

package controllers;

import actors.Actors;
import actors.ClientConnection;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.stream.Materializer;
import akka.stream.OverflowStrategy;
import play.libs.streams.ActorFlow;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.WebSocket;

import javax.inject.Inject;

public class Application extends Controller {

    private final ActorSystem actorSystem;
    private final Materializer materializer;
    private final Actors actors;
    private ActorRef regionManagerClient;


  @Inject
  public Application(ActorSystem actorSystem, Materializer materializer, Actors actors) {
      this.actorSystem = actorSystem;
      this.materializer = materializer;
      this.actors = actors;
      regionManagerClient = actors.regionManagerClient();
  }

  /**
   * The index page.
   */
  public Result index(Http.Request request) {
    return ok(views.html.index.render(request));
  }

  // TODO - this needs to be updated
  /**
   * The WebSocket
   */
  public WebSocket stream(String email) {
      return WebSocket.Json.accept(request ->
              ActorFlow.actorRef(upstream -> ClientConnection.props(email, upstream, regionManagerClient),16, OverflowStrategy.dropNew(), actorSystem, materializer)
      );
  }

}
package controllers;

import actors.Actors;
import actors.ClientConnection;
import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.actor.typed.Scheduler;
import akka.actor.typed.javadsl.Adapter;
import akka.actor.typed.javadsl.AskPattern;
import akka.stream.javadsl.Flow;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import javax.inject.Inject;
import org.webjars.play.WebJarsUtil;
import play.libs.F;
import play.mvc.*;

public class Application extends Controller {

  private final ActorSystem system;

  @Inject
  public Application(ActorSystem system) {
    this.system = system;
  }

  /**
   * The index page.
   */
  public static Result index() {
    return ok(views.html.index.render());
  }

  // TODO - this needs to be updated
  /**
   * The WebSocket
   */
  public static WebSocket<JsonNode> stream(String email) {
      return WebSocket.withActor(upstream -> ClientConnection.props(email, upstream, Actors.regionManagerClient()));
  }


  // SAMPLE CODE:
  //  public WebSocket ws() {
  //    return WebSocket.Json.acceptOrResult(request -> {
  //      if (sameOriginCheck(request)) {
  //        final CompletionStage<Flow<JsonNode, JsonNode, NotUsed>> future = wsFutureFlow(request);
  //        final CompletionStage<F.Either<Result, Flow<JsonNode, JsonNode, ?>>> stage = future.thenApply(F.Either::Right);
  //        return stage.exceptionally(this::logException);
  //      } else {
  //        return forbiddenResult();
  //      }
  //    });
  //  }
  //
  //  @SuppressWarnings("unchecked")
  //  private CompletionStage<Flow<JsonNode, JsonNode, NotUsed>> wsFutureFlow(Http.RequestHeader request) {
  //    String id = Long.toString(request.asScala().id());
  //    Scheduler scheduler = Adapter.toTyped(system.scheduler());
  //    return AskPattern.<UserParentActor.Create, Flow<JsonNode, JsonNode, NotUsed>>ask(
  //            userParentActor, replyTo -> new UserParentActor.Create(id, replyTo), timeout, scheduler
  //    ).thenApply(f -> f.named("websocket"));
  //  }
  //
  //  private CompletionStage<F.Either<Result, Flow<JsonNode, JsonNode, ?>>> forbiddenResult() {
  //    final Result forbidden = Results.forbidden("forbidden");
  //    final F.Either<Result, Flow<JsonNode, JsonNode, ?>> left = F.Either.Left(forbidden);
  //
  //    return CompletableFuture.completedFuture(left);
  //  }
  //
  //  private F.Either<Result, Flow<JsonNode, JsonNode, ?>> logException(Throwable throwable) {
  //    logger.error("Cannot create websocket", throwable);
  //    Result result = Results.internalServerError("error");
  //    return F.Either.Left(result);
  //  }
  //
  //  /**
  //   * Checks that the WebSocket comes from the same origin.  This is necessary to protect
  //   * against Cross-Site WebSocket Hijacking as WebSocket does not implement Same Origin Policy.
  //   * <p>
  //   * See https://tools.ietf.org/html/rfc6455#section-1.3 and
  //   * http://blog.dewhurstsecurity.com/2013/08/30/security-testing-html5-websockets.html
  //   */
  //  private boolean sameOriginCheck(Http.RequestHeader rh) {
  //    final Optional<String> origin = rh.header("Origin");
  //
  //    if (! origin.isPresent()) {
  //      logger.error("originCheck: rejecting request because no Origin header found");
  //      return false;
  //    } else if (originMatches(origin.get())) {
  //      logger.debug("originCheck: originValue = " + origin);
  //      return true;
  //    } else {
  //      logger.error("originCheck: rejecting request because Origin header value " + origin + " is not in the same origin: "
  //              + String.join(", ", validOrigins));
  //      return false;
  //    }
  //  }
  //
  //  private List<String> validOrigins = Arrays.asList("localhost:9000", "localhost:19001");
  //  private boolean originMatches(String actualOrigin) {
  //    return validOrigins.stream().anyMatch(actualOrigin::contains);
  //  }
}
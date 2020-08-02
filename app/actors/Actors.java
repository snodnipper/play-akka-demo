package actors;

import akka.actor.*;
import akka.cluster.Cluster;
import backend.*;
import play.Application;

import javax.inject.Inject;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Lookup for actors used by the web front end.
 */
public class Actors {

    private final Application app;

    @Inject
    public Actors(Application app) {
        this.app = app;
    }

    public ActorRef regionManagerClient() {
        ActorSystem system = app.asScala().actorSystem();

        ActorRef regionManagerClientC = system.actorOf(RegionManagerClient.props(), "regionManagerClient");

        if (Cluster.get(system).getSelfRoles().stream().anyMatch(r -> r.startsWith("backend"))) {
            system.actorOf(RegionManager.props(), "regionManager");
        }

        if (Settings.SettingsProvider.get(system).BotsEnabled) {
            int id = 1;
            URL url = app.classloader().getResource("bots/" + id + ".json");
            List<URL> urls = new ArrayList<>();
            while (url != null) {
                urls.add(url);
                id++;
                url = app.classloader().getResource("bots/" + id + ".json");
            }
            system.actorOf(BotManager.props(regionManagerClientC, urls));
        }

        return regionManagerClientC;
    }
}

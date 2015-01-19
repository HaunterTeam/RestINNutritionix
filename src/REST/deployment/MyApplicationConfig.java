package REST.deployment;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("nutritionix")
public class MyApplicationConfig extends ResourceConfig {
    public MyApplicationConfig () {
        packages("REST");
    }
}

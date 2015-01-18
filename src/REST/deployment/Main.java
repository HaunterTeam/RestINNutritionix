package REST.deployment;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class Main
{
    private static final URI BASE_URI = URI.create("http://localhost:8443/nutritionix/");

    public static void main(String[] args) throws Exception, IllegalArgumentException, IOException, URISyntaxException
    {
        System.out.println("Starting Nutritionix standalone HTTP server...");
        JdkHttpServerFactory.createHttpServer(BASE_URI, createApp());
        System.out.println("Server started on " + BASE_URI + "\n[kill the process to exit]");



        System.out.println("Done");

    }
    public static ResourceConfig createApp() {
        return new MyApplicationConfig();
    }
}

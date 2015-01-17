package REST.helpers;

import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.*;
/**
 * Created by les on 16/01/15.
 */
public class RequestHandler {

    private Client _client;
    private WebTarget _service;
    private Response _response;
    private String _url;

    public RequestHandler(String url)
    {
        ClientConfig config = new ClientConfig();
        _client = ClientBuilder.newClient(config);
        _url = url;
        _service = _client.target(UriBuilder.fromUri(url).build());
        _response = null;

    }
    public String getRequestResult()
    {
        _response = _service.path("").request().accept(MediaType.APPLICATION_JSON).get();

        if(_response != null)
            return _response.readEntity(String.class);
        return "";
    }
}

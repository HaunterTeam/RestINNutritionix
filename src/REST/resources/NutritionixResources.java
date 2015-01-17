package REST.resources;

/**
 * Created by les on 16/01/15.
 */


import REST.beans.FoodBean;
import REST.helpers.RequestHandler;
import org.glassfish.jersey.client.ClientConfig;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.*;
import org.json.JSONObject;
import org.json.JSONArray;

@Path("/food")
public class NutritionixResources {

    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    String apiKey = "d8c597152276cf358f2d45444de7a4db";
    String apiID = "27b323e6";

    @GET
    @Path("{food}")
    @Produces(MediaType.APPLICATION_JSON )
    public FoodBean getFoodData(@PathParam("food") String food){

        String url = String.format("https://api.nutritionix.com/v1_1/search/%s?results=0:20&field" +
                "s=item_name,brand_name,item_id,nf_calories,nf_total_fat&appId=%s&appKey=%s",food,apiID,apiKey);
        RequestHandler handler = new RequestHandler(url);

        JSONObject object = new JSONObject(handler.getRequestResult());
        JSONArray array = object.getJSONArray("hits");

        //selection of a random item if there are multiple instances
        Random rmd = new Random();
        int ind = rmd.nextInt(array.length() -1);
        JSONObject foodJSON = array.getJSONObject(ind).getJSONObject("fields");
        FoodBean bean = new FoodBean();
        bean.setBrand_name(foodJSON.getString("brand_name"));
        bean.setItem_name(foodJSON.getString("item_name"));
        bean.setCalories((float)foodJSON.getDouble("nf_calories"));
        bean.setFat((float)foodJSON.getDouble("nf_total_fat"));

        return bean;

        //System.out.println(response.getStatus());

    }

}


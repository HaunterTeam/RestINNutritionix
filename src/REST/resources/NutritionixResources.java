package REST.resources;

/**
 * Created by les on 16/01/15.
 */


import REST.beans.FoodBean;
import REST.helpers.RequestHandler;
import org.glassfish.jersey.client.ClientConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.ws.rs.*;
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
    @Produces(MediaType.APPLICATION_JSON )
    public FoodBean getFoodData(@QueryParam("food") String food){

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
        bean.setCalories(foodJSON.getDouble("nf_calories"));
        bean.setFat(foodJSON.getDouble("nf_total_fat"));

        return bean;


    }
    @GET
    @Path("/diet")
    @Produces(MediaType.APPLICATION_JSON)

    public List<FoodBean> getDieteticFood(){
        String url = "https://api.nutritionix.com/v1_1/search/?results=0%3A50&cal_min=0&cal_max=300&fields=" +
                "item_name,brand_name,item_id,brand_id,nf_total_fat,nf_calories&appId=27b323e6&appKey=d8c597152276cf358f2d45444de7a4db";
        RequestHandler handler = new RequestHandler(url);

        JSONObject object = new JSONObject(handler.getRequestResult());
        JSONArray array = object.getJSONArray("hits");

        Boolean[] hasSeen = new Boolean[array.length() - 1];
        Arrays.fill(hasSeen,Boolean.FALSE);
        int listCount = 10;
        Random rmd = new Random();
        List<FoodBean> foods = new ArrayList<FoodBean>();

        for (int i = 0; i < listCount; i++) {
            int rand = rmd.nextInt(array.length() -1);

            if(!hasSeen[rand])
            {
                JSONObject foodJSON = array.getJSONObject(rand).getJSONObject("fields");
                FoodBean bean = new FoodBean();
                bean.setBrand_name(foodJSON.getString("brand_name"));
                bean.setItem_name(foodJSON.getString("item_name"));
                bean.setCalories(foodJSON.getDouble("nf_calories"));
                bean.setFat(foodJSON.getDouble("nf_total_fat"));
                foods.add(bean);
            }
            else
                i--;

        }

        return foods;

    }

}


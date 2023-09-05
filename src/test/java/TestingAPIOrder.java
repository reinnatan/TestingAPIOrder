import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import request.OrderRequestEndPoint;
import utils.PayloadProccessing;
import utils.PayloadUtil;

import java.sql.Timestamp;
import java.time.Instant;


public class TestingAPIOrder {

    @Test
    public void testingProcessOrder() {
        PayloadProccessing payloadProccessing = new PayloadUtil();

        RestAssured.baseURI = OrderRequestEndPoint.getBASE_URL();
        RequestSpecification request = RestAssured.given().contentType("application/json");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Instant instant = timestamp.toInstant();
        JsonObject orderRequest = payloadProccessing.getJsonObjectFromFile("ProcessOrder.json");
        payloadProccessing.replacePropertyValue(orderRequest, "last_updated_timestamp", instant.toEpochMilli());
        payloadProccessing.replacePropertyValue(orderRequest, "order_description", "Processed");

        //modify the request default payload
        request.body(orderRequest.toString());
        Response response = request.post(OrderRequestEndPoint.getEND_POINT_PROCESS_ORDER());

        JSONObject object = new JSONObject(response.getBody().asString());

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(object.getString("order_status"), "Processing");
        Assert.assertEquals(object.getString("order_description"), "Processed");
        //Assert.assertEquals(response.getBody().asString().contains("Processing"), true);
    }

    @Test
    public void testingNoOrderIdParam(){
        PayloadProccessing payloadProccessing = new PayloadUtil();
        RestAssured.baseURI = OrderRequestEndPoint.getBASE_URL();
        RequestSpecification request = RestAssured.given().contentType("application/json");

        JsonObject orderRequest = payloadProccessing.getJsonObjectFromFile("ProcessOrder.json");
        orderRequest.remove("order_id");
        request.body(orderRequest.toString());
        Response response = request.post(OrderRequestEndPoint.getEND_POINT_PROCESS_ORDER());

        Assert.assertEquals(response.getStatusCode(), 400);
        JSONObject object = new JSONObject(response.getBody().asString());
        Assert.assertEquals(object.getString("message"), "Invalid order id");
    }


    @Test
    public void testingNoOrderStatusParam(){
        PayloadProccessing payloadProccessing = new PayloadUtil();
        RestAssured.baseURI = OrderRequestEndPoint.getBASE_URL();
        RequestSpecification request = RestAssured.given().contentType("application/json");

        JsonObject orderRequest = payloadProccessing.getJsonObjectFromFile("ProcessOrder.json");
        orderRequest.remove("order_status");
        request.body(orderRequest.toString());
        Response response = request.post(OrderRequestEndPoint.getEND_POINT_PROCESS_ORDER());

        Assert.assertEquals(response.getStatusCode(), 400);
        JSONObject object = new JSONObject(response.getBody().asString());
        Assert.assertEquals(object.getString("message"), "Invalid order status");
    }

    @Test
    public void testingNoOrderTimeStampParam(){
        PayloadProccessing payloadProccessing = new PayloadUtil();
        RestAssured.baseURI = OrderRequestEndPoint.getBASE_URL();
        RequestSpecification request = RestAssured.given().contentType("application/json");

        JsonObject orderRequest = payloadProccessing.getJsonObjectFromFile("ProcessOrder.json");
        orderRequest.remove("last_updated_timestamp");
        request.body(orderRequest.toString());
        Response response = request.post(OrderRequestEndPoint.getEND_POINT_PROCESS_ORDER());

        JSONObject object = new JSONObject(response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(), 400);
        Assert.assertEquals(object.getString("message"),"Invalid last updated timestamp");
    }


    @Test
    public void testingInvalidSpecialOrderParamDataType(){
        PayloadProccessing payloadProccessing = new PayloadUtil();
        RestAssured.baseURI = OrderRequestEndPoint.getBASE_URL();
        RequestSpecification request = RestAssured.given().contentType("application/json");

        JsonObject orderRequest = payloadProccessing.getJsonObjectFromFile("ProcessOrder.json");
        orderRequest = payloadProccessing.replacePropertyValue(orderRequest, "special_order", "False");
        request.body(orderRequest.toString());
        Response response = request.post(OrderRequestEndPoint.getEND_POINT_PROCESS_ORDER());

        Assert.assertEquals(response.getStatusCode(), 400);
        JSONObject object = new JSONObject(response.getBody().asString());
        Assert.assertEquals(object.getString("message").equals("Invalid request body"), true);
    }

}

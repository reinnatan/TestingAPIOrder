import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

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

        //modify the request default payload
        request.body(orderRequest.toString());
        Response response = request.post(OrderRequestEndPoint.getEND_POINT_PROCESS_ORDER());

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.getBody().asString().contains("Processing"), true);
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
        Assert.assertEquals(response.getBody().asString().contains("Invalid order id"), true);
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
        Assert.assertEquals(response.getBody().asString().contains("Invalid order status"), true);
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

        Assert.assertEquals(response.getStatusCode(), 400);
        Assert.assertEquals(response.getBody().asString().contains("Invalid last updated timestamp"), true);
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
        Assert.assertEquals(response.getBody().asString().contains("Invalid request body"), true);
    }

}

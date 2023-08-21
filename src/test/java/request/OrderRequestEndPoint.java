package request;


import lombok.Getter;

//class that request any
public class OrderRequestEndPoint {

    @Getter
    private static final String BASE_URL= "http://localhost:3000";

    @Getter
    private static final String END_POINT_PROCESS_ORDER = "/processOrder";


}

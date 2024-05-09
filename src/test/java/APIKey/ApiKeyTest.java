package APIKey;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class ApiKeyTest {

    /*
    anahtar yöntemi: barrear token
Api Key yöntemi :rtrtrgfgfgdf54ggh
endpoint: https://l9njuzrhf3.execute-api.eu-west-1.amazonaws.com/prod/user
ApiKey in Key ve Values i  vardır
Key : x-api-key
Values: GwMco9Tpstd5vbzBzlzW9I7hr6E1D7w2zEIrhOra
     */

    @Test
    public void ApiKeyTesting() {

        given()

                .header("x-api-key", "GwMco9Tpstd5vbzBzlzW9I7hr6E1D7w2zEIrhOra")


                .when()
                .get("https://l9njuzrhf3.execute-api.eu-west-1.amazonaws.com/prod/user")

                .then()
                .log().body()
        ;

    }
}

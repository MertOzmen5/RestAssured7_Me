package GoRest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.*;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class GoRestUsersTest {

    // Token ı aldım ,
    // usersCreate için neler lazım, body(user bilgileri)
    // endpointi aldım gidiş metodu
    // BeforeClass ın içinde yapılacaklar var mı? nelerdir ? url set ve spec hazırlanmalı

    @BeforeClass
    public void SetUp(){

        baseURI="https://gorest.co.in/public/v2/users";

        RequestSpecification reqSpec=new RequestSpecBuilder()
                .addHeader("Authorization","Bearer 458e27862be6224aba47617b37b7d5b865dcb3e33f80e80494329a1abf9dd949")
                .setContentType(ContentType.JSON)
                .build();
    }
}

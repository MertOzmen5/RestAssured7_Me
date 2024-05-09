package GoRest;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.*;

import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;

public class _07_GoRestUsersTest {

    // Token ı aldım ,
    // usersCreate için neler lazım, body(user bilgileri)
    // endpointi aldım gidiş metodu
    // BeforeClass ın içinde yapılacaklar var mı? nelerdir ? url set ve spec hazırlanmalı

    RequestSpecification reqSpec;
    Faker randomUreteci = new Faker();
    String rndFullName;
    String rndEmail;
    int userID;


    @BeforeClass
    public void SetUp() {

        baseURI = "https://gorest.co.in/public/v2/users";

        reqSpec = new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer 458e27862be6224aba47617b37b7d5b865dcb3e33f80e80494329a1abf9dd949")
                .setContentType(ContentType.JSON)
                .build();
    }
    // Create User Testini yapınız

    @Test
    public void CreateUser(){
        Map<String,String> user=new HashMap<>();
        rndFullName=randomUreteci.name().fullName();
        rndEmail=randomUreteci.internet().emailAddress();


        user.put("name",rndFullName);
        user.put("email",rndEmail);
        user.put("gender","male");
        user.put("status","active");

        userID=
        given()
                .spec(reqSpec)
                .body(user)

                .when()
                .post()

                .then()
                .log().body()
                .statusCode(201)
                .extract().path("id");

    }

    // GetUserById testini yapınız.

    @Test(dependsOnMethods = "CreateUser")
    public void GetUserByID(){

        given()
                .spec(reqSpec)

                .when()
                .get("/"+userID)

                .then()
                .log().body()
                .statusCode(200)
                .body("id",equalTo(userID));
    }

    // UpdateUser testini yapınız.

    @Test(dependsOnMethods = "GetUserByID")
    public void updateUser(){
        String updName=randomUreteci.name().fullName();
        Map<String,String>user=new HashMap<>();
        user.put("name",updName);
        user.put("email",rndEmail);
        user.put("gender","male");
        user.put("status","active");


        given()
                .spec(reqSpec)
                .body(user)

                .when()
                .put("/"+userID)

                .then()
                .log().body()
                .statusCode(200)
                .body("name",equalTo(updName));
    }

    // DeleteUser testini yapınız.

    @Test(dependsOnMethods = "updateUser")
    public void DeleteUser(){

        given()
                .spec(reqSpec)


                .when()
                .delete("/"+userID)

                .then()
                .log().body()
                .statusCode(204)
                ;

    }

    @Test(dependsOnMethods = "DeleteUser")
    public void DeleteUserNegative(){

        given()
                .spec(reqSpec)


                .when()
                .delete("/"+userID)

                .then()
                .log().body()
                .statusCode(404)
        ;
    }
}

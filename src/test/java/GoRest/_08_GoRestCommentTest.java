package GoRest;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.*;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class _08_GoRestCommentTest {

    RequestSpecification reqSpec;
    Faker randomUreteci = new Faker();
    int commentID;

    @BeforeClass
    public void SetUp() {

        baseURI = "https://gorest.co.in/public/v2/comments";

        reqSpec = new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer 458e27862be6224aba47617b37b7d5b865dcb3e33f80e80494329a1abf9dd949")
                .setContentType(ContentType.JSON)
                .build();
    }
    // Soru : CreateComment testini yapınız.

    @Test
    public void CreateComment(){
        String fullName=randomUreteci.name().fullName();
        String email=randomUreteci.internet().emailAddress();
        String body=randomUreteci.lorem().paragraph();
        String postID="122490";

        Map<String,String>newComment=new HashMap<>();
        newComment.put("name",fullName);
        newComment.put("email",email);
        newComment.put("body",body);
        newComment.put("post_id",postID);

        commentID=
        given()
                .spec(reqSpec)
                .body(newComment)


                .when()
                .post()


                .then()
                .log().body()
                .statusCode(201)
                .extract().path("id");

        System.out.println("commentID = " + commentID);
    }

    // Soru : Create edilen Commenti GetCommentByID testi ile çağırarak id sinin kontrolünü yapınız.

    @Test(dependsOnMethods = "CreateComment")
    public void GetCommentByID(){

        given()
                .spec(reqSpec)


                .when()
                .get(""+commentID)


                .then()
                .statusCode(200)
                .body("id",equalTo(commentID))


                ;
    }
}

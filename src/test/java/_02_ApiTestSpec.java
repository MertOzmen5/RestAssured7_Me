import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class _02_ApiTestSpec {

    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;

    @BeforeClass
    public void Setup() {
        baseURI = "https://gorest.co.in/public/v1" ; // when kısmı için
        // hazırda tanımlanmış RESTASSURED e ait değişken bu http yoksa otomatik olarak ekleniyor baş tarafa

        requestSpec = new RequestSpecBuilder() // given için
                .setContentType(ContentType.JSON)
                .log(LogDetail.URI) // linki gösteriyor. yani log().uri() nin aynısı
                .build();

        responseSpec = new ResponseSpecBuilder() // then için
                .expectStatusCode(200) // status code 200 e göre ayarlar
                .log(LogDetail.BODY) // body döndürüyor,liste şeklinde yazdırır. log().body()
                .expectContentType(ContentType.JSON) // Yazı tipi JSON ayarlıyor
                .build();

    }

    @Test
    public void Test1() {
        given()


                // request özellikleri
                .spec(requestSpec)
                .when()
                .get("/users")// başında http yok ise baseURI geçerli
                // https://gorest.co.in/public/v1/users

                .then()
                // test özellikleri yani response
                .spec(responseSpec)
        ;
    }

}

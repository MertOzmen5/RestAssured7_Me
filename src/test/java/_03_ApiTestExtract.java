import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.*;


public class _03_ApiTestExtract {

    @Test
    public void extractingJsonPath() {
        baseURI = "http://api.zippopotam.us";

        String ulkeAdi =
                given()


                        .when()
                        .get("/us/90210")


                        .then()
                        .log().body()
                        .extract().path("country"); // PATH i country olan değeri EXTRACT yap

        System.out.println("ulkeAdi = " + ulkeAdi);
        Assert.assertEquals(ulkeAdi, "United States");

    }

    @Test
    public void extractingJsonPath2() {

        // Soru : "http://api.zippopotam.us/us/90210"  endpoint indne dönen
        // place dizisinin ilk elemanının state değerinin  "California"
        // olduğunu testNG Assertion ile doğrulayınız

        baseURI = "http://api.zippopotam.us";

        String stateName =
                given()


                        .when()
                        .get("/us/90210")


                        .then()
                        .extract().path("places[0].state");

        System.out.println("stateName = " + stateName);
        Assert.assertEquals(stateName, "California");
    }

    @Test
    public void extractingJsonPath3() {

        // Soru : "http://api.zippopotam.us/us/90210"  endpoint indne dönen
        // place dizisinin ilk elemanının place name değerinin  "Beverly Hills"
        // olduğunu testNG Assertion ile doğrulayınız

        baseURI = "http://api.zippopotam.us";

        String placeName =
                given()


                        .when()
                        .get("/us/90210")


                        .then()
                        .extract().path("places[0].'place name'");

        System.out.println("placeName = " + placeName);
        Assert.assertEquals(placeName, "Beverly Hills");
    }

    @Test
    public void extractingJsonPath4() {

        // Soru : "https://gorest.co.in/public/v1/users"  endpoint in den dönen
        // limit bilgisinin 10 olduğunu testNG ile doğrulayınız.


        int limit =
                given()


                        .when()
                        .get("https://gorest.co.in/public/v1/users")


                        .then()
                        .log().body()
                        .extract().path("meta.pagination.limit"); // burada limit int çünkü çift tırnak içinde yazılmamış

        Assert.assertEquals(limit, 10);

    }

    @Test
    public void extractingJsonPath5() {

        List<Integer> idler =
                given()


                        .when()
                        .get("https://gorest.co.in/public/v1/users")


                        .then()
                        .log().body()
                        .extract().path("data.id"); // id lerin yer aldığı array

        System.out.println("idler = " + idler);
    }

    @Test
    public void extractingJsonPath6() {

        // Soru : "https://gorest.co.in/public/v1/users"  endpoint in den dönen
        // bütün name leri yazdırınız.

        List<String> names =
                given()


                        .when()
                        .get("https://gorest.co.in/public/v1/users")


                        .then()
                        .extract().path("data.name"); // name lerin yer aldığı array

        System.out.println("names = " + names);
    }

    @Test
    public void extractingJsonPathResponseAll() {

        Response donenData = // pm.Response.Json
                given()


                        .when()
                        .get("https://gorest.co.in/public/v1/users")


                        .then()
                        .extract().response();
        List<Integer> idler=donenData.path("data.id");
        List<String> names=donenData.path("data.name");
        int limit=donenData.path("meta.pagination.limit");

        System.out.println("idler = " + idler);
        System.out.println("names = " + names);
        System.out.println("limit = " + limit);

        Assert.assertTrue(idler.contains(6880124));
        Assert.assertTrue(names.contains("The Hon. Divya Kakkar"));
        Assert.assertTrue(limit==10);
    }
}

package Campus;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;import io.restassured.http.Cookies;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

import static io.restassured.RestAssured.baseURI;

public class _06_CountryTest {

    RequestSpecification reqSpec; // Bütün yerlerde kullanabilelim diye burada tanımladık.
    Faker randomUreteci = new Faker(); // Bütün yerlerde kullanabilelim diye burada tanımladık. Fake ülke adı  vs üretiyor
    String countryName = "";
    String countryCode = "";
    String countryID;


    @BeforeClass // hep ilk başta çalışsın diye yaptık.Bütün testlerden önce burasının çalışması lazım.Cookies alıyor
    // O yüzden buranın ilk başta çalışması lazım ki cookie yi alalım
    public void LoginCampus() {
        baseURI = "https://test.mersys.io";
        // Token cookies içinde geldi
        // Cookies i alacağım
        // Request speci hazırlayacağım
        // Environment variable : baseURI
        // {"username": "turkeyts", "password": "TechnoStudy123","rememberMe": "true"}

        String userCredential = "{\"username\": \"turkeyts\", \"password\": \"TechnoStudy123\",\"rememberMe\": \"true\"}";

        Map<String, String> userCredMap = new HashMap<>();
        userCredMap.put("username", "turkeyts");
        userCredMap.put("password", "TechnoStudy123");
        userCredMap.put("rememberMe", "true");

        Cookies gelenCookies =
                given()
                        //  .body(userCredential) // Body bilgilerini yolladık.Username , password vs . .
                        .body(userCredMap) // bir üstün aynısı ,ama ona göre daha kolay yol.Bilgileri Map e atıyoruz ve
                        // kullanıyoruz.Parantez,tırnak işareti vs uğraşmıyoruz.Eğer Map deki value ların bir kısmı String bir
                        // kısmı Integer ise Map deki value tarafını Object yaparsın.
                        .contentType(ContentType.JSON) // JSON formatında Giden Body i gönderiyoruz.Postman de de böyle yaptık

                        .when()
                        .post("/auth/login") // get olmamasının sebebi Postman da Login yaparken Post kullanıldı.
                        .then()
                        .log().all() // Body dahil ne var ne yok görelim MetaData vs .. Cookieleri de görüyoruz bu sayede.
                        .statusCode(200)
                        .extract().response().getDetailedCookies() // Cookie yi dışarı çıkarıp aldım.Önceden response.body
                ;
        // diyip Body i alıyorduk burada Cookie yi aldık.Çünkü Cookie lazım olacak bize hep onu diğer yerlerde de
        // kullanacağız.

        System.out.println("gelenCookies = " + gelenCookies);

        reqSpec = new RequestSpecBuilder()
                .addCookies(gelenCookies)
                .setContentType(ContentType.JSON)
                .build(); // Hazırlık aşaması yani given kısmı için bunları bir bütün olarak Spec aracalığıyla hafızaya
        // attık ki her yerde otomatik olarak kullanalım

    }

    @Test
    public void CreateCountry() {
        // Burada gelen token ın yine cookies içinde geri gitmesi lazım : spec
        countryName = randomUreteci.address().country() + randomUreteci.address().countryCode()
                + randomUreteci.address().latitude();
        countryCode = randomUreteci.address().countryCode();

        Map<String, String> newCountry = new HashMap<>();
        newCountry.put("name", countryName);
        newCountry.put("code", countryCode);

        // Not : Spec bilgileri givendan hemen sonra yazılmalı !!


        countryID=
        given()

                .spec(reqSpec) // gelen cookies , yeni istek için Login olduğumuzun kanıtı olarak gönderildi.
                .body(newCountry)
                .when()
                .post("/school-service/api/countries")

                .then()
                .log().body()
                .statusCode(201)
                .extract().path("id")

        ;

    }

    @Test(dependsOnMethods = "CreateCountry")
    public void CreateCountryNegative() {
        // Yukarıda gönderilen name ve codu tekrar göndererek kayıt yapılamadığını doğrulayınız.
        // Burada gelen token ın yine cookies içinde geri gitmesi lazım : spec

        Map<String, String> reNewCountry = new HashMap<>();
        reNewCountry.put("name", countryName);
        reNewCountry.put("code", countryCode);

        given()

                .spec(reqSpec)
                .body(reNewCountry)

                .when()
                .post("/school-service/api/countries")

                .then()
                .log().body()
                .statusCode(400)
                ;
    }

    @Test(dependsOnMethods = "CreateCountryNegative")
    public void UpdateCountry() {
        // Yukarıda create edilen ülkenin adını bir başka ülke adı ile güncelleyiniz.

        String updCountryName="Mert"+randomUreteci.address().country()+
                randomUreteci.address().latitude();


        Map<String, String> updCountry = new HashMap<>();
        updCountry.put("id", countryID);
        updCountry.put("name", updCountryName);
        updCountry.put("code", countryCode);

        String name=
        given()
                .spec(reqSpec)
                .body(updCountry)


                .when()
                .put("/school-service/api/countries/")

                .then()
                .log().body()
                .statusCode(200)
                .extract().path("name")
                ;

        Assert.assertEquals(name,updCountryName); // gönderdiğimiz ülke adının , dönen body deki ülke adıyla aynı
    }

    @Test(dependsOnMethods = "UpdateCountry")
    public void deleteCountry(){

        given()
                .spec(reqSpec)

                .when()
                .delete("/school-service/api/countries/"+countryID)

                .then()
                .log().body()
                .statusCode(200)
        ;
    }

    @Test(dependsOnMethods = "deleteCountry")
    public void DeleteCountryNegative(){

        given()
                .spec(reqSpec)

                .when()
                .delete("/school-service/api/countries/"+countryID)

                .then()
                .log().body()
                .statusCode(400);
    }
}

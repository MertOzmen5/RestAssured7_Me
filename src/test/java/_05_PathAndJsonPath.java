import Model.Location;
import Model.Place;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.*;


public class _05_PathAndJsonPath {

    @Test
    public void extractingJsonPath(){

        // Gelen Body i dışarı almanın 2 yöntemini gördük
        // .extract.path("") ,  as(Todo.Class)

        int postcode=
        given()


                .when()
                .get("http://api.zippopotam.us/us/90210")


                .then()
                .log().body()
                .extract().jsonPath().getInt("'post code'")
                // tip dönüşümü otomatik, uygun tip verilmeli
                ;
        System.out.println("postcode = " + postcode);

    }
    
    @Test
    public void getZipCode(){

        Response response=
        given()
        
                .when()
                .get("http://api.zippopotam.us/us/90210")
                
                .then()
                .log().body()
                .extract().response();

        Location locationAsPath=response.as(Location.class); // Bütün class yapısını yazmak zorundayız
        System.out.println("locationAsPath.getPlaces() = " + locationAsPath.getPlaces());
        // Bana sadece places dizisi lazım olsa bile , diğer bütün fieldları da oluştururken yazmak zorundayım.

        List<Place> places=response.jsonPath().getList("place",Place.class);
        // Sadece Place dizisi lazım ise diğerlerini yazmak zorunda değilsin.

        // Daha önceki örneklerde (as) Clas dönüşümleri için tüm yapıya karşılık gelen
        // gereken tüm classları yazarak dönüştürüp istediğimiz elemanlara ulaşıyorduk.

        // Burada ise(JsonPath) aradaki bir veriyi clasa dönüştürerek bir list olarak almamıza
        // imkan veren JSONPATH i kullandık.Böylece tek class ile veri alınmış oldu
        // diğer class lara gerek kalmadan

        // path : class veya tip dönüşümüne imkan veremeyen direk veriyi verir. List<String> gibi
        // jsonPath : class dönüşümüne ve tip dönüşümüne izin vererek , veriyi istediğimiz formatta verir.
    }

    @Test
    public void Task1(){

        // https://gorest.co.in/public/v1/users  endpointte dönen Sadece Data Kısmını POJO
        // dönüşümü ile alarak yazdırınız.

        Response response=
        given()


                .when()
                .get("https://gorest.co.in/public/v1/users")

                .then()
                .log().body()
                .extract().response();
    }
}

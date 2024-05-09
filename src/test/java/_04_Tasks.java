
import Model.ToDo;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;

public class _04_Tasks {
    /**
     * Task 1
     * create a request to https://jsonplaceholder.typicode.com/todos/2
     * expect status 200
     * expect content type JSON
     * expect title in response body to be "quis ut nam facilis et officia qui"
     */

    @Test
    public void Test1() {

        // String title=
        given()


                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")


                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("title", equalTo("quis ut nam facilis et officia qui"));
        // .extract().path("title"); ya da böyle dışarı çıkararak yaparsın

        // Assert.assertEquals(title,"quis ut nam facilis et officia qui");

    }

    @Test
    public void Test2(){
        /**

         Task 2
         create a request to https://jsonplaceholder.typicode.com/todos/2
         expect status 200
         expect content type JSON
         *a) expect response completed status to be false(hamcrest)
         *b) extract completed field and testNG assertion(testNG)*/

        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")


                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("completed", equalTo(false));


        // EXTRACT

        Boolean falseMu=
        given()


                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")


                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().path("completed");

       // Assert.assertEquals(falseMu,false);
        Assert.assertFalse(falseMu);
    }

    @Test
    public void Test3() {

        /** Task 3

         create a request to https://jsonplaceholder.typicode.com/todos/2
         expect status 200
         Converting Into POJO*/


        ToDo userPOJO =
                given()


                        .when()
                        .get("https://jsonplaceholder.typicode.com/todos/2")


                        .then()
                        .statusCode(200)
                        .extract().body().as(ToDo.class);

        System.out.println("userPOJO özellikler = " + userPOJO);
    }

}

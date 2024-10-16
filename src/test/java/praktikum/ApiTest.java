package praktikum;

import io.restassured.http.ContentType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class ApiTest {

    @Test
    public void deleteStellarUser() {
        given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer ...")
                .baseUri("https://stellarburgers.nomoreparties.site")
                .when()
                .delete("/api/auth/user")
                .then().log().all()
                .assertThat()
                .statusCode(202);
    }

    @Test
    public void createOrder() {
        IngredientsDto dto = new IngredientsDto();
        dto.add("61c0c5a71d1f82001bdaaa6d");
        dto.add("61c0c5a71d1f82001bdaaa72");

        given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer ...")
                .baseUri("https://stellarburgers.nomoreparties.site")
                .body(dto)
                .when()
                .post("/api/orders")
                .then().log().all()
                .assertThat()
                .statusCode(200);
    }
}

class IngredientsDto {
    private List<String> ingredients = new ArrayList<>();

    public List<String> getIngredients() {
        return ingredients;
    }

    public void add(String id) {
        ingredients.add(id);
    }
}

package praktikum;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v113.network.Network;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;

public class SystemTest {

    private WebDriver driver;

    @Before
    public void setUpChrome() {
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        ChromeDriverService service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File("/opt/chromedriver/chromedriver"))
                .build();

        ChromeOptions options = new ChromeOptions()
                .setBinary("/opt/chrome-for-testing/chrome");

        driver = new ChromeDriver(service, options);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void switchTabs() {
        driver.get("https://stellarburgers.nomoreparties.site/login");

        var ingredientTab = By.cssSelector(".tab_tab__1SPyG:nth-child(3)");
        driver.findElement(ingredientTab).click();


        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.attributeContains(By.xpath("..."), "class", "current"));

//        new WebDriverWait(driver, Duration.ofSeconds(10))
//                .until(driver -> {
//                    return driver.findElement(By.xpath("")).getRect().x < 300;
//                });
    }

    @Test
    public void fetchAuthTokenFromLocalStorage() {
        driver.get("https://stellarburgers.nomoreparties.site/login");

        driver.findElement(By.cssSelector("input[name='name']")).sendKeys("uasya@pupkin.dev");
        driver.findElement(By.cssSelector("input[name='Пароль']")).sendKeys("pupkin");
        driver.findElement(By.cssSelector("form button")).click();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.numberOfElementsToBeMoreThan(
                        By.cssSelector("a[class^='BurgerIngredient']"), 2));

        LocalStorage localStorage = ((WebStorage) driver).getLocalStorage();
        String accessToken = localStorage.getItem("accessToken");
        System.out.println(accessToken);
    }

    @Test
    public void deleteStellarUser() {
        given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY1M2NjYWVjOWVkMjgwMDAxYjM3NjJhYiIsImlhdCI6MTY5ODQ4MjkyNiwiZXhwIjoxNjk4NDg0MTI2fQ.G4JV91gowD_V6lZY7fDH7FB8Q6RcHvR6dOT0M6hKFQA")
                .baseUri("https://stellarburgers.nomoreparties.site")
                .when()
                .delete("/api/auth/user")
                .then().log().all()
                .assertThat()
                .statusCode(202);
    }

    @Test
    public void captureRequest() throws InterruptedException {
        driver.get("https://stellarburgers.nomoreparties.site/login");

        DevTools devTools = ((ChromeDriver) driver).getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

        BlockingQueue<String> q = new LinkedBlockingQueue<>();

        devTools.addListener(Network.responseReceived(), r -> {
            if (r.getResponse().getUrl().contains("auth")) {
                Network.GetResponseBodyResponse response = devTools.send(Network.getResponseBody(r.getRequestId()));
                q.add(response.getBody());
            }
        });

        driver.findElement(By.cssSelector("input[name='name']")).sendKeys("uasya@pupkin.dev");
        driver.findElement(By.cssSelector("input[name='Пароль']")).sendKeys("pupkin");
        driver.findElement(By.cssSelector("form button")).click();

        String authReply = q.poll(10, TimeUnit.SECONDS);
        System.out.println("authReply = " + authReply);

        devTools.clearListeners();
    }
}

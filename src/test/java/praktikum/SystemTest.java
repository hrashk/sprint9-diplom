package praktikum;

import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v122.network.Network;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class SystemTest {
    @Rule
    public DriverRule driverRule = new DriverRule();

    @Test
    public void switchTabs() {
        WebDriver driver = driverRule.getDriver();
        logIn(driver);

        var ingredientTab = By.cssSelector(".tab_tab__1SPyG:nth-child(3)");

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(ingredientTab));

        driver.findElement(ingredientTab).click();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.attributeContains(ingredientTab, "class", "current"));

//        new WebDriverWait(driver, Duration.ofSeconds(10))
//                .until(driver -> {
//                    return driver.findElement(By.xpath("")).getRect().x < 300;
//                });
    }

    @Test
    public void fetchAuthTokenFromLocalStorage() {
        WebDriver driver = driverRule.getDriver();
        logIn(driver);

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.numberOfElementsToBeMoreThan(
                        By.cssSelector("a[class^='BurgerIngredient']"), 2));

        LocalStorage localStorage = ((WebStorage) driver).getLocalStorage();
        String accessToken = localStorage.getItem("accessToken");
        System.out.println(accessToken);
    }

    private static void logIn(WebDriver driver) {
        driver.get("https://stellarburgers.nomoreparties.site/login");

        By loginButton = By.cssSelector("form button");
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(loginButton));

        driver.findElement(By.cssSelector("input[name='name']")).sendKeys("uasya@pupkin.dev");
        driver.findElement(By.cssSelector("input[name='Пароль']")).sendKeys("pupkin");
        driver.findElement(loginButton).click();
    }

    @Test
    public void captureRequest() throws InterruptedException {
        WebDriver driver = driverRule.getDriver();
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

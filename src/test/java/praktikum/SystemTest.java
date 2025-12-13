package praktikum;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v142.network.Network;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class SystemTest {
    @RegisterExtension
    private DriverExtension ext = new DriverExtension();

    @Test
    public void switchTabs() {
        WebDriver driver = ext.getDriver();
        logIn(driver);

        var ingredientTab = By.cssSelector(".tab_tab__1SPyG:nth-child(3)");

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(ingredientTab));

        // https://github.com/awaitility/awaitility/wiki/Usage
//        Awaitility.await()
//                .atMost(30, TimeUnit.SECONDS)
//                .pollInterval(500, TimeUnit.MILLISECONDS)
//                .until(() -> click());

        driver.findElement(ingredientTab).click();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.attributeContains(ingredientTab, "class", "current"));

//        new WebDriverWait(driver, Duration.ofSeconds(10))
//                .until(d -> {
//                    return d.findElement(By.xpath("...")).getRect().x < 300;
//                });
    }

    @Test
    public void fetchAuthTokenFromLocalStorage() {
        RemoteWebDriver driver = ext.getDriver();
        driver.get("https://stellarburgers.education-services.ru/login");
        logIn(driver);

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.numberOfElementsToBeMoreThan(
                        By.cssSelector("a[class^='BurgerIngredient']"), 2));

        String accessToken = (String) driver.executeScript(
                "return localStorage.getItem(arguments[0]);", "accessToken");
        System.out.println(accessToken);
    }

    private static void logIn(WebDriver driver) {
        By loginButton = By.cssSelector("form button");
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(loginButton));

        driver.findElement(By.cssSelector("input[name='name']")).sendKeys("uasya@pupkin.dev");
        driver.findElement(By.cssSelector("input[name='Пароль']")).sendKeys("pupkin");
        driver.findElement(loginButton).click();
    }

    @Test
    public void captureRequest() throws InterruptedException {
        WebDriver driver = ext.getDriver();
        driver.get("https://stellarburgers.education-services.ru/login");

        DevTools devTools = ((HasDevTools) driver).getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()));

        BlockingQueue<String> q = new LinkedBlockingQueue<>();

        devTools.addListener(Network.responseReceived(), r -> {
            if (r.getResponse().getUrl().contains("auth")) {
                Network.GetResponseBodyResponse response = devTools.send(Network.getResponseBody(r.getRequestId()));
                q.add(response.getBody());
            }
        });

        logIn(driver);

        String authReply = q.poll(10, TimeUnit.SECONDS);
        System.out.println("authReply = " + authReply);

        devTools.clearListeners();
    }
}

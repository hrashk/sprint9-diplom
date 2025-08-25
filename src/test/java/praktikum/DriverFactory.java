package praktikum;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {
    private WebDriver driver;

    public void initDriver() {
        if ("firefox".equals(System.getProperty("browser"))) {
            setupFirefox();
        } else if ("yandex".equals(System.getProperty("browser"))) {
            initYandex();
        } else {
            setupChrome();
        }

        // Selenide
//        WebDriverRunner.setWebDriver(driver);
    }

    public void setupChrome() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

    }

    public void setupFirefox() {
        WebDriverManager.firefoxdriver().setup();
        var opts = new FirefoxOptions().setBinary("/usr/bin/firefox");

        driver = new FirefoxDriver(opts);
    }

    private void initYandex() {
        WebDriverManager.chromedriver().driverVersion(System.getProperty("driver.version")).setup();

        var options = new ChromeOptions();
        options.setBinary(System.getProperty("webdriver.yandex.bin"));

        driver = new ChromeDriver(options);
    }

    public WebDriver getDriver() {
        return driver;
    }
}

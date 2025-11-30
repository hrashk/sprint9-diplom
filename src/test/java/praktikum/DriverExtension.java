package praktikum;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverExtension implements BeforeEachCallback, AfterEachCallback {
    private DriverFactory factory = new DriverFactory();

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        factory.getDriver().quit();
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        factory.initDriver();
    }

    public RemoteWebDriver getDriver() {
        return factory.getDriver();
    }

}

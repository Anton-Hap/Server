package utils;

import constants.Data;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public abstract class BrowserUtils {

    private static ChromeDriver driverChrome;
    private static EdgeDriver driverEdge;

    public static void run() {
        switch (Data.BROWSER) {
            case "Chrome" : {
                ChromeOptions options = new ChromeOptions();
                options.setHeadless(true);
                options.setCapability(ChromeOptions.CAPABILITY, options);
                System.setProperty("webdriver.chrome.driver","src/main/resources/drivers/chromedriver.exe");
                driverChrome = new ChromeDriver(options);
                break;
            }
            case "Edge": {
                EdgeOptions options = new EdgeOptions();
                options.setHeadless(true);
                options.setCapability(EdgeOptions.CAPABILITY, options);
                System.setProperty("webdriver.edge.driver","src/main/resources/drivers/msedgedriver.exe");
                driverEdge = new EdgeDriver(options);
                break;
            }
        }
    }

    public static void executeScript(String script, WebElement element) {
        ((JavascriptExecutor) getDriver()).executeScript(script, element);
    }

    public static void goTo(String url) {
        getDriver().get(url);
    }

    public static WebDriver getDriver() {
        switch (Data.BROWSER) {
            case "Chrome" : {
                return driverChrome;
            }
            case "Edge" : {
                return driverEdge;
            }
        }

        return null;
    }

    public static void quit() {
        switch (Data.BROWSER) {
            case "Chrome" : {
                driverChrome.quit();
                driverChrome = null;
                break;
            }
            case "Edge" : {
                driverEdge.quit();
                driverEdge = null;
                break;
            }
        }
    }
}

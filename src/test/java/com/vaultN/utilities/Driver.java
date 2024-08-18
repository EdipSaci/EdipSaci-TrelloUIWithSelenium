package com.vaultN.utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;

public class Driver {
    //Creating a private constructor, so we are closing access to the object of this class from outside the class

    private Driver() {
    }

    // We make WebDriver private, because we want to close access from outside the class.
    // We make it static because we will use it in a static method.
    private static InheritableThreadLocal<WebDriver> driverPool = new InheritableThreadLocal<>();

    //Create a re-usable utility method which will return same driver instance when we call it
    public static WebDriver getDriver() {
        if (driverPool.get() == null) {
            String browserType = null;
            // We put following statement for specifying driver type with maven command line if we need. Otherwise we
            // already have determined driver type in configuration.properties file
            if (System.getProperty("BROWSER") == null) {
                browserType = ConfigurationReader.getProperty("browser");
            } else {
                browserType = System.getProperty("BROWSER");
            }
            /*
                Depending on the browserType that will be return from configuration.properties file
                switch statement will determine the case, and open the matching browser
            */
            if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                System.setProperty("webdriver.chrome.driver", "drivers/chromedriver-mac-x64/chromedriver");
            } else if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                System.setProperty("webdriver.chrome.driver", "drivers/chromedriver-win64/chromedriver.exe");
            }
            switch (browserType) {
                case "chrome":
                    driverPool.set(new ChromeDriver());
                    break;
                case "chrome-headless":
                    ChromeOptions options2 = new ChromeOptions();
                    options2.setHeadless(true);
                    driverPool.set(new ChromeDriver(options2));
                    break;
                case "firefox":
                    driverPool.set(new FirefoxDriver());
                    break;
                case "firefox-headless":
                    driverPool.set(new FirefoxDriver(new FirefoxOptions().setHeadless(true)));
                    break;
                case "ie":
                    if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                        throw new WebDriverException("Your operating system does not support the requested browser");
                    }
                    driverPool.set(new InternetExplorerDriver());
                    break;
                case "edge":
                    if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                        throw new WebDriverException("Your operating system does not support the requested browser");
                    }
                    driverPool.set(new EdgeDriver());
                    break;
                case "safari":
                    if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                        throw new WebDriverException("Your operating system does not support the requested browser");
                    }
                    driverPool.set(new SafariDriver());
                    break;

            }
        }
        return driverPool.get();
    }

    // This method will make sure our driver value is always null after using quit() method
    public static void closeDriver() {
        if (driverPool.get() != null) {
            driverPool.get().quit(); // this line will terminate the existing session. value will not even be null
            driverPool.remove();
        }
    }

    public static void releaseDriver() {
        if (driverPool.get() != null) {
            //driverPool.get().quit(); // this line will terminate the existing session. value will not even be null
            driverPool.remove();
        }
    }

}
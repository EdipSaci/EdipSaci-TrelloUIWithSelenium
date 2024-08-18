package com.vaultN.utilities;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.*;

public class BrowserUtils {

    /**
    This method will accept int (in seconds) and execute Thread.sleep
    for given duration
    */
    public static void sleep(int second){
        second *=1000;
        try {
            Thread.sleep(second);
        }catch (InterruptedException e ) {

        }
    }

    /**
     * Waits until the current URL contains the specified keyword.
     *
     * @param keyword The keyword to be present in the URL.
     * @param timeout The maximum time to wait for the condition.
     * @throws TimeoutException if the URL does not contain the keyword within the specified time.
     */
    public static void waitForUrlContains(String keyword, Duration timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), timeout);
        wait.until(ExpectedConditions.urlContains(keyword));
    }

    /**
     * Verifies that the current URL contains the provided keyword.
     *
     * @param keyword The keyword that should be present in the URL.
     * @throws AssertionError if the URL does not contain the expected keyword.
     */
    public static void verifyUrlContainsKeyword(String keyword) {
        // Get the current URL
        String currentUrl = Driver.getDriver().getCurrentUrl();
        System.out.println(currentUrl);

        // Assert that the URL contains the keyword
        assertTrue("The URL does not contain the expected keyword: " + keyword, currentUrl.contains(keyword));
    }



    /**
     * Waits for the provided element to be clickable and then clicks it.
     *
     * @param element The WebElement to wait for and click.
     * @param timeout The maximum amount of time to wait for the element to become clickable.
     * @throws TimeoutException if the element is not clickable within the provided timeout.
     * @throws ElementClickInterceptedException if the element cannot be clicked (e.g., due to being covered by another element).
     */
    public static void clickWhenClickable(WebElement element, Duration timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), timeout);
            WebElement clickableElement = wait.until(ExpectedConditions.elementToBeClickable(element));
            clickableElement.click();
        } catch (TimeoutException e) {
            throw new AssertionError("Element was not clickable within the given timeout: " + element, e);
        } catch (ElementClickInterceptedException e) {
            throw new AssertionError("Element could not be clicked due to interception: " + element, e);
        }
    }




    /**
     * Waits for the provided element to be visible on the page.
     *
     * @param element The WebElement to wait for visibility
     * @param timeToWaitInSec The maximum time to wait in seconds
     * @return The WebElement once it is visible
     */
    public static WebElement waitForVisibility(WebElement element, int timeToWaitInSec) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeToWaitInSec));
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Verifies whether the element is displayed on page
     *
     * @param element
     * @throws AssertionError if the element is not found or not displayed
     */
    public static void verifyElementDisplayed(WebElement element) {
        try {
            assertTrue("Element not visible: " + element, element.isDisplayed());
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            Assert.fail("Element not found: " + element);

        }
    }


    /**
     * Verifies whether the element matching the provided locator is NOT displayed on page
     *
     * @param element
     * @throws AssertionError the element matching the provided locator is displayed
     */
    public static void verifyElementNotDisplayed(WebElement element) {
        try {
            Assert.assertFalse("Element should not be visible: " + element, element.isDisplayed());
        } catch (NoSuchElementException e) {
            e.printStackTrace();

        }
    }



}

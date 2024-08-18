package com.vaultN.pages;

import com.vaultN.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class BoardPage extends BasePage{

    @FindBy(css = "button[type='submit']")
    public WebElement addListButton;

    @FindBy(xpath = "//textarea[@placeholder='Enter list name…']")
    public WebElement enterListNameBox;

    @FindBy(css = "button[type='submit']")
    public WebElement addCardButton;


    @FindBy(xpath = "//button[normalize-space()='Move']")
    public WebElement moveButtonInMoveCard;

    public WebElement eachListNameInboard(String listName){
        return Driver.getDriver().findElement(By.xpath("//h2[text()='"+listName+"']"));
    }

    public WebElement getBoardByName(String boardName){
        return Driver.getDriver().findElement(By.xpath("//div[text()='"+boardName+"']"));
    }

    @FindBy(xpath = "//h2[@data-testid='list-name']")
    public List<WebElement> listNames;

    /**
     * Retrieves the text of all WebElements in the listNames field.
     *
     * @return A list of strings containing the text of each WebElement in listNames.
     */
    public List<String> getListNamesFromBoard() {
        return listNames.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public  WebElement addACardButtonOfTheList(String listName){
        return Driver.getDriver().findElement(By.xpath("//li//h2[text()='"+listName+"']/../../..//button[text()='Add a card']"));
    }

    @FindBy(className = "qJv26NWQGVKzI9")
    public WebElement cardNameInputBox;

    public WebElement getListByName(String listName){
        return Driver.getDriver().findElement(By.xpath("//h2[normalize-space()='"+listName+"']"));
    }


    /**
     * Returns List of String
     *
     * @param listName
     * @return List<String> card names in a specific listName
     */
    public List<String> cardNamesOfTheList(String listName){
        List<WebElement> cards = Driver.getDriver().findElements(By.xpath("//li//h2[text()='" + listName + "']/../../..//div/a"));
        return cards.stream().map(WebElement::getText).collect(Collectors.toList());

    }


    public WebElement getCardByNameAndList(String cardName, String fromList) {
        return Driver.getDriver().findElement(By.xpath("//li//h2[text()='" + fromList + "']/../../..//div/a[text()='"+cardName+"']"));
    }


    @FindBy(xpath = "//button[@aria-label='Show menu']")
    public WebElement thereDots;

    @FindBy(xpath = "//a[contains(text(), 'Close board')]")
    public WebElement closeBoardButton;

    @FindBy(xpath = "//input[@value='Close']")
    public WebElement closeButton;

    @FindBy(css = "div[class='xJP6EH9jYQiWkk'] p")
    public WebElement thisBoardIsClosed;

    @FindBy(xpath = "//button[text()='Permanently delete board']")
    public WebElement permanentlyDeleteBoardButton;

    @FindBy(xpath = "//button[text()='Delete']")
    public WebElement deleteButton;

    @FindBy(xpath = "//span[text()=\"Board deleted.\"]")
    public WebElement boardDeletedPopUp;





}

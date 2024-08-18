package com.vaultN.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Homepage extends BasePage{

    @FindBy(xpath = "//p[text()='Create']")
    public WebElement createButton;

    @FindBy(xpath = "//span[text()='Create board']")
    public WebElement createBoardButton;

    @FindBy(css = "input[type='text']")
    public WebElement boardTitleBox;

    @FindBy(xpath = "//button[(text()='Create')]")
    public WebElement creatingBoardButton;

    @FindBy(css = ".HKTtBLwDyErB_o")
    public WebElement createdBoard;




}

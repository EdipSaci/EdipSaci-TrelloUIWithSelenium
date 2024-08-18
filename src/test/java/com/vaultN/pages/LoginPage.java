package com.vaultN.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy(xpath = "//a[text()='Log in']")
    public WebElement loginButton;

    @FindBy(id = "username")
    public WebElement emailBox;

    @FindBy(id= "password")
    public WebElement passwordBox;

    @FindBy(className= "css-178ag6o")
    public WebElement continueButton;

    @FindBy(id= "login-submit")
    public WebElement logInButton;





}

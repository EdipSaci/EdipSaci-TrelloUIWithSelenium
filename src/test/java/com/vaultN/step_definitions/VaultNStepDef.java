package com.vaultN.step_definitions;

import com.vaultN.pages.BoardPage;
import com.vaultN.pages.Homepage;
import com.vaultN.pages.LoginPage;
import com.vaultN.utilities.BrowserUtils;
import com.vaultN.utilities.ConfigurationReader;
import com.vaultN.utilities.Driver;
import io.cucumber.java.en.*;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class VaultNStepDef {

    LoginPage loginPage = new LoginPage();
    Homepage homepage = new Homepage();
    BoardPage boardPage = new BoardPage();

    static List<String> expectedListNames;

    static Map<String,String> cardAndBelongList = new HashMap<>();

    @Given("the User is on the Trello website")
    public void the_user_is_on_the_trello_website() {
        Driver.getDriver().get(ConfigurationReader.getProperty("url"));
    }
    @When("the User clicks on the Log in button")
    public void the_user_clicks_on_the_log_in_button() {
        loginPage.loginButton.click();
    }
    @When("the User enters email address and clicks on Continue button")
    public void the_user_enters_email_address_and_clicks_on_continue_button() {
         loginPage.emailBox.sendKeys(ConfigurationReader.getProperty("email"));
         loginPage.continueButton.click();
    }
    @When("the User enters password and clicks on Log in button")
    public void the_user_enters_password_and_clicks_on_log_in_button() {
         loginPage.passwordBox.sendKeys(ConfigurationReader.getProperty("password"));
         loginPage.logInButton.click();

         BrowserUtils.waitForUrlContains("boards",Duration.ofSeconds(3));
         //BrowserUtils.sleep(3);
    }
    @Then("the User should be redirected to Trello homepage")
    public void the_user_should_be_redirected_to_trello_homepage() {
        BrowserUtils.verifyUrlContainsKeyword("boards");
    }
    @When("the User clicks on Create button")
    public void the_user_clicks_on_create_button() {
        homepage.createButton.click();
    }
    @When("the User clicks on Create Board button")
    public void the_user_clicks_on_create_board_button() {
        homepage.createBoardButton.click();
    }
    @When("the User enters a new board named {string} in Board title box")
    public void the_user_enters_a_new_board_named_in_board_title_box(String vaultN) {
         homepage.boardTitleBox.sendKeys(vaultN);
    }
    @When("the User clicks on Create button in Create board popup")
    public void the_user_clicks_on_create_button_in_create_board_popup() {
        BrowserUtils.clickWhenClickable(homepage.creatingBoardButton, Duration.ofSeconds(2));
        //homepage.creatingBoardButton.click();

    }
    @Then("the board {string} should be created successfully")
    public void the_board_should_be_created_successfully(String createdBoardName) {
        assertEquals(createdBoardName,homepage.createdBoard.getText());
    }
    @Then("the User should be redirected to {string} board page")
    public void the_user_should_be_redirected_to_board_page(String createdBoardName) {
        BrowserUtils.verifyUrlContainsKeyword(createdBoardName.toLowerCase());
    }


    @When("the User clicks the Add list button")
    public void theUser_Clicks_The_Add_list_Button() {
        boardPage.addListButton.click();
    }

    @And("the User adds the following lists to board")
    public void the_user_adds_the_following_lists_to_board(List<String> list) {
        expectedListNames=list;
        for (String eachList : list) {
            boardPage.enterListNameBox.sendKeys(eachList+ Keys.ENTER);
        }
    }

    @Then("the lists should be added successfully to the board")
    public void the_lists_should_be_added_successfully_to_the_board() {

        for (String eachExpectedListName : expectedListNames) {
            BrowserUtils.waitForVisibility(boardPage.eachListNameInboard(eachExpectedListName),5);
            assertTrue(boardPage.eachListNameInboard(eachExpectedListName).isDisplayed());
        }
        //Second way to assertion by using custom methods from BoardPage
        // Retrieve the list names from the board after they are added
        List<String> actualListNames = boardPage.getListNamesFromBoard();

        // Assert that the actual list names match the expected list names
        assertEquals("The lists added to the board do not match the expected lists", expectedListNames, actualListNames);

    }

    @When("the User adds the following cards to the list")
    public void the_user_adds_the_following_cards_to_the_list(Map<String,String> cardListPair) {

        cardAndBelongList = cardListPair;

        String currentListName = "";
        for (Map.Entry<String, String> eachPair : cardListPair.entrySet()) {
            if (eachPair.getKey().equals("cardName")) {
                continue;
            }
            //clicking Add a card button for the list name is key of Map
            if (!currentListName.equals(eachPair.getValue())) {
                boardPage.addACardButtonOfTheList(eachPair.getValue()).click();
            }
            currentListName = eachPair.getValue();

            //enter the card name
            boardPage.cardNameInputBox.sendKeys(eachPair.getKey());
            boardPage.addCardButton.click();
        }

    }

    @Then("the cards should be added successfully to the respective lists")
    public void the_cards_should_be_added_successfully_to_the_respective_lists() {

        for (Map.Entry<String, String> eachCardAndList : cardAndBelongList.entrySet()) {
            if (eachCardAndList.getKey().equals("cardName")) {
                continue;
            }
            assertTrue(boardPage.cardNamesOfTheList(eachCardAndList.getValue()).contains(eachCardAndList.getKey()));
        }

    }


    @When("the User moves the following cards:")
    public void the_User_Moves_The_Following_Cards(List<Map<String, String>> cardData) {
        Actions actions = new Actions(Driver.getDriver());

        // Loop through each card to move it from one list to another
        for (Map<String, String> data : cardData) {
            String cardName = data.get("cardName");
            String fromList = data.get("fromList");
            String toList = data.get("toList");

            // Locate the card in the 'fromList'
            WebElement cardElement = boardPage.getCardByNameAndList(cardName, fromList);

            // Locate the target list where the card should be moved
            WebElement targetListElement = boardPage.getListByName(toList);

            // Perform the drag-and-drop action
            actions.dragAndDrop(cardElement, targetListElement).perform();
        }

    }

    @Then("the cards should be moved successfully to their new lists")
    public void the_Cards_Should_Be_Moved_Successfully_To_Their_New_Lists(Map<String,String> expectedCardsInList) {

        for (Map.Entry<String, String> eachCardAndList : expectedCardsInList.entrySet()) {
            //we have to skip first pair
            if (eachCardAndList.getKey().equals("cardName")) {
                continue;
            }
            assertTrue(boardPage.cardNamesOfTheList(eachCardAndList.getValue()).contains(eachCardAndList.getKey()));
        }
    }


    @When("the User clicks three dots near the Share button")
    public void the_user_clicks_three_dots_near_the_share_button() {
         boardPage.thereDots.click();
    }

    @When("the User clicks the Close board button")
    public void the_user_clicks_the_close_board_button() {
         boardPage.closeBoardButton.click();
    }

    @When("the User clicks the Close button")
    public void the_user_clicks_the_close_button() {
        boardPage.closeButton.click();
    }

    @Then("the board should be closed successfully")
    public void the_board_should_be_closed_successfully() {
         assertTrue(boardPage.thisBoardIsClosed.isDisplayed());
    }

    @When("the User clicks the Permanently delete button")
    public void the_user_clicks_the_permanently_delete_button() {
         boardPage.permanentlyDeleteBoardButton.click();
    }

    @And("the User clicks the delete button")
    public void the_User_Clicks_The_Delete_Button() {
        boardPage.deleteButton.click();
    }


    @Then("the board {string} should be deleted successfully")
    public void the_Board_Should_Be_Deleted_Successfully(String boardName) {
        assertFalse(boardPage.getBoardByName(boardName).isDisplayed());
        //second assertion
        BrowserUtils.verifyElementDisplayed(boardPage.boardDeletedPopUp);
    }
}

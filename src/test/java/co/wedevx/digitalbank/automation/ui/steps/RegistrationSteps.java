package co.wedevx.digitalbank.automation.ui.steps;

import co.wedevx.digitalbank.automation.ui.pages.RegistrationPage;
import co.wedevx.digitalbank.automation.ui.utils.ConfigReader;
import co.wedevx.digitalbank.automation.ui.utils.DBUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static co.wedevx.digitalbank.automation.ui.utils.Driver.getDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegistrationSteps {
    RegistrationPage registrationPage = new RegistrationPage(getDriver());
    List<Map<String, Object>> nextValList = new ArrayList<>();

    @Given("User navigates to Digital Bank signup page")
    public void user_navigates_to_digital_bank_signup_page() {
        getDriver().get(ConfigReader.getPropertiesValue("digitalbank.registrationpageurl"));
        assertEquals("Digital Bank", getDriver().getTitle(), "Register page title mismatch");
    }
    @When("User creates account with the following fields")
    public void user_creates_account_with_the_following_fields(List<Map<String, String>> registrationTestDataListMap) {
        registrationPage.fillOutRegistrationPage(registrationTestDataListMap);
    }
    @Then("User should be displayed with the message {string}")
    public void user_should_be_displayed_with_the_message(String expectedSuccessMessage) {
        //assertEquals(expectedSuccessMessage, registrationPage.getMessage(), "Success message mismatch" );
    }

    @Then("User should the {string} required field error message {string}")
    public void userShouldTheRequiredFieldErrorMessage(String fieldName, String expectedErrorMessage) {
        String actualErrorMessage = registrationPage.getRequiredFieldErrorMessage(fieldName);
        assertEquals(expectedErrorMessage, actualErrorMessage,
                "The error message of required " + fieldName + " field mismatch");
    }

    @Then("the following user info should be saved in DB")
    public void theFollowingUserInfoShouldBeSavedInDB(List<Map<String, String>> expectedUserProfileInfoinDBList) {
        Map<String, String> expectedUserInfoMap = expectedUserProfileInfoinDBList.get(0);

        String email = expectedUserInfoMap.get("email");

        String queryUserTable = String.format("select * from users where username = '%s'", expectedUserInfoMap.get("email"));
        String queryUserProfile = String.format("select * from user_profile where email_address = '%s'", expectedUserInfoMap.get("email"));

        List<Map<String, Object>> actualUserInfoList = DBUtils.runSQLSelectQuery(queryUserTable);
        List<Map<String, Object>> actualUserProfileInfoList = DBUtils.runSQLSelectQuery(queryUserProfile);

        assertEquals(1, actualUserInfoList.size(), "Registration created an unexpected number of users");
        assertEquals(1, actualUserProfileInfoList.size(), "Registration created an unexpected number of user profiles");

        Map<String, Object> actualUserInfoMap = actualUserInfoList.get(0);
        Map<String, Object> actualUserProfileInfoMap = actualUserProfileInfoList.get(0);

        assertEquals(expectedUserInfoMap.get("title"), actualUserProfileInfoMap.get("title"), "Registration generated the wrong title");
        assertEquals(expectedUserInfoMap.get("firstName"), actualUserProfileInfoMap.get("first_name"), "Registration generated the wrong first name");
        assertEquals(expectedUserInfoMap.get("lastName"), actualUserProfileInfoMap.get("last_name"), "Registration generated the wrong last name");
        assertEquals(expectedUserInfoMap.get("gender"), actualUserProfileInfoMap.get("gender"), "Registration generated the wrong gender");
        //assertEquals(expectedUserInfoMap.get("dob"), actualUserInfoMap.get("dob"), "Registration generated the wrong dob");
        assertEquals(expectedUserInfoMap.get("ssn"), actualUserProfileInfoMap.get("ssn"), "Registration generated the wrong ssn");
        assertEquals(expectedUserInfoMap.get("email"), actualUserProfileInfoMap.get("email_address"), "Registration generated the wrong email");
        assertEquals(expectedUserInfoMap.get("address"), actualUserProfileInfoMap.get("address"), "Registration generated the wrong address");
        assertEquals(expectedUserInfoMap.get("locality"), actualUserProfileInfoMap.get("locality"), "Registration generated the wrong locality");
        assertEquals(expectedUserInfoMap.get("region"), actualUserProfileInfoMap.get("region"), "Registration generated the wrong region");
        assertEquals(expectedUserInfoMap.get("postalCode"), actualUserProfileInfoMap.get("postal_code"), "Registration generated the wrong postalCode");
        assertEquals(expectedUserInfoMap.get("country"), actualUserProfileInfoMap.get("country"), "Registration generated the wrong country");
        assertEquals(expectedUserInfoMap.get("homePhone"), actualUserProfileInfoMap.get("home_phone"), "Registration generated the wrong homePhone");
        assertEquals(expectedUserInfoMap.get("mobilePhone"), actualUserProfileInfoMap.get("mobile_phone"), "Registration generated the wrong mobile phone");
        assertEquals(expectedUserInfoMap.get("workPhone"), actualUserProfileInfoMap.get("work_phone"), "Registration generated the wrong work phone");

        //validate user table
        assertEquals(expectedUserInfoMap.get("accountNonExpired"), String.valueOf(actualUserInfoMap.get("account_non_expired")), "accountNonExpired mismatched");
        assertEquals(expectedUserInfoMap.get("accountNonLocked"), String.valueOf(actualUserInfoMap.get("account_non_locked")), "accountNonLocked mismatched");
        assertEquals(expectedUserInfoMap.get("credentialsIsNonExpired"), String.valueOf(actualUserInfoMap.get("credentials_non_expired")), "credentialsIsNonExpired mismatched");
        assertEquals(expectedUserInfoMap.get("enabled"), String.valueOf(actualUserInfoMap.get("enabled")), "enabled mismatched");
        assertEquals(expectedUserInfoMap.get("email"), actualUserInfoMap.get("username"), "username mismatched");
        assertEquals(nextValList.get(0).get("next_val"), actualUserInfoMap.get("id"), "ID mismatched");
        long expectedUserProfileId = Integer.parseInt(String.valueOf(nextValList.get(0).get("next_val")));
        assertEquals(++expectedUserProfileId, actualUserProfileInfoMap.get("id"), "ID mismatched");
    }

    @Given("the user with {string} is not in DB")
    public void theUserWithIsNotInDB(String email) {
        String queryForUserProfile = String.format("Delete FROM user_profile where email_address = '%s'", email);
        String queryForUsers = String.format("Delete FROM users where username= '%s'", email);

        String queryToGetNextValInHybernateSequence = String.format("select * from hibernate_sequence");
        nextValList = DBUtils.runSQLSelectQuery(queryToGetNextValInHybernateSequence);

        DBUtils.runSQLUpdateQuery(queryForUserProfile);
        DBUtils.runSQLUpdateQuery(queryForUsers);
    }
}

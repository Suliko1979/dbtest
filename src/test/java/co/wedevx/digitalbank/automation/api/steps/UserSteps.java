package co.wedevx.digitalbank.automation.api.steps;

import io.cucumber.java.en.Given;
import co.wedevx.digitalbank.automation.api.models.User;

import java.util.List;

public class UserSteps {
    @Given("the following user is in DB:")
    public void the_following_user_is_in_db(List<User> users) {
        System.out.println(users);

    }
}

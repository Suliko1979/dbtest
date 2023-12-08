package co.wedevx.digitalbank.automation.api.steps.data_transformer;

import io.cucumber.java.DataTableType;
import co.wedevx.digitalbank.automation.api.models.User;
import co.wedevx.digitalbank.automation.ui.models.AccountCard;

import java.util.Map;

public class dataTableTransformer {
    @DataTableType
    public User accountCardEntry(Map<String, String> entry) {
        String title = entry.get("title");
        String firstName = entry.get("firstName");
        String lastName = entry.get("lastName");
        String gender = entry.get("gender");
        String dob = entry.get("dob");
        String ssn = entry.get("ssn");
        String emailAddress = entry.get("emailAddress");
        String password = entry.get("password");
        String address = entry.get("address");
        String locality = entry.get("locality");
        String region = entry.get("region");
        String postalCode = entry.get("postalCode");
        String country = entry.get("country");
        String homePhone = entry.get("homePhone");
        String mobilePhone = entry.get("mobilePhone");
        String workPhone = entry.get("workPhone");

        return new User(title, firstName, lastName, gender, dob, ssn, emailAddress, password, address, locality, region, postalCode, country, homePhone, mobilePhone, workPhone);
    }
}

@Registration
Feature: Digital Bank Registration Page
  Background:
    Given the user with "jack@test.com" is not in DB
    And User navigates to Digital Bank signup page

  @Test
  Scenario: Positive Case: As a user I want to successfully create Digital bank account
    When User creates account with the following fields

    |title |firstName|lastName|gender|  dob        |ssn         |   email        |  password   |address      |locality|region|postalCode|country|homePhone|mobilePhone|workPhone|
    |  Mr. |Jack     | Test   | M    |12/12/1990   | 122-33-4453|  jack@test.com  |Tester123    | 12 Main str  | City  | CA   |  9921    |  US   |   3423  |5436543    | 9876545678|

    Then User should be displayed with the message "Registration Successful. Please Login"

    Then the following user info should be saved in DB
      |title |firstName|lastName|gender|  dob        |ssn         |email        |  password   |address      |locality|region|postalCode|country|homePhone|mobilePhone|workPhone   |accountNonExpired|accountNonLocked|credentialsIsNonExpired|enabled|
      |  Mr. |Jack     | Test   | M    |12/12/1990   | 122-33-4453| jack@test.com|Tester123    | 12 Main str  | City  | CA   |  9921    |  US   |   3423  |5436543    | 9876545678|true             |true            |true                   |   true|


  @NegativeRegistrationCases
    Scenario Outline: Negative Test Case: As a DB admin, I want to make sure users can't register without providing all valid data
      When User creates account with the following fields

        |title    |firstName  |lastName  |gender|  dob   | ssn  |email|password   |address  |locality   |region  |postalCode  |country  | homePhone    |mobilePhone  |workPhone   |
        |<title> |<firstName>|<lastName>|<gender>|  <dob>|<ssn>|<email> |<password>|<address> |<locality> |<region>|<postalCode>|<country>|<homePhone>   |<mobilePhone>|<workPhone>|

      Then User should the "<fieldWithError>" required field error message "<errorMessage>"

      Examples:

        |title |firstName|lastName|gender|  dob        |ssn       |email |password   |address     |locality|region|postalCode|country|homePhone|mobilePhone|workPhone|termsCheckMark|fieldWithError |errorMessage                         |
        |      |         |        |       |            |            |      |          |             |         |      |         |      |           |          |         |              |   title       |  Please select an item in the list. |
        |  Mr. |         |        |       |            |            |      |          |             |         |      |         |      |           |          |         |              |   firstName   |  Please fill out this field.        |
        |  Mr. |  Jack   |        |       |            |            |      |          |             |         |      |         |      |           |          |         |              |   lastName    |  Please fill out this field.        |
        |  Mr. |  Jack   |   Test |       |            |            |      |          |             |         |      |         |      |           |          |         |              |   gender      |  Please select one of these options.        |
        |  Mr. |  Jack   |   Test |   M   |            |            |      |          |             |         |      |         |      |           |          |         |              |   dob         |  Please fill out this field.        |
        |  Mr. |  Jack   |   Test |   M   | 12/12/1990 |            |      |          |             |         |      |         |      |           |          |         |              |   ssn         |  Please fill out this field.        |
        |  Mr. |  Jack   |   Test |   M   | 12/12/1990 |122-33-4453|      |          |             |         |      |         |      |           |          |         |              |   email       |  Please fill out this field.        |


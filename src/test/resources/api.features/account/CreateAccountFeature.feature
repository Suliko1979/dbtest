Feature: Create Account Test Scenarios
  Scenario: Create a valid account
    Given the following user is in DB:
    |title|firstName|lastName|gender|dob        |ssn         |emailAddress    |password    |address     |locality   |region   |postalCode  |country|homePhone   |mobilePhone|workPhone|
    |Mr.  |El       |M       |M     |01/30/1999 |123-33-3833 |test0@gmail.com |Test123$$   |123 main st |CA         |Palo Alto|60001       |US     |3120001234  |           |         |

Scenario: Create an account with wrong account name
  Scenario: Create an account with wrong account type code
  Scenario: Create an account with wrong opening deposite
    Scenario: Create an account with wrong ownerTypeCode
Feature: Login page feature

  @login
  Scenario: Login page title
    Given user is on login page
    When user gets the title of the page
    Then page title should be "Login - My Store"

  @login
  Scenario: Forgot Password link
    Given user is on login page
    Then forgot your password link should be displayed

  @login
  Scenario: Login with correct credentials
    Given user is on login page
    When user enters username "funny.scorpion@gmail.com"
    And user enters password "Selenium@12345"
    And user clicks on Login button
    Then user gets the title of the page
    And page title should be "My account - My Store"

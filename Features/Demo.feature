Feature: MyAccount-Login Feature
Description: This feature will test a login functionality

#Scenario: Login with valid username and password
#Given Open browser
#When Enter the url "http://practice.automationtesting.in/"
#And Click on MyAccount Menu
#And Enter registered username "Pavanoltraining" and password "Test@selenium123"
#And Click on Login button
#Then User must successfully login to the webpage
  
  
Scenario Outline: Login with valid username and password
Given Open browser
When Enter the url "http://practice.automationtesting.in/"
And Click on MyAccount Menu
And Enter registered username "<Username>" and password "<Password>"
And Click on Login button
Then User must successfully login to the webpage
 
Examples:

| Username | Password |
| Pavanoltraining | Test@selenium123 |
| Pavanoltraining | Test@selenium |

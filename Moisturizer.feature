#Author: yadu.verma@gmail.com
#Date:19-10-2021

@functional
Feature: Weather Shopper
  I want to use this template for my feature file
     
      
@below19 @Chrome
  Scenario: Shop for moisturizers if the weather is below 19 degrees
   	Given user is on Home Page
    And user reads if the Current temperature below 19
    When user clicks on info icon
    Then user should see Your task info tip
    And user should see info tip text contains as "Shop for"
    And user should see Moisturizers section
    And user should see Buy moisturizers button
    When user should click on Buy moisturizers button
    Then user should be on Moisturizers page
    And user should click on info icon
    And user should see Your task info
    And user should see info tip text contains "Add two moisturizers to your cart."
    And user should see moisturizers content
    And user should see moisturizers price
    And user should select least expensive moisturizer that contains Aloe
    And user should select the least expensive moisturizer that contains Almond
    And user clicks on cart
    And user should be on Checkout page
    And user should click on info icon
    And user should see Your task info
    And user should see info text contains "Verify that the shopping cart looks correct."
    And user should verify items
    And user should verify Pay with Card button
    And user should click on Pay with Card button
    And user should see Payment details form
    And user should enter email as "test123@gmail.com"
    And user should click on Test Mode
    And user should copy a card number
    And user should be back on Payment page
    And user should enter card number
    And user should enter date as "12/22"
    And user should enter cvc as "456"
    And user should see zip code field
    And user should enter zip code as "560037"
    And user clicks on Pay button
    And user should be on Confirmation page
   	And user should click on info icon
   	And user should see Your task info
    And user should see info text contains "Verify if the payment was successful."
   	And user should see Payment Success
    And user should see Payment Success message as "Your payment was successful."
   
   
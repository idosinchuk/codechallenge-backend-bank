Feature: Transaction operations

  @ignore
  Scenario: Receive the transaction information and store it into the system
    Given A transaction that is not stored in our system and with existing account
    When I create the transaction
    Then Transaction have been stored in the system


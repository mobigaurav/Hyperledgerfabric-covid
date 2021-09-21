
# Hyperledgerfabric-covid

DESCRIPTION

Track Covid vaccine recipients so that no one is missed or receives any extra dose. One of the challenges that the government is facing is people are producing fake covid vaccine certificates and infected people are spreading this further.

 

Background of the problem statement:

Covid vaccine is one of the crucial tools to fight against covid-19. Many countries are working at their full strength to vaccinate their citizens.

It becomes important to track who has taken the vaccine and who hasn’t. Since most vaccines come in two shots, it has become equally important to track who got first short and who got both the shots.

It has become difficult to track down fake covid vaccine recipients. An immutable solution is needed where covid vaccine recipients can be tracked and updated as and when they receive the first or second shot.

 

Features of the application:

Has the following parameters:

Beneficiary name
Beneficiary age
Beneficiary gender
Beneficiary identity proof ID
Vaccine reference ID
Vaccine name
Date of vaccination
Dose number (first or second)
 

     1. Adds first dose recipients of the vaccine:

This function is used to add a recipient who has received the very first dose of covid vaccine.

Input parameters:

             * @param name        the name of the recipient

             * @param age         the age of the recipient

             * @param gender      the gender of the recipient

             * @param identity    the identity proof of the recipient

             * @param vaccineName the name of the vaccine

             * @param date        the date of the vaccine administration

             * @param vaccineDose the dose number of the vaccine   

          This function does the following checks as well:

Recipient should not have taken second dose of the vaccine
Recipient should not have taken both the doses of the vaccine
 

      2. View covid vaccination status:

This function helps to check if a person is vaccinated or not. If yes, then how many doses they have taken.

Input parameters:

* @param identity    the identity proof of the recipient

     This function returns the status of the person.

 

     3. Update the status of the recipient after the second dose:

This function helps to check and update the ledger when the recipient takes a second dose of the vaccine.

Input parameters:

* @param identity    the identity proof of the recipient

* @param date        the date of the vaccine administration

This function does the following checks as well:

Recipient should be given the first dose of the same vaccine
Recipient should not be fully vaccinated already
 

Recommended technologies:

IDE Tool: Eclipse
Chaincode Language: Java
Build Automation tool: Gradle
Blockchain : Hyperledger Fabric
Server: Test network

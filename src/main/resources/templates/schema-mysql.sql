drop table Traveler;
CREATE TABLE Traveler (
                          travelerId  UUID PRIMARY KEY,
                          customerId UUID,
                          firstName VARCHAR(256) NOT NULL,
                          middleName VARCHAR(256),
                          lastName VARCHAR(256) NOT NULL,
                          gender VARCHAR(1) NOT NULL,
                          dob DATE NOT NULL,
                          countryCode1 VARCHAR(1) NOT NULL,
                          phone1 VARCHAR(10) NOT NULL,
                          countryCode2 VARCHAR(1),
                          phone2 VARCHAR(10),
                          emergencyFirstName VARCHAR(256),
                          emergencyLastName VARCHAR(256),
                          emergencyCountryCode VARCHAR(1),
                          emergencyPhone VARCHAR(10),

                          flightPrefSeat VARCHAR(256),
                          flightPrefSpecial VARCHAR(256),
                          passportCountryCode VARCHAR(1),
                          passportNumber VARCHAR(256)
)
    ENGINE=InnoDB;

ALTER TABLE `traveler` ADD UNIQUE `unique_index`(`customer_id`, `first_name`, `last_name`);
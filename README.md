# AddressParser

A small web service built using spring boot that takes as an input an address, it extracts the street name and house
number and returns them in a json formatted string.

##### Key Dependencies

- Java SDK 11
- Spring Boot 2.5.0

## Running the server

Import the project into Intellij IDE and click run !

The default url for the project is: http://localhost:8080

## Endpoint

- GET 'api/split_address'

#### GET 'api/split_address'

- Get a json formatted string containing the street name and house number.
- Request Arguments: address
  ```
  ?address=Blaufeldweg 123
  ```
- Returns: Json formatted string containing street name and house number
    ```
    {"street":"Blaufeldweg","houseNumber":"123 B"}
    ```


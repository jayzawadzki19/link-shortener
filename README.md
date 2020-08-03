# Link shortener


REST application that allows user to shorten URL link. Send full link and receive it's shorter version with password that is necessary to delete created link.

### Technologies
Project is created with:
* Java 11
* Spring boot 2.3.2
* Spring Data
* Maven
* H2Database
* REST
* Lombok

## Features

  - Send full link to app and receive it's shorter version.
  - Short link saves space and redirects directly to full link.
  - Delete created short link with it's code and password.
  - Every created link has his own views counter.
  - Drag and drop images (requires your Dropbox account be linked)

## Setup

To run this project go to app root folder and use mvn script:

```sh
$ ./mvnw spring-boot:run
```

## Code Examples

Link shortener is running on http://localhost:8080/.

To generate new short link send JSON object with POST method to **/new/** endpoint.
```json
{
 "link": "https://www.google.com/"
}
```
The response you will recive is another object with short link and 4 digit delete password.
```json
{
    "shortLink": "lJDROU",
    "password": 4872
}
```
To use short link send GET request on **/go/** endpoint. E.g. http://localhost:8080/go/lJDROU

To delete link from database send JSON short link object with DELETE request on **/delete** endpoint.
```json
{
    "shortLink": "lJDROU",
    "password": 4872
}
```
If shortLink and password is correct, object will be removed from database.


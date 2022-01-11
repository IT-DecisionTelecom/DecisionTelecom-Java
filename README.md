IT-Decision Telecom Java SDK
===============================

Convenient Java client for IT-Decision Telecom messaging API.

[![Java CI with Maven](https://github.com/IT-DecisionTelecom/DecisionTelecom-Java/actions/workflows/maven.yml/badge.svg)](https://github.com/IT-DecisionTelecom/DecisionTelecom-Java/actions/workflows/maven.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

Requirements
-----

- [Sign up](https://web.it-decision.com/site/signup) for a free IT-Decision Telecom account
- Request login and password to send SMS messages and access key to send Viber messages
- Java 1.8+ and Maven installed
- You should have an application written in Java to make use of this SDK

Installation
-----

The easiest way to use the IT-Decision Telecom SDK in your Go project is to install it to your local Maven repository from github:

```
git clone https://github.com/IT-DecisionTelecom/DecisionTelecom-Java
cd DecisionTelecom-Java/src
mvn install
```

Usage
-----

We have put some self-explanatory usage examples in the [examples](https://github.com/IT-DecisionTelecom/DecisionTelecom-Java/tree/main/examples/src/main/java/examples) folder,
but here is a quick reference on how IT-Decision Telecom clients work.
First, you need to import DecisionTelecom package which corresponds to your needs: 

```java
import com.decisiontelecom.SmsClient;
import com.decisiontelecom.ViberClient;
import com.decisiontelecom.ViberPlusSmsClient;
```

Then, create an instance of a required client. Be sure to use real login, password and access key.

```java
SmsClient smsClient = new SmsClient("<YOUR_LOGIN>", "<YOUR_PASSWORD>");
ViberClient viberClient = new ViberClient("<YOUR_ACCESS_KEY>");
ViberPlusSmsClient viberPlusSmsClient = new ViberPlusSmsClient("<YOUR_ACCESS_KEY>");
```

Now you can use created client to perform needed operations. For example, this is how you can get your SMS balance:

```java
try {
    // Call client GetBalance method to get SMS balance information.
    SmsBalance balance = smsClient.getBalance();

    // GetBalance method should return SMS balance information.
    System.out.printf("Balance information: Balance: %f, Credit: %f, Currency: %s\n",
            balance.getBalanceAmount(), balance.getCreditAmount(), balance.getCurrency());
} catch (SmsException e) {
    // SmsException contains specific DecisionTelecom error with the code of what went wrong during the operation.
    System.out.printf("Error while getting SMS balance information. Error code: %d (%s)\n",
            e.getErrorCode().getCode(), e.getErrorCode().toString());
} catch (Exception e) {
    // A non-DecisionTelecom error occurred during the operation (like connection error)
    e.printStackTrace();
}
```

### Error handling
All client methods throw an exception in case if something went wrong during the operation. It might be a general exception in case of connection error, for example. Or it might be a specific DecisionTelecom exception with some details of what went wrong. 

SMS client methods might throw `SmsException` which contains an SMS error code.
Viber and Viber plus SMS client methods might throw `ViberException` which contains `ViberError` object with some error details like name, message, status and code.

See provided examples on how to handle specific DecisionTelecom exceptions.

#### SMS errors
SMS client methods return errors in form of the error code. Here are all possible error codes:

- 40 - Invalid number
- 41 - Incorrect sender
- 42 - Invalid message ID
- 43 - Incorrect JSON
- 44 - Invalid login or password
- 45 - User locked
- 46 - Empty text
- 47 - Empty login
- 48 - Empty password
- 49 - Not enough money to send a message
- 50 - Authentication error
- 51 - Invalid phone number

#### Viber errors
Viber and Viber plus SMS client methods return errors in form of a struct with the `Name`, `Message`, `Code` and `Status` properties.

If underlying API request returns unsuccessful status code (like 401 Unauthorized),
then client methods will return error with only `Name` and `Status` properties set:

```json
{
  "name": "Unauthorized",
  "status": 401
}
```

Known Viber errors are:

```json
{
  "name": "Too Many Requests",
  "message": "Rate limit exceeded",
  "code": 0,
  "status": 429
}
```

```json
{
  "name": "Invalid Parameter: [param_name]",
  "message": "Empty parameter or parameter validation error",
  "code": 1,
  "status": 400
}
```

```json
{
  "name": "Internal server error",
  "message": "The server encountered an unexpected condition which prevented it from fulfilling the request",
  "code": 2,
  "status": 500
}
```

```json
{
  "name": "Topup balance is required",
  "message": "User balance is empty",
  "code": 3,
  "status": 402
}
```

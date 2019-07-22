# Simple Email Notification Server

This RestApi is created for extending home automation capabilities of smart home stations that support scripting.
Currently it contains a notification service and a telnet service to control certain devices (e.g. Denon Amplifier)

You can make feature request if you wish, or submit new features. 

## Settings
Application Settings is in [`src/main/res/ources/application.conf`](https://github.com/x3lq/notificationserver/blob/master/src/main/resources/application.conf "src/main/res/ources/application.conf") for altering the port on which the application listens for requests.

User settings for specifying SMTP server and Account are found in [`src/main/res/ources/user.properties`](https://github.com/x3lq/notificationserver/blob/master/src/main/resources/user.properties "`src/main/res/ources/user.properties`").
```
        hostName = //SMTP server
        smtpPort = //SMTP server port
        email = //E-Mail you login with
        pwd = //PASSWORD
        sendTo = //EMails you want to notify
        sendTo = //you can have multple Emails, as many as you like
```

## API Documentation
|  Name / URL  | Method-Type  | Header  | Body  | Functionality  |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| sendEmail  | POST  | Contenty-Type : application/json  | "{"subject" : "TestSubject","message" : "TestMessage"}"  | Send email to list of definde emails with the Subject and Message from the POST-Body |
| telnet  | POST  | Contenty-Type : application/json  | "{"command": "TestCommand","receiverIp" : "172.0.0.1", "receiverPort": 23}"  | Opens a Telnet Connection and sends the specified command to the telnet server  |



## Frameworks / Libraries used
- Ktor
- commons-configuration2
- commons-email 1.4
- commons-net 3.6

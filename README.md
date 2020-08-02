# Messanger API

Messanger APP is service that can be used to send the message to another user by providing user id in the message body. This service will provide the sended/received 
message from sender or user by ID. For message read and write service uses ActiveMQ.

# Project structure
  * Backend - Spring boot framework used to build the backend service and for content deliver used inbuild tomcat apache server which us running on default port 8080.
  * Database - Use postgres database used for this project

# How to use this?
  - Use has need to clone this repo at host machine and run delow command
    * docker-compose up
    Container will start running at the host machine and user can check the running container by using below command: Docker ps

# Features!
    This service has provide five operation. Below is the list of service and corresponding the definition.
    
|  HTTP METHOD | END POINT   |  DESCRIPTION |
|---|---|---|
|  GET | http://localhost:8080/createAccount  | This end point will create user and return user with UUID ID that will use to send message  |
|  POST | http://localhost:8080/sendMessage  | This will send message to the end receiver and persist message into into database. User has need to set userid in header as senderId |
|  GET | http://localhost:8080/findAllMessage}  |  Get all sended/received message |
|  GET | http://localhost:8080/findAllMessageSendedByMe/{userId}  | Get all message send by me(pass sender Id in URI) |
|  Get | http://localhost:8080/findAllMessageReceivedByMe/{userId}  |  Get all message received by me(pass receiver Id in URI)  |

 
 ### Maintainer 
 Raj K Upadhyay


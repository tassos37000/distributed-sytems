# distributed-sytems

Part 1 of a project for the course Distributed Systems at AUEB.

The goal was to create a service in which the users would be able to connect to topics (conversations), and in those send and receive messages in text, image, and video form. 
See [Part 2](https://github.com/anape03/chat-filiw) of the project.

## Description
---
### Client

Each user acts as a Client object. A Client is both a Publisher (able to send content to Brokers) and a Consumer (able to receive content from Brokers) at a given topic.
In order to connect to a topic, it first connects to a random broker and based on information received it may need to esatblish a connection with a different Broker.

### Broker

Each Broker is resposible for some topics, based on a hash taken from the topic's name.
The Broker has a connection open for each Client currently using a topic it's responsible for.

## How to use
---

### Run

1. Clone project

2. Edit Broker IP Addresses in file ```\src\conf.txt```

3. Run each Broker seperately.\
In order to run in cmd:
    ```
    cd src
    javac Broker1.java
    java Broker1
    ```
    (Repeat for Broker2, Broker3)\
    **Note:** Brokers may be run in any order

4. Run each Client in a process.
In order to run in cmd:
    ```
    cd src
    javac ClientCreate.java
    java ClientCreate
    ```
    Repeat for each user.\
    Any info needed, such as username and topic name, are explicitly requested
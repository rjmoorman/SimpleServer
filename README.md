# SimpleServer
Creates a simple server for creating a chat client 

SimpleChatServer and DailyAdviceServer both create a local server on the users computer. The AdviceServer has a stored list of strings and opens up a Server Socket Channel that creates a PrintWriter to write one of the strings to the socket. The ChatServer opens a socket in a similar fashion creates a threadPool to handle incoming messages and write messages out to everyone connected to the serverv The runnable method reads the string stored on the socket and prints sends the message to those that are connected.

DailyAdviceClient establishes a connection to the server using 127.0.0.1 and port 5000 once connected the client recieves the string advice from the server and prints that advice to the terminal

SimpleChatClientA establishes a connection to the server using 127.0.0.1 and port 5000 once connected the user can utilize the GUI to input messages and send them to the server where the server is able to write those messages back out to all the connected clients. The client utilizes a single new thread to check for incoming messages from the server that it prints to the JTextField for the user to read.

### Set Up
1. compile and run the server class first
2. once the server is running compile and run the associated client and start interacting.


# Credits
Head First Java, 3rd edition By Kathy Sierra, Bert Bates, Trisha Gee

# OnlineChatRoom
A multi-threaded server communicates with clients simultaneously through encrypted messages.

Without encryption:
The server:
1. Listens on an IP/Port pair provided as arguments, i.e. the args in main method.
2. Maintains a thread pool which manages many threads. Each thread handles one connection.
3. Keep user infomation (username, password and messages) in memory. All data will be discarded when the server is down.
4. Maintains a message list. Each message consists of the sender, time, and content. 
   When a new message is received, append it to the end of the list.
5. Supports four functionalities for clients: sign up, sign in, get history messages of the chat room, send messages to the chat room



Encryption:

Two phases:

1. Key exchange (RSA) between the client and server.
The client sends a random key1 (for AES) to the server, the whole message is encrypted using RSA with the public key.
The server can decrypt it using the private key.
The server generate a random key2.
The server sends key2 encrypted by AES with key1.

2. Message encryption (AES):
After the key is exchanged, every message sent between the client and server will be encrypted by AES with this key. 
The same key will be used to decrypt the message.
Sending:
Encrypt the message with AES and key2. Transform it to a string using BASE64.

Receiving:
Transform the BASE64 string to byte[]. Decrypt it with (AES, key2) and get byte[].
Transform byte[] to String and decode it following the protocol.

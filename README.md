# Metody do komunikacji z serwerem:

- regiserReq(String login, String pass)
- loginReq(String login, String pass)
- getIpReq(String friendLogin)
- logoutReq(String login)

Do wysłania wiadomości do serwera tworzymy obiekt 
```
PwrMsg.client_to_server
```
Do odbierania wiadomości tworzymy obiekt 
```
PwrMsg.server_to_client
```
Odbieranie i wysyłanie wiadomości z obsługą błędów w przykładzie.
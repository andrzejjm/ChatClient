Metody do komunikacji z serwerem:
	-registerReq(String login, String pass)
	-loginReq(String login, String pass)
	-getIpReq(String login)
	-logoutReq() <- jeszcze niegotowa
	
	Do wysłania wiadomości do serwera tworzymy obiekt PwrMsg.client_to_server i wypełniamy dane jak w przykładzie.
	Do odbierania wiadomości tworzymy obiekt PwrMsg.server_to_client i parsujemy metodą PwrMsg.server_to_clinet.parseFrom(byte[]);
	
	Odbieranie wiadomości z obsługą błędów w przykładzie.
	
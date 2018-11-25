package pl.chatClient;

import pwr_msg.PwrMsg;

import java.io.*;
import java.net.Socket;

public class Client {
    private String secret = "BSIUIPANY123";
    private PwrMsg.clinet_to_server toSend;
    private PwrMsg.server_to_clinet toGet;

    private Socket socket;

    private DataOutputStream outputStream;
    private DataInputStream inputStream;

    public Client() {
    }

    public byte[] encodeOrDecodeMsg(byte[] bytes){
	    int size = bytes.length;
	    byte [] result= new byte[size];
	    for (int i = 0; i< size; i++)
	    {
		for ( char s : secret.toCharArray()) {
			if(i<size)
			{
				result[i]= (byte)(s ^ bytes[i]);
				i++;
			}
			i--;
		}
	    }
	   return result; 
    }

    public void start() throws Exception {

        socket = new Socket("localhost", 8085);
        outputStream = new DataOutputStream(socket.getOutputStream());
        inputStream = new DataInputStream(socket.getInputStream());

        System.out.println("Wybierz operację: " +
                "\n0. Zarejestruj" +
                "\n1. Zaloguj" +
                "\n2. GetIp" +
                "\n3. Wyloguj" +
                "\n");

        int opt;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        opt = Integer.parseInt(br.readLine());
        switch (opt) {
            case 0:
                toSend = registerReq("user1", "pass");
                break;
            case 1:
                toSend = loginReq("user1", "pass");
                break;
            case 2:
                toSend = getIpReq("user1");
                break;
            case 3:
                toSend = logoutReq("user1");
                break;

            default:
                System.out.println("Err");
                break;
        }

        byte[] arr = toSend.toByteArray();
	arr = encodeOrDecodeMsg(arr); //encode (xor) message with secret
        int length = 0;

        outputStream.flush();
        outputStream.writeInt(arr.length);
        outputStream.write(arr); //wysyłanie wiadomości

        length = inputStream.readInt();
        arr = new byte[length];

        if(length > 0) {
            inputStream.read(arr, 0, arr.length);
	    arr= encodeOrDecodeMsg(arr); // decode(xor) message with secret
            toGet = PwrMsg.server_to_clinet.parseFrom(arr); //parsowanie wiadomości
            System.out.println("\n" + toGet.getTypeValue() + " " + toGet.getIsSuccesful());
            System.out.println("Długość: " + toGet.toByteArray().length);

            switch (toGet.getTypeValue()) {
                case 0: { //rejestracja
                    if(toGet.getIsSuccesful() == true) {
                        System.out.println("Zarejestrowano pomyślnie");
                    } else {
                        System.out.println("Błąd rejestracji");
                    }
                    break;
                }
                case 1: { //logowanie
                    if(toGet.getIsSuccesful() == true) {
                        System.out.println("Zalogowano pomyślnie");
                    } else {
                        System.out.println("Błąd logowania");
                    }
                    break;
                }
                case 2: { //getIp
                    if(toGet.getIsSuccesful() == true) {
                        System.out.println(toGet.getSecondClinetIp());
                    } else {
                        System.out.println("Błąd pozyskiwania IP");
                    }
                    break;
                }
                case 3: { //wyloguj
                    if(toGet.getIsSuccesful() == true) {
                        System.out.println("Wylogowano");
                    } else {
                        System.out.println("Błąd wylogowywania");
                    }
                    break;
                }
            }
        }

        socket.close();
    }

    public PwrMsg.clinet_to_server registerReq(String login, String password) {
        return PwrMsg.clinet_to_server.newBuilder().setTypeValue(0).setLoginString(login).setPasswordString(password).build();
    }

    public PwrMsg.clinet_to_server loginReq(String login, String password) {
        return PwrMsg.clinet_to_server.newBuilder().setTypeValue(1).setLoginString(login).setPasswordString(password).build();
    }

    public PwrMsg.clinet_to_server getIpReq(String login) {
        return PwrMsg.clinet_to_server.newBuilder().setTypeValue(2).setLoginString(login).build();
    }

    public PwrMsg.clinet_to_server logoutReq(String login) {
        return PwrMsg.clinet_to_server.newBuilder().setTypeValue(3).setLoginString(login).build();
    }
}

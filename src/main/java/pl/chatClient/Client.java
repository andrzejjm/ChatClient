package pl.chatClient;

import pwr_msg.PwrMsg;

import java.io.*;
import java.net.Socket;

public class Client {
    private PwrMsg.clinet_to_server toSend;
    private PwrMsg.server_to_clinet toGet;

    private Socket socket;

    private DataOutputStream outputStream;
    private DataInputStream inputStream;

    public Client() {
        try {
            socket = new Socket("localhost", 8085);
            outputStream = new DataOutputStream(socket.getOutputStream());
            inputStream = new DataInputStream(socket.getInputStream());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void start() throws Exception {
        toSend = PwrMsg.clinet_to_server.newBuilder().setTypeValue(0).setLoginString("Andrzej").setPasswordString("Lol").build();
        byte[] arr = toSend.toByteArray();
        int length = 0;

        outputStream.flush();
        outputStream.writeInt(arr.length);
        outputStream.write(arr);

        length = inputStream.readInt();
        arr = new byte[length];

        if(length > 0) {
            inputStream.read(arr, 0, arr.length); // read the message

            toGet = PwrMsg.server_to_clinet.parseFrom(arr);
            System.out.println("\n" + toGet.getTypeValue() + " " + toGet.getIsSuccesful());

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

                }
            }
        }
    }
}

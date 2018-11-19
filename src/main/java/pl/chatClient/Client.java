package pl.chatClient;

import pwr_msg.PwrMsg;

import java.io.*;
import java.net.Socket;

public class Client {
    private PwrMsg.clinet_to_server toSend;
    private PwrMsg.server_to_clinet toGet;

    private Socket socket;

    private BufferedReader keyReader;

    private DataOutputStream outputStream;
    private DataInputStream inputStream;
    
    private PrintWriter writer;

    private BufferedReader reciver;

    private String reciveMessage;
    private String sendMessage;

    public Client() {
        try {
            socket = new Socket("localhost", 8085);

            //        keyReader = new BufferedReader(new InputStreamReader(System.in));

            //writer = new PrintWriter(socket.getOutputStream(), true);
            outputStream = new DataOutputStream(socket.getOutputStream());
            inputStream = new DataInputStream(socket.getInputStream());
            //        reciver = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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

//            toSend.writeTo(socket.getOutputStream());

//            if ((reciveMessage = reciver.readLine()) != null) {
//                System.out.println(toSend.toByteArray());
//            }

        length = inputStream.readInt();
        System.out.println(length);
        arr = new byte[length];

        if(length > 0) {
            inputStream.read(arr, 0, arr.length); // read the message

            toGet = PwrMsg.server_to_clinet.parseFrom(arr);
            System.out.println("\n" + toGet.getTypeValue() + " " + toGet.getIsSuccesful());
        }
    }
}

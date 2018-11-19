package pl.chatClient;

public class Main {
    public static void main(String[] args) {
        Client client = null;

        client = new Client();
        try {
            client.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

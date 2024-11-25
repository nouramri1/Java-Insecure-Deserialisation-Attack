import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class SecureApp {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java SecureApp <payload.ser>");
            return;
        }

        String payloadFile = args[0];

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(payloadFile))) {
            Object obj = ois.readObject(); // Unsafe deserialization
            if (obj instanceof String) {
                System.out.println("Deserialized safe object: " + obj);
            } else {
                System.out.println("Untrusted object detected! Aborting deserialization.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class VulnerableApp {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java VulnerableApp <payload.ser>");
            return;
        }

        String payloadFile = args[0];

        try (FileInputStream fis = new FileInputStream(payloadFile)) {
            int header1 = fis.read();
            int header2 = fis.read();
            System.out.printf("File Header: %02X %02X%n", header1, header2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(payloadFile))) {
            Object obj = ois.readObject(); // Vulnerable deserialization
            System.out.println("Deserialized object: " + obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


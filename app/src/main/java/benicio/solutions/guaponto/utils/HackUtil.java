package benicio.solutions.guaponto.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HackUtil {
    public static String gerarHash(String valor) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] array = digest.digest(valor.getBytes());

            StringBuilder strHexa = new StringBuilder();
            for (byte b : array) {
                strHexa.append(String.format("%02x", b));
            }

            return strHexa.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}

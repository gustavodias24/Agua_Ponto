package benicio.solutions.guaponto.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public static boolean isDateTimeString(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS");
        dateFormat.setLenient(false); // Para evitar interpretações estranhas

        try {
            Date date = dateFormat.parse(dateString);
            return date != null;
        } catch (ParseException e) {
            return false;
        }
    }
}

package pt.uc.dei.paj.projeto4.grupoi.utilities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Ana Sofia Mendes
 * @author Orlando Neves
 */
public class Encrypt {

    private static MessageDigest md;

    /**
     * Method that receives String with password and encrypt to message digest 5
     *
     * @param pass
     * @return
     */
    public static String cryptWithMD5(String pass) {
        try {
            md = MessageDigest.getInstance("MD5");
            byte[] passBytes = pass.getBytes();
            md.reset();
            byte[] digested = md.digest(passBytes);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < digested.length; i++) {
                sb.append(Integer.toHexString(0xff & digested[i]));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Encrypt.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}

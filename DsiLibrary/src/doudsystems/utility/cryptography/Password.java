/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package doudsystems.utility.cryptography;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import sun.misc.BASE64Encoder;
/**
 *
 * @author Owner
 */
public class Password { // TODO Add password format definition
    
    public static String encryptOneWayToBase64(String clearText) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(clearText.getBytes("UTF-8"));
            byte[] raw = md.digest();
            String cypherText = (new BASE64Encoder()).encode(raw);
            return cypherText;
        } catch (NoSuchAlgorithmException e) {
        } catch (UnsupportedEncodingException e) {
        }
        return null;
    }

    public Password() {}
}

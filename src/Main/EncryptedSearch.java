/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.security.Security;
import java.util.Arrays;
import jsse.SSEUtil;
import jsse.SWP;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 *
 * @author phoenix
 */
public class EncryptedSearch {

    public static void main(String[] args) {
        String password="test";
        Security.addProvider(new BouncyCastleProvider());
        System.out.println("Test AES Searchable ");

        double loadFactor = 1; // No false positives but additional storage
         try {
             SWP swp = new SWP(SSEUtil.getSecretKeySpec(password,
                     SSEUtil.getRandomBytes(20)), "AES",loadFactor, 128);

             byte[] plainBytes = ("Hello").getBytes();
             byte[] cipherText = swp.encrypt(plainBytes, 1);

             byte[] plainText = swp.decrypt(cipherText, 1);

             if (Arrays.equals(plainBytes, plainText))
                 System.out.println("Encryption and Decryption works !");
             else
                 System.out.println("Failed");

             // Get Trapdoor
             byte[] trapDoor = swp.getTrapDoor("Hello".getBytes());

             // Check Match
             if (swp.isMatch(trapDoor, cipherText))
                 System.out.println("Matching works Blind-foldedly !");
             else
                 System.out.println("Matching Does not work !");


         } catch (Exception e){
             e.printStackTrace();
         }
    }
    
}

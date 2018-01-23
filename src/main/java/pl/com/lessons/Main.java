package pl.com.lessons;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;

import javax.crypto.NoSuchPaddingException;

import pl.com.lessons.calendar.CalendarHelper;
import pl.com.lessons.crypt.AssymetricEncryption;
import pl.com.lessons.crypt.SymmetricEncryption;

public class Main {

	public static void main(String[] args) throws Exception
	{
		String date = CalendarHelper.formatDate(CalendarHelper.FORMAT_DATETIME, new Date());
		// Sys ctrl space
		System.out.println(date);
		
		SymmetricEncryption sm = new SymmetricEncryption("12345678");  // minimum 56 bitów
        try
        {
        	String enc = sm.encrypt("Lekcja nr 1");
        	System.out.println(enc);
        	
            System.out.println(sm.decrypt(enc));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }	
                
        
        AssymetricEncryption ac = new AssymetricEncryption();
        
        ac.createKeys("KeyPair/publicKey", "KeyPair/privateKey");
        
		PrivateKey privateKey = ac.getPrivate("KeyPair/privateKey");
		PublicKey publicKey = ac.getPublic("KeyPair/publicKey");

		String msg = "Cryptography is fun1!";
		String encrypted_msg = ac.encryptText(msg, privateKey);
		String decrypted_msg = ac.decryptText(encrypted_msg, publicKey);
		System.out.println("Original Message: " + msg +
			"\nEncrypted Message: " + encrypted_msg
			+ "\nDecrypted Message: " + decrypted_msg);

		if (new File("KeyPair/text.txt").exists()) 
		{
			ac.encryptFile(ac.getFileInBytes(new File("KeyPair/text.txt")),
				new File("KeyPair/text_encrypted.txt"),privateKey);
			ac.decryptFile(ac.getFileInBytes(new File("KeyPair/text_encrypted.txt")),
				new File("KeyPair/text_decrypted.txt"), publicKey);
		}      
                       
	}
		
}

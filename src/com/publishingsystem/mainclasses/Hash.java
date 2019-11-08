package com.publishingsystem.mainclasses;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Hash{
	String hash;
	String salt;
	
	private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for (int j = 0; j < bytes.length; j++) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = HEX_ARRAY[v >>> 4];
	        hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
	private static byte[] hexToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}
	
	public Hash(String password) {
		this.generateHash(password);
	}
	
	public Hash(String password, String s) {
		byte [] salt = hexToByteArray(s);
		this.generateHash(password, salt);
	}
	
	public String getHash() {
		return this.hash;
	}
	
	
	public String getSalt(){
		return this.salt;
	}
	
	public void generateHash(String p) {
		String password = p;
        MessageDigest md;
        try{
            // Select the message digest for the hash computation -> SHA-256
            md = MessageDigest.getInstance("SHA-256");

            // Generate the random salt
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);

            // Passing the salt to the digest for the computation
            md.update(salt);

            // Generate the salted hash
            byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            for (byte b : hashedPassword)
                sb.append(String.format("%02x", b));
            
            this.hash = sb.toString();
            this.salt = bytesToHex(salt);
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
	}
	
	public void generateHash(String p, byte[] salt) {
		String password = p;

        MessageDigest md;
        try{
            // Select the message digest for the hash computation -> SHA-256
            md = MessageDigest.getInstance("SHA-256");

            // Passing the salt to the digest for the computation
            md.update(salt);

            // Generate the salted hash
            byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            for (byte b : hashedPassword)
                sb.append(String.format("%02x", b));
            
            this.hash = sb.toString();
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
       
	}
	
	 public static void main(String[] args){
		 Hash hash1 = new Hash("password");
		 System.out.println(hash1.getHash());
		 System.out.println(new Hash("password", hash1.getSalt()).getHash());

	 }
	
}
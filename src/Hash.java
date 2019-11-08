import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Hash{
	String hash;
	byte[] salt;
	
	public Hash(String password) {
		this.generateHash(password);
	}
	
	public Hash(String password, byte[] salt) {
		this.generateHash(password, salt);
	}
	
	public String getHash() {
		return this.hash;
	}
	
	public byte[] getSalt() {
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
            this.salt = salt;
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
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

class Hash{
	String password;
	byte[] salt;
	
	public Hash(String password, byte[] salt) {
		this.password = password;
		this.salt = salt;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public byte[] getSalt() {
		return this.salt;
	}
}

public class PasswordEncryption{
	public static Hash generateHash(String p) {
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
            
            return new Hash(sb.toString(), salt);
        } catch (NoSuchAlgorithmException e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
	}
	
	public static String generateHash(String p, byte[] salt) {
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
            
            return sb.toString();
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return "";
	}
	
    public static void main(String[] args){
    	Hash hash1 = PasswordEncryption.generateHash("password");
    	System.out.println(hash1.getPassword());
    	System.out.println(PasswordEncryption.generateHash("password", hash1.getSalt()));
        
    }
}
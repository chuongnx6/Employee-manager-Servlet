package util;

import org.mindrot.jbcrypt.BCrypt;

public class EncryptionUtil {
	private static String generalSalt() {
		return BCrypt.gensalt();
	}
	
	public static String encrypt(String plaintext) {
		return BCrypt.hashpw(plaintext, generalSalt());
	}
	
	public static boolean compare(String plaintext, String hashedText) {
		if (hashedText != null) {
			return BCrypt.checkpw(plaintext, hashedText);
		}
		return false;
	}
//	private static byte[] generateSalt() {
//		byte[] salt = new byte[16];
//		SecureRandom secureRandom = new SecureRandom();
//		secureRandom.nextBytes(salt);
//		return salt;
//	}
//	
//	private static byte[] extractSalt(String hashdedText) {
//		return Base64.getDecoder().decode(hashdedText.substring(0, 16));
//	}
//	
//	private static String extractHash(String hashedText) {
//		return hashedText.substring(16);
//	}
//	
//	public static String encrypt(String plaintext, byte[] salt) throws NoSuchAlgorithmException {
//		if (salt == null) {
//			salt = generateSalt();
//		}
//		
//		System.out.println("salt: " + Arrays.toString(salt));
//		try {
//			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
//			messageDigest.update(salt);
//			byte[] hashedBytes = messageDigest.digest(plaintext.getBytes());
//			
//			return Base64.getEncoder().encodeToString(hashedBytes);
//		} catch (NoSuchAlgorithmException e) {
//			throw new NoSuchAlgorithmException();
//		}
//	}
//	
//	public static String encrypt(String plaintext) throws NoSuchAlgorithmException {
//		return encrypt(plaintext, null);
//	}
//	
//	public static boolean compare(String hashedText, String plaintext) {
//		if (hashedText == null || plaintext == null) {
//			return false;
//		}
//		
//		boolean result = false;
//		byte[] salt = extractSalt(hashedText);
//		System.out.println("extract salt: " + Arrays.toString(salt));
//		System.out.println("extract hash: " + extractHash(hashedText));
//		try {
//			String hashedPlaintext = encrypt(plaintext, salt);
//			result = extractHash(hashedText).equals(hashedPlaintext);
//			System.out.println(extractHash(hashedPlaintext));
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
//		
//		return result;
//	}
}

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
}

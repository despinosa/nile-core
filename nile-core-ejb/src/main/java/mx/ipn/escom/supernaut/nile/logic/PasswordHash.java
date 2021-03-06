/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package mx.ipn.escom.supernaut.nile.logic;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 *
 * @author supernaut
 */
public class PasswordHash {
  public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";

  public static final int SALT_BYTE_SIZE = 4 * 4;
  public static final int HASH_BYTE_SIZE = 8 * 4;
  public static final int ITERATIONS = 0;


  static String decodeUTF8(byte[] bytes) {
    return new String(bytes, StandardCharsets.UTF_8);
  }

  static byte[] encodeUTF8(String string) {
    return string.getBytes(StandardCharsets.UTF_8);
  }

  public static String getSalt() {
    // Generate a random salt
    SecureRandom random = new SecureRandom();
    byte[] salt = new byte[SALT_BYTE_SIZE];
    random.nextBytes(salt);
    return decodeUTF8(salt);
  }

  /**
   * Returns a salted PBKDF2 hash of the password.
   *
   * @param password the password to hash
   * @return a salted PBKDF2 hash of the password
   */
  public static String createHash(String password, byte[] salt)
      throws NoSuchAlgorithmException, InvalidKeySpecException {
    return createHash(password.toCharArray(), salt);
  }

  /**
   * Returns a salted PBKDF2 hash of the password.
   *
   * @param password the password to hash
   * @return a salted PBKDF2 hash of the password
   */
  public static String createHash(char[] password, byte[] salt)
      throws NoSuchAlgorithmException, InvalidKeySpecException {

    // Hash the password
    byte[] hash = pbkdf2(password, salt, HASH_BYTE_SIZE);
    // format iterations:salt:hash
    return decodeUTF8(hash);
  }

  /**
   * Validates a password using a hash.
   *
   * @param password the password to check
   * @param correctHash the hash of the valid password
   * @return true if the password is correct, false if not
   */
  public static boolean validatePassword(String password, String hash,
      String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
    return validatePassword(password.toCharArray(), encodeUTF8(hash),
        encodeUTF8(salt));
  }

  /**
   * Validates a password using a hash.
   *
   * @param password the password to check
   * @param correctHash the hash of the valid password
   * @return true if the password is correct, false if not
   */
  public static boolean validatePassword(char[] password, byte[] hash,
      byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
    // Decode the hash into its parameters
    // Compute the hash of the provided password, using the same salt,
    // iteration count, and hash length
    byte[] testHash = pbkdf2(password, salt, hash.length);
    // Compare the hashes in constant time. The password is correct if
    // both hashes match.
    return slowEquals(hash, testHash);
  }

  /**
   * Compares two byte arrays in length-constant time. This comparison method is used so that
   * password hashes cannot be extracted from an on-line system using a timing attack and then
   * attacked off-line.
   * 
   * @param a the first byte array
   * @param b the second byte array
   * @return true if both byte arrays are the same, false if not
   */
  private static boolean slowEquals(byte[] a, byte[] b) {
    int diff = a.length ^ b.length;
    for (int i = 0; i < a.length && i < b.length; i++)
      diff |= a[i] ^ b[i];
    return diff == 0;
  }

  /**
   * Computes the PBKDF2 hash of a password.
   *
   * @param password the password to hash.
   * @param salt the salt
   * @param iterations the iteration count (slowness factor)
   * @param bytes the length of the hash to compute in bytes
   * @return the PBDKF2 hash of the password
   */
  private static byte[] pbkdf2(char[] password, byte[] salt, int bytes)
      throws NoSuchAlgorithmException, InvalidKeySpecException {
    PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, bytes * 8);
    SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
    return skf.generateSecret(spec).getEncoded();
  }

}

 package org.dhbw.otp.filters;
 
 import java.util.Arrays;
import java.util.Random;

import org.apache.commons.codec.binary.Base32;
 
 public class GoogleSecretKey
 {
   private String secretKeyEncoded;
   
   /**
    * The number of bits of a secret key in binary form. Since the Base32
    * encoding with 8 bit characters introduces an 160% overhead, we just need
    * 80 bits (10 bytes) to generate a 16 bytes Base32-encoded secret key.
    */
   private static final int SECRET_BITS = 80;

   /**
    * Number of scratch codes to generate during the key generation.
    * We are using Google's default of providing 5 scratch codes.
    */
   private static final int SCRATCH_CODES = 5;

   /**
    * Number of digits of a scratch code represented as a decimal integer.
    */
   private static final int SCRATCH_CODE_LENGTH = 8;

   /**
    * Modulus used to truncate the scratch code.
    */
   public static final int SCRATCH_CODE_MODULUS = (int) Math.pow(10, SCRATCH_CODE_LENGTH);
   
   /**
    * Length in bytes of each scratch code. We're using Google's default of
    * using 4 bytes per scratch code.
    */
   private static final int BYTES_PER_SCRATCH_CODE = 4;
   
 
   public GoogleSecretKey() {
	  // Allocating a buffer sufficiently large to hold the bytes required by
      // the secret key and the scratch codes.
      byte[] buffer = new byte[SECRET_BITS / 8 + SCRATCH_CODES * BYTES_PER_SCRATCH_CODE];
 
      // Filling the buffer with random numbers.
      // Notice: you want to reuse the same random generator
      // while generating larger random number sequences.
      new Random().nextBytes(buffer);
     

	  // Getting the key and converting it to Base32
	  Base32 codec = new Base32();
	  byte[] secretKey = Arrays.copyOf(buffer, SECRET_BITS / 8);
	  byte[] bEncodedKey = codec.encode(secretKey);
	  String encodedKey = new String(bEncodedKey);
	  this.secretKeyEncoded = encodedKey;

   }
 
   public String getSecretKeyEncoded()
   {
     return this.secretKeyEncoded;
   }
   
   public static String getQRBarcodeURL(String user, String host,String secret) {
		String format = "https://chart.googleapis.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=otpauth://totp/%s@%s%%3Fsecret%%3D%s";
		return String.format(format, user, host, secret);
	}
 }

package com.cs;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class UnbreakableEncryption {
    //XOR - exclusive or. Returns true only if exactly one operand is true. In java XOR is ^
    // 0^1 and 1^0 are true. 0^0 and 1^1 are false.
    public static void main(String[] args){
        String data = "Hello World!";
        KeyPair kp = encrypt(data);

        String decrypted = decrypt(kp);

        System.out.println(decrypted);
    }

    public static String decrypt(KeyPair keyPair){
        byte[] decrypted = new byte[keyPair.key1.length];

        for(int i = 0; i < decrypted.length; i++){
            decrypted[i] = (byte)(keyPair.key1[i] ^ keyPair.key2[i]);
        }

        return new String(decrypted);
    }

    public static KeyPair encrypt(String data){
        byte[] original = data.getBytes();
        byte[] dummy = randomKey(data.length());

        byte[] encryptedKey = new byte[data.length()];

        for(int i = 0; i < original.length; i++){
            encryptedKey[i] = (byte) (original[i] ^ dummy[i]);
        }

        return new KeyPair(dummy, encryptedKey);
    }

    private static byte[] randomKey(int length){
        byte[] dummy = new byte[length];
        Random random = new Random();

        random.nextBytes(dummy);
        return dummy;
    }

    static class KeyPair{
        public final byte[] key1;
        public final byte[] key2;

        public KeyPair(byte[] key1, byte[] key2){
            this.key1 = key1;
            this.key2 = key2;
        }
    }
}

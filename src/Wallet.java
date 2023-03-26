package src;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;

public class Wallet {
    private KeyPair keys;
    private SecureRandom secureRandom;
    
    Wallet() throws NoSuchAlgorithmException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("DSA"); // NoSuchAlgorithmException
        generator.initialize(1024);
        keys = generator.generateKeyPair();
        
        secureRandom = new SecureRandom();
        
    }
    public PublicKey getPublicKey() {
        return this.keys.getPublic();
    }
    byte[] sign(byte[] data) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance("SHA256WithDSA"); // NoSuchAlgorithmException
        signature.initSign(keys.getPrivate(), secureRandom); // InvalidKeyException
        signature.update(data); // SignatureException

        return signature.sign();
    }

    // Вообще ты не тут должен быть, но пока не знаю куда сунуть
    // Думаю сделать общую функцию verifyTransaction в src/tools
    boolean verifySign(byte[] data, byte[] digitalSignature) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance("SHA256WithDSA"); // NoSuchAlgorithmException
        signature.initVerify(getPublicKey()); // InvalidKeyException
        signature.update(data); // SignatureException
        return signature.verify(digitalSignature); // SignatureException
    }
}

/*
 * Короче, задача кошелька:
 * 1. Создавать транзакции
 * 2. Хранить информацию о своих UTXO
 * 3. Показывать свой публичный ключ
 */
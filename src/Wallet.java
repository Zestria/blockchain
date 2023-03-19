package src;
// разумается настоящий секретный ключ куда длиньше, но мы для примера будем брать небольшие(до 6 знаков)
public class Wallet {
    private int privateKey;
    private int publicKey;
    
    Wallet(int key) {
        this.privateKey = key;
        this.publicKey = 0; // Сгенерирование из секретного ключа
    }
    Wallet() {
        this.privateKey = generatePrivateKey(); // Сгенерирование рандомного ключа
        this.publicKey = 0; // Сгенерирование из секретного ключа
    }
    
    public int getPrivateKey() {
        return privateKey;
    }
    public int getPublicKey() {
        return publicKey;
    }
    
    public int generatePrivateKey() {
        return (int) Math.round(Math.random()*1000000);
    }
}

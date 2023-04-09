package src;

public class Block {
    int index;
    int nonce;
    long timestamp;
    String data;
    String prevHash;
    
    Block(String hash, String data, int index, long timestamp, int nonce){
        this.index = index;
        this.prevHash = hash;
        this.data = data;
        this.timestamp = timestamp;
        this.nonce = nonce;
    }
}
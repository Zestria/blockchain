package src;

class Block {
    int index;
    int nonce;
    long timestamp;
    String data;
    String hashOfPrevBlock;
    Block(String hash, String data, int index){
        this.index = index;
        this.hashOfPrevBlock = hash;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
        this.nonce = 0;
    }
}
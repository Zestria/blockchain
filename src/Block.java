package src;

class Block {
    int index;
    long timestamp;
    String data;
    String hashOfPrevBlock;
    Block(String hash, String data, int index){
        this.index = index;
        this.hashOfPrevBlock = hash;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }
}
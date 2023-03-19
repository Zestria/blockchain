package src;

class Block {
    int index;
    long timestamp;
    String data;
    String hash;
    Block(String hash, String data, int index){
        this.index = index;
        this.hash = hash;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }
}
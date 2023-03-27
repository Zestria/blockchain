package src;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import src.tools.SHA256;

class Blockchain {
    private ArrayList<Block> chain;
    private ArrayList<Transaction> currentTransactions;
    private int index;
    
    Blockchain() {
        chain = new ArrayList<>();
        currentTransactions = new ArrayList<>();
        index = 0;
        createGenesisBlock();
    }
    Blockchain(String path) {

    }

    private void createGenesisBlock() {
        String data = "First block data";
        String prevHash = null;
        chain.add(new Block(prevHash, data, index++));
    }
    private void createBlock(String data, String hash) {
        chain.add(new Block(hash ,data, index++));
    }
    void createTransaction(String sender, String recipient, int sum, String signature) throws NoSuchAlgorithmException {
        currentTransactions.add(new Transaction(sender, recipient, sum, signature));
        if(currentTransactions.size()!=2) return;
        StringBuilder data = new StringBuilder();
        for(int i = 0; i < 2; i++) {
            data
            .append(currentTransactions.get(i).toString())
            .append(" ");
        }
        currentTransactions.clear();
        createBlock(data.toString(), hashBlock(null));
    }   
    
    
    static String hashBlock(Block block) throws NoSuchAlgorithmException {
        byte[] timestamp = ByteBuffer.allocate(8).putLong(block.timestamp).array();
        byte[] data = block.data.getBytes(StandardCharsets.UTF_8);
        byte[] prevHash = block.hashOfPrevBlock.getBytes(StandardCharsets.UTF_8);
        
        String hash = "";
        do {
            int count = 0;
            byte[] nonce = ByteBuffer.allocate(4).putInt(block.nonce++).array(); // здесь то он плюсуется
            
            byte[] bytes = new byte[timestamp.length+data.length+prevHash.length+nonce.length];
            for(byte b : timestamp) bytes[count++] = b;
            for(byte b : nonce) bytes[count++] = b;
            for(byte b : data) bytes[count++] = b;
            for(byte b : prevHash) bytes[count++] = b;
            hash = SHA256.hash(bytes);
        } while(!hash.substring(0, 4).equals("aaaa"));
        block.nonce--;
        return hash;
    }
    static boolean validate(String hash, Block block) throws NoSuchAlgorithmException {
        if(!hash.substring(0, 4).equals("aaaa"))return false;
        
        byte[] timestamp = ByteBuffer.allocate(8).putLong(block.timestamp).array();
        byte[] data = block.data.getBytes(StandardCharsets.UTF_8);
        byte[] prevHash = block.hashOfPrevBlock.getBytes(StandardCharsets.UTF_8);
        byte[] nonce = ByteBuffer.allocate(4).putInt(block.nonce).array();
        byte[] bytes = new byte[timestamp.length+data.length+prevHash.length+nonce.length];
        int count = 0;
        for(byte b : timestamp) bytes[count++] = b;
        for(byte b : nonce) bytes[count++] = b;
        for(byte b : data) bytes[count++] = b;
        for(byte b : prevHash) bytes[count++] = b;
        String hash2 = SHA256.hash(bytes);
        if(!hash2.equals(hash)) return false;
        return true;
    }

    public String toString() {
        StringBuilder output = new StringBuilder();

        for(int i = 0; i < chain.size(); i++) {
            Block block = chain.get(i);
            output
            .append("\nBlock №").append(block.index)
            .append("   data: ").append(block.data)
            .append("   hash: ").append(block.hashOfPrevBlock)
            .append("   timestamp: ").append(block.timestamp)
            ;
        }

        return output.toString();
    }
}
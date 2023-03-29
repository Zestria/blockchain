package src;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import src.tools.SHA256;

class Blockchain {
    private ArrayList<Block> chain;
    private int index;
    
    Blockchain(String path) {
        chain = new ArrayList<>();
        index = 0;
        createGenesisBlock();
    }

    private void createGenesisBlock() {
        String data = "Genesis block";
        chain.add(new Block(null, data, index++));
    }
    void createBlock(String data) throws NoSuchAlgorithmException {
        String prevHash = hashBlock(getLastBlock());
        if(validate(prevHash, getLastBlock()))
            chain.add(new Block(prevHash, data, index++));
        else
            System.out.println("The previous block's hash is not valid.");
    }
       
    Block getLastBlock() {
        return chain.get(chain.size()-1);
    }
    
    static String hashBlock(Block block) throws NoSuchAlgorithmException {
        byte[] timestamp = ByteBuffer.allocate(8).putLong(block.timestamp).array();
        byte[] data = block.data.getBytes(StandardCharsets.UTF_8);
        byte[] prevHash = block.prevHash.getBytes(StandardCharsets.UTF_8);
        
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
        byte[] prevHash = block.prevHash.getBytes(StandardCharsets.UTF_8);
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
}
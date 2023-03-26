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
    
    
    String hashBlock(Block block) throws NoSuchAlgorithmException {
        byte[] timestamp =  ByteBuffer.allocate(8).putLong(block.timestamp).array();
        byte[] data = block.data.getBytes(StandardCharsets.UTF_8);
        byte[] prevHash = block.hashOfPrevBlock.getBytes(StandardCharsets.UTF_8);
        int count = 0;

        byte[] bytes = new byte[timestamp.length+data.length+prevHash.length];
        for(byte b : timestamp) bytes[count++] = b;
        for(byte b : data) bytes[count++] = b;
        for(byte b : prevHash) bytes[count++] = b;
        
        return SHA256.hash(bytes);
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
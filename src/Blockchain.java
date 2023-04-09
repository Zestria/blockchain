package src;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import src.tools.SHA256;

class Blockchain {
    private int index;
    private int difficulty = 4;
    final String DIR;
    
    Blockchain(String path) throws Exception {
        DIR = path;
        File dir = new File(DIR);
        this.index = dir.list().length > 1 ? dir.list().length-2 : 0;
        if(this.index==0) createGenesisBlock();
    }

    private void createGenesisBlock() throws Exception {         
        Block genesisBlock = new Block("0", "genesis block", 0, System.currentTimeMillis(), -1);

        Tool.writeFile(this.DIR+"currentHash", hashBlock(genesisBlock)); // он незаметно изменяет nonce
        Tool.writeFile(this.DIR+"block0", Tool.createJSON(genesisBlock));   
    }
    
    void createBlock(String data) throws Exception {
        String prevHash = Tool.readFile(this.DIR+"currentHash");

        Block block = new Block(prevHash, data, ++this.index, System.currentTimeMillis(), -1);
        String fileName = "block"+index;

        Tool.writeFile(this.DIR+"currentHash", hashBlock(block));
        Tool.writeFile(this.DIR+fileName, Tool.createJSON(block));      
    }
       
    Block getBlock(int index) throws Exception {
        // получаем блок по индексу
        return Tool.parseJSON(Tool.readFile(this.DIR+"block"+index));
    }
    
    String hashBlock(Block block) throws Exception {
        byte[] timestamp = ByteBuffer.allocate(8).putLong(block.timestamp).array();
        byte[] data = block.data.getBytes(StandardCharsets.UTF_8);
        byte[] prevHash = block.prevHash.getBytes(StandardCharsets.UTF_8);
        
        String hash = "";
        String target = new String(new char[this.difficulty]).replace('\0', '0');
        do {
            int count = 0;
            byte[] nonce = ByteBuffer.allocate(4).putInt(++block.nonce).array();
            
            byte[] bytes = new byte[timestamp.length+data.length+prevHash.length+nonce.length];
            for(byte b : timestamp) bytes[count++] = b;
            for(byte b : nonce) bytes[count++] = b;
            for(byte b : data) bytes[count++] = b;
            for(byte b : prevHash) bytes[count++] = b;
            hash = SHA256.hash(bytes);
        
        } while(!hash.substring(0, difficulty).equals(target));
        
        return hash;
    }
    
    boolean validateHash(String hash, Block block) throws Exception {
        String target = new String(new char[this.difficulty]).replace('\0', '0');

        if(!hash.substring(0, difficulty).equals(target)) 
            return false;
        
        byte[] timestamp = ByteBuffer.allocate(8).putLong(block.timestamp).array(),
               data      = block.data.getBytes(StandardCharsets.UTF_8),
               prevHash  = block.prevHash.getBytes(StandardCharsets.UTF_8),
               nonce     = ByteBuffer.allocate(4).putInt(block.nonce).array(),
               bytes     = new byte[timestamp.length+data.length+prevHash.length+nonce.length];
        
        int count = 0;
        for(byte b : timestamp) bytes[count++] = b;
        for(byte b : nonce) bytes[count++] = b;
        for(byte b : data) bytes[count++] = b;
        for(byte b : prevHash) bytes[count++] = b;
        
        if(!SHA256.hash(bytes).equals(hash)) return false;
        return true;
    }
}
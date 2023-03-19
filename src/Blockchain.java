package src;
import java.util.ArrayList;

class Blockchain {
    private ArrayList<Block> chain;
    private ArrayList<Transaction> currentTransactions;
    private int index;
    
    Blockchain() {
        chain = new ArrayList<>();
        currentTransactions = new ArrayList<>();
        index = 0;
    }

    void createFirstBlock() {
        String data = "First block data";
        String prevHash = null;
        chain.add(new Block(prevHash, data, index++));
    }
    void createBlock(String data, String hash) {
        chain.add(new Block(hash ,data, index++));
    }
    void createTransaction(String sender, String recipient) {
        currentTransactions.add(new Transaction(sender, recipient));
        if(currentTransactions.size()!=2) return;
        StringBuilder data = new StringBuilder();
        for(int i = 0; i < 2; i++) {
            data
            .append(currentTransactions.get(i).toString())
            .append(" ");
        }
        currentTransactions.clear();
        createBlock(data.toString(), getHash());
    }   
    
    
    String getHash() {
        return "hash";
    }

    public String toString() {
        StringBuilder output = new StringBuilder();

        for(int i = 0; i < chain.size(); i++) {
            Block block = chain.get(i);
            output
            .append("\nBlock №").append(block.index)
            .append("   data: ").append(block.data)
            .append("   hash: ").append(block.hash)
            .append("   timestamp: ").append(block.timestamp)
            ;
        }

        return output.toString();
    }
}
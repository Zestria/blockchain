package src;



public class Main {
    public static void main(String[] args) throws Exception {
        Blockchain slavius = new Blockchain("./blockchaindata");
        slavius.getLastBlock();
        slavius.createBlock("I've created block");
        System.out.println(slavius.getLastBlock().prevHash);
    }
}

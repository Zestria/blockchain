package src;


class Transaction {
    String sender;
    String recipient;
    int sum;
    String signature;
    Transaction(String sender, String recipient, int sum, String signature) {
        this.sender = sender;
        this.recipient = recipient;
        this.sum = sum;
        this.signature = signature;
    }
    public String toString() {
        return sender+"have sended "+recipient+" "+sum+" coins";
    }
}
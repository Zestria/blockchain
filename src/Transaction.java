package src;


class Transaction {
    String sender;
    String recipient;
    Transaction(String sender, String recipient) {
        this.sender = sender;
        this.recipient = recipient;
    }
    public String toString() {
        return "deal "+sender+" with "+recipient+".";
    }
}
import java.util.List;

public class Account {
    String number;
    Long balance;
    Boolean isLocked;
    List<Transaction> transactions;

    public void setNumber(String number) {
        this.number = number;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Boolean getLocked() {
        return isLocked;
    }

    public void setLocked(Boolean locked) {
        isLocked = locked;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public String getNumber() {
        return number;
    }

    public Long getBalance() {
        return balance;
    }

    public Boolean isLocked() {
        return isLocked;
    }
}

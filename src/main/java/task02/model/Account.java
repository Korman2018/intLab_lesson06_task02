package task02.model;

import java.io.Serializable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account implements Serializable {
    private final long id;

    private transient Lock lock;

    private String ownerName;
    private long balance;

    public Account(long id, String ownerName, long balance) {
        this.id = id;
        this.ownerName = ownerName;
        this.balance = balance;
        lock = new ReentrantLock();
    }

    public long getId() {
        return id;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public Lock getLock() {
        return lock;
    }

    public void initLock() {
        if (lock == null) {
            lock = new ReentrantLock();
        }
    }

    @Override
    public String toString() {
        return ownerName + " id=" + id + " balance:" + balance;
    }
}

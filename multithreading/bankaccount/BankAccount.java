package multithreading.bankaccount;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {
    private int balance=100;

    private final Lock lock=new ReentrantLock();

    public void withdraw(int ammount){
        System.out.println(Thread.currentThread().getName()+"attempting to withdraw"+ammount);
        try {
            if(lock.tryLock(1000, TimeUnit.MILLISECONDS)){
                if(balance>=ammount){
                    try{
                        System.out.println(Thread.currentThread().getName()+"proceeding with withdraw");
                        Thread.sleep(3000);
                        balance-=ammount;
                        System.out.println(Thread.currentThread().getName()+"completing withdrawal.remaining balance: "+balance);
                    }catch(Exception e){
                        Thread.currentThread().interrupt();
                    }finally{
                        lock.unlock();
                    }
                }else{
                    System.out.println(Thread.currentThread().getName()+"insufficient balance");
                }
            }else{
                System.out.println(Thread.currentThread().getName()+"could not acquire the lock, will try later");
            }
        }catch(Exception e){
            Thread.currentThread().interrupt();
        }
    }
}

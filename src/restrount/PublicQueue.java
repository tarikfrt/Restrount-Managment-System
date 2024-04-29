package restrount;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

import restrount.entites.Chef;
import restrount.entites.Customer;
import restrount.entites.Waiter;

public class PublicQueue {

	
	public static  List<Customer> customerQueue =new CopyOnWriteArrayList<>();
    public static  List<Customer> sittingQueue = new CopyOnWriteArrayList<>();
    public static  List<Customer> orderQueue = new CopyOnWriteArrayList<>();
    public static  List<Customer> eatQueue = new CopyOnWriteArrayList<>();
    public static  List<Customer> payQueue = new CopyOnWriteArrayList<>();
    
    public static  List<Waiter> waiterQueue = new CopyOnWriteArrayList<>();
    public static  List<Chef> chefQueue = new CopyOnWriteArrayList<>();
    
    
    public static ReentrantLock chefLock = new ReentrantLock();
    public static ReentrantLock waiterLock = new ReentrantLock();
    
    public static boolean isOpen;
    
}

package restrount.entites;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

import restrount.PublicQueue;


public class Chef implements Runnable{
	
	Semaphore semaphore = new Semaphore(2);
	public Thread chefThread;
	public int id;
	public int numberOfOrder;
	public boolean isFree;
	
	public Chef(int id, boolean isFree,int numberOfOrder) {
		super();
		this.id = id;
		this.isFree = isFree;
		this.numberOfOrder = numberOfOrder;
		
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
			
		for (Customer customer : PublicQueue.orderQueue) {
			
			PublicQueue.chefLock.lock();
			if(customer.situationOrder==true && customer.situationCook == false)
			{	
				
				System.out.println("Chef "+id+" Musteri "+customer.id+" nin yemegini hazirliyor ");
				customer.situationCook = true;
			}
			PublicQueue.chefLock.unlock();
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		}
			
		
		
		
		
	}
		
	
	
	public void start() throws InterruptedException
	{
		if(chefThread == null)
		{
			String name = Integer.toString(id);
			chefThread = new Thread(this,name);
		
			chefThread.start();
		}
	}

}

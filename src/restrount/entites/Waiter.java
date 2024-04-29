package restrount.entites;

import java.util.concurrent.locks.ReentrantLock;

import restrount.PublicQueue;

public class Waiter implements Runnable{
	
	ReentrantLock lock = new ReentrantLock();
	
	public Thread waiterThread;
	public int id;
	public boolean isFree;
	
	public Waiter(int id, boolean isFree) {
		super();
		this.id = id;
		this.isFree = isFree;
		
		
	}

	@Override
	public void run() {
		
		
		while(true)
		{
		
			
		for (Customer customer : PublicQueue.orderQueue) {
			
			PublicQueue.waiterLock.lock();
			if(customer.situationLogin==true && customer.situationOrder == false)
			{
				
				System.out.println("Garson "+id+" Musteri "+customer.id+" nin siparisini aliyor");
				customer.situationOrder= true;
			}
			PublicQueue.waiterLock.unlock();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			}
		
		}	
	}
	
	public void start() throws InterruptedException
	{
		if(waiterThread == null)
		{
			String name = Integer.toString(id);
			waiterThread = new Thread(this,name);
		
			waiterThread.start();
		}
	}
	
	

}

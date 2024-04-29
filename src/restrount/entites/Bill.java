package restrount.entites;

import java.util.concurrent.locks.ReentrantLock;

import restrount.PublicQueue;

public class Bill implements Runnable{

	public Thread billThread;
	ReentrantLock lock = new ReentrantLock();
	public boolean isFree;
	String name;
	
	public Bill(boolean isFree,String name) {
		super();
		
		this.isFree = isFree;
		this.name = name;
		
		
	}

	@Override
	public void run() {
		
		// TODO Auto-generated method stub
		while(true)
		{
			
		for (Customer customer : PublicQueue.orderQueue) {
			
			if(customer.situationPay == false && customer.situationEat == true)
			{
				
				
				
				System.out.println("Kasa Musteri "+customer.id+" in hesabini kapatiliyor");
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.out.println("Samet Usta eline saglik ben gidiyom Musteri "+customer.id);
				customer.situationPay = true;
				
				
				customer.semaphore.release();
				customer.situationSit = false;
				customer.situationLogin = false;
				customer.situationOrder = false;
				customer.situationCook = false;
				customer.situationEat= false;
				customer.situationPay = false;
				PublicQueue.sittingQueue.remove(customer);
				PublicQueue.orderQueue.remove(customer);
				
				
				
			}
			
			}
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
	}
	
	public void start()
	{
		if(billThread == null)
		{
			String name = this.name;
			billThread = new Thread(this,name);
			billThread.start();
		}
	}
	
}

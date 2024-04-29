package restrount.entites;

import restrount.PublicQueue;

public class CustomerEat implements Runnable{
	public Thread thread;
	String name = "customer";
	
	public CustomerEat() {
		super();
	}

	@Override
	public void run() {
		while(true)
		{
			
		for (Customer customer : PublicQueue.orderQueue) 
		{
			
			if(customer.situationEat == false && customer.situationCook == true)
			{
				
				System.out.println("Musteri "+customer.id+" yemegini yiyiyor ");
				
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				customer.situationEat = true;
				
				
			}
		}
		
		}
		
		
	}

	public void start()
	{
		if(thread == null)
		{
			String name = this.name;
			thread = new Thread(this,name);
			thread.start();
		}
	}
}

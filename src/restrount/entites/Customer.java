package restrount.entites;

import java.util.concurrent.Semaphore;

import restrount.PublicQueue;

public class Customer implements Runnable{
	
	public Thread customerThread;
	public int id;
	public boolean isPrivate;
	public boolean situationLogin;
	public boolean situationSit;
	public boolean situationOrder;
	public boolean situationCook;
	public boolean situationPay;
	public boolean situationEat;
	int control = 0;
	public long firstSecond;
	public long waitingSecond;
	
	public static final Semaphore semaphore = new Semaphore(6);
	
	public Customer(int id, boolean isPrivate, boolean situationSit, boolean situationOrder, boolean situationPay,boolean situationCook,boolean situationEat) {
		super();
		this.id = id;
		this.isPrivate = isPrivate;
		this.situationSit = situationSit;
		this.situationOrder = situationOrder;
		this.situationCook = situationCook;
		this.situationPay = situationPay;
		this.situationEat = situationEat;

	}

	@Override
	public void run() {
		
		//while(PublicQueue.customerQueue != null)
		
			situationLogin = true;
			if(isPrivate)
			{
				System.out.println("Oncelikli Musteri "+id+" siraya girdi");
			}
			else
				System.out.println("Musteri "+id+" siraya girdi");
			
			try {
				semaphore.acquire();
				waitingSecond = System.currentTimeMillis();
				long sonuc = waitingSecond-firstSecond;
				if(sonuc>20000)
				{
					System.out.println("Cok bekledim o yüzden restoandan ayriliyorum !!!. Musteri" +id);
					semaphore.release();
					situationLogin = false;
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(situationLogin)
			{
			System.out.println("Musteri "+id+" masaya oturdu");
			situationSit = true;
			PublicQueue.sittingQueue.add(this);
			PublicQueue.orderQueue.add(this);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
	
		
	}
	
	public void start()
	{
		if(customerThread == null)
		{
			String name = Integer.toString(id);
			customerThread = new Thread(this,name);
			customerThread.start();
		}
	}
	
	

}

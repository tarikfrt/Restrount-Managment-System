package restrount;

import java.util.Scanner;

import restrount.entites.Bill;
import restrount.entites.Chef;
import restrount.entites.Customer;
import restrount.entites.CustomerEat;
import restrount.entites.Table;
import restrount.entites.Waiter;

public class Main {
	
	

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		
		System.out.println("Restoran Yonetim Sistemine Hosgeldiniz");
		
		System.out.println("1-)Simulasyon Uygula");
		System.out.println("2-)2.Problem Hesapla");
		
		Scanner scanner = new Scanner(System.in);
		int state = scanner.nextInt();

		
		switch (state) {
		case 1: {
			
			Scanner scannerPrivate = new Scanner(System.in);
	        Scanner scannerCustomer = new Scanner(System.in);
	        int countCustomer;
			
			
	      
	        
	        //Create Tables
	        Table table1 = new Table(1, true, 0, 0);
	        Thread.sleep(100);
	        Table table2 = new Table(2, true, 0, 0);
	        Thread.sleep(100);
	        Table table3 = new Table(3, true, 0, 0);
	        Thread.sleep(100);
	        Table table4 = new Table(4, true, 0, 0);
	        Thread.sleep(100);
	        Table table5 = new Table(5, true, 0, 0);
	        Thread.sleep(100);
	        Table table6 = new Table(6, true, 0, 0);
	        Thread.sleep(100);
	        
	        for (int i=0;i<3;i++) {
        		Waiter waiter = new Waiter(i+1, false);
        		PublicQueue.waiterQueue.add(waiter);
				waiter.start();
				Thread.sleep(100);
				
			}
	        
	        for (int i=0;i<2;i++) {
        		Chef chef = new Chef(i+1, false, 0);
        		PublicQueue.chefQueue.add(chef);
				chef.start();
				Thread.sleep(300);
			}
        	
        	for(int i = 0; i<1;i++)
        	{
        		CustomerEat customerEat = new CustomerEat();
        		customerEat.start();
        		Thread.sleep(300);
        	}
        	
        	
        	
        	for(int i = 0; i<1;i++)
        	{
        		Bill bill = new Bill(true,"Kasa");
        		bill.start();
        		Thread.sleep(300);
        	}
	        
	        int privateCustomer = 0;
	    	int normalCustomer = 0;
	    	int customerCount = 1;
	    	//Get Number of Customer from User
	        do {
	        	
	        	
	        	
	        	System.out.println("Oncelikli Musteri sayisini giriniz");
	        	privateCustomer = scannerPrivate.nextInt();
	        	
	        	System.out.println("Normal Musteri sayisini giriniz");
	        	normalCustomer = scannerCustomer.nextInt();
	        	
	        	
	        	
	        	// Create customer objects and add queue
	        	for (int i = 0; i < privateCustomer ; i++) {
					Customer customer = new Customer(customerCount, true, false, false,false, false,false);
					Thread.sleep(100);
					PublicQueue.customerQueue.add(0, customer);
					customerCount++;
				}
	        	
	        	for (int i = 0; i < normalCustomer ; i++) {
					Customer customer = new Customer(customerCount, false, false, false,false, false,false);
					Thread.sleep(100);
					PublicQueue.customerQueue.add(customer);
					customerCount++;
				}
	        	
	        	PublicQueue.customerQueue.removeAll(PublicQueue.sittingQueue);
	        	
	        	for (Customer customer : PublicQueue.customerQueue) {
	        		if(customer.situationLogin == false)
	        		{
	        			customer.start();
	        			customer.firstSecond = System.currentTimeMillis();;
	    				Thread.sleep(100);
	        		}	
				}
	        	
	        	
	        	
	        	
	        	
	        	PublicQueue.customerQueue.removeAll(PublicQueue.sittingQueue);
	        	
	        	
	        	
	        	
	        	

				
			} while ((privateCustomer != 0) || (normalCustomer != 0));
	        
	        for (Customer customer : PublicQueue.customerQueue) {
				customer.customerThread.join();
				Thread.sleep(100);
			}
	        
	        for (Waiter waiter : PublicQueue.waiterQueue) {
				waiter.waiterThread.join();
				Thread.sleep(100);
				
			}
	        
	        for (Chef chef : PublicQueue.chefQueue) {
				chef.chefThread.join();
				Thread.sleep(100);
			}
	        
	        
	        
	        System.out.println("Dukkanimiz 1 saat icerisinde kapanacaktir !!!");
			
			
			
			break;
		}
		case 2: {
			

				Scanner scanner6 = new Scanner(System.in);

		        System.out.print("Simülasyon süresini saniye cinsinden girin: ");
		        int simulationTime = scanner6.nextInt();

		        int maxProfit = 0;
		        int bestTables = 0;
		        int bestWaiters = 0;
		        int bestChefs = 0;

		        // Simülasyonu başlatın
		        for (int tables = 1; tables <= 10; tables++) {
		            for (int waiters = 1; waiters <= 10; waiters++) {
		                for (int chefs = 1; chefs <= 10; chefs++) {
		                    Restaurant restaurant = new Restaurant(tables, waiters, chefs);
		                    int currentProfit = restaurant.simulate(simulationTime);

		                    if (currentProfit > maxProfit) {
		                        maxProfit = currentProfit;
		                        bestTables = tables;
		                        bestWaiters = waiters;
		                        bestChefs = chefs;
		                    }
		                }
		            }
		        }

		        System.out.println("En kazançlı durum:");
		        System.out.println("Masalar: " + bestTables + " | Garsonlar: " + bestWaiters + " | Aşçılar: " + bestChefs);
		        System.out.println("Toplam Kazanç: " + maxProfit);

			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + state+" Lutfen 1 ya da 2 degerlerinden birini seciniz");
		}
		
		
		
		//Create Scanner Class
		
        

	}

}

package restrount;

import java.util.LinkedList;
import java.util.Queue;



public class Restaurant {
	
	
	private static final int CUSTOMER_PROFIT = 1;
    private static final int TABLE_COST = 1;
    private static final int WAITER_COST = 1;
    private static final int CHEF_COST = 1;
    private static final int ORDER_INTERVAL = 2;
    private static final int COOK_INTERVAL = 3;
    private static final int EAT_INTERVAL = 3;
    private static final int MAX_QUEUE_WAIT_TIME = 20;

    private int earnings = 0;
    private int costs = 0;
    private int currentTime = 0;
    private int departedCustomers = 0;

    private Queue<Customer> customerQueue = new LinkedList<Customer>();
    private int numberOfTables;
    private int numberOfWaiters;
    private int numberOfChefs;
   

    public Restaurant(int numberOfTables, int numberOfWaiters, int numberOfChefs) {
        this.numberOfTables = numberOfTables;
        this.numberOfWaiters = numberOfWaiters;
        this.numberOfChefs = numberOfChefs;
    }

    public int simulate(int seconds) {
        int servedCustomers = 0;
        departedCustomers = 0; // departedCustomers sayısını sıfırla

        for (int i = 0; i < seconds; i++) {
            currentTime++;

            // Her saniyede müşteri oluştur
            generateCustomers();

            // Masalara oturmayan müşterileri kontrol et
            checkDepartures();

            // Müşterilere, garsonlara ve aşçılara hizmet ver
            servedCustomers += serveCustomers();
            generateWaiters();
            
            generateChefs();

            // Maliyetleri hesapla
            calculateCosts();

            // Kazancı hesapla
            calculateEarnings();

            // Toplam kazanç - Toplam maliyet
            int profit = earnings - costs;

            // Sonuçları ekrana yazdır
            System.out.println("Zaman: " + currentTime + " saniye | Kazanç: " + earnings + " birim | Maliyet: " + costs + " birim | Kar: " + profit + " birim");
        }

        System.out.println("Toplam hizmet verilen müşteri sayısı: " + servedCustomers);
        System.out.println("Toplam kuyruktan ayrılan müşteri sayısı: " + departedCustomers);

        return earnings - costs;
    }

    private void generateCustomers() {
        if (currentTime % ORDER_INTERVAL == 0) {
            int customersToGenerate = 4;

            // Her saniyede en fazla numberOfTables kadar müşteri oluştur
            customersToGenerate = Math.min(customersToGenerate, numberOfTables);

            for (int i = 0; i < customersToGenerate; i++) {
                customerQueue.offer(new Customer(currentTime));
            }
        }
    }
    
    private void generateWaiters() {
        if (currentTime % ORDER_INTERVAL == 0) {
            int waitersToGenerate = 1; // Örneğin her saniyede 1 garson oluştur

            // İhtiyaç duyulan garson sayısını kontrol et
            waitersToGenerate = Math.min(waitersToGenerate, numberOfWaiters);

            // Garsonları oluştur
            for (int i = 0; i < waitersToGenerate; i++) {
                // Garson oluşturma işlemleri burada yapılır
                // waitersQueue.offer(new Waiter(currentTime));
            }
        }
    }
    
    private void generateChefs() {
        if (currentTime % COOK_INTERVAL == 0) {
            int chefsToGenerate = 1; // Örneğin her saniyede 1 aşçı oluştur

            // İhtiyaç duyulan aşçı sayısını kontrol et
            chefsToGenerate = Math.min(chefsToGenerate, numberOfChefs);

            // Aşçıları oluştur
            for (int i = 0; i < chefsToGenerate; i++) {
                // Aşçı oluşturma işlemleri burada yapılır
                // chefsQueue.offer(new Chef(currentTime));
            }
        }
    }

    private void checkDepartures() {
        Queue<Customer> tempQueue = new LinkedList<Customer>();

        while (!customerQueue.isEmpty()) {
            Customer customer = customerQueue.poll();

            // Eğer müşteri kuyrukta 20 saniyeden fazla beklediyse, kazançtan düş
            if (currentTime - customer.getArrivalTime() >= MAX_QUEUE_WAIT_TIME) {
                departedCustomers++;
            } else {
                tempQueue.offer(customer);
            }
        }

        customerQueue = tempQueue;
    }

    private int serveCustomers() {
        int customersServed = 0;

        while (!customerQueue.isEmpty() && customersServed < numberOfTables) {
            // Müşteriyi al
            Customer customer = customerQueue.poll();

            // Müşteriye hizmet et
            if (serveCustomer(customer)) {
                customersServed++;
            }

            // Eğer müşteri hizmet alamadıysa ve kuyruktan ayrıldıysa departedTime'ı güncelle
            if (!customer.hasEaten && currentTime - customer.getArrivalTime() > MAX_QUEUE_WAIT_TIME) {
                customer.setDepartedTime(currentTime);
            }
        }

        return customersServed;
    }

    private boolean serveCustomer(Customer customer) {
        // Masaya otur
        if (customer.sitAtTable()) {
            // Garson sipariş alsın
            if (currentTime % ORDER_INTERVAL == 0) {
                customer.placeOrder();
            }

            // Aşçı yemek yapsın
            if (currentTime % COOK_INTERVAL == 0) {
                customer.prepareFood();
            }

            // Müşteri yemeği yesin
            if (currentTime % EAT_INTERVAL == 0) {
                customer.eatFood();
                earnings += CUSTOMER_PROFIT;
                return true;
            }
        }

        return false;
    }

    private void calculateCosts() {
        costs = numberOfTables * TABLE_COST + numberOfWaiters * WAITER_COST + numberOfChefs * CHEF_COST;
    }

    private void calculateEarnings() {
        // Kazanç her müşteriden 1 birim, ancak kuyruktan ayrılan müşterilerin sayısını da azalt
        earnings += (customerQueue.size() - departedCustomers) * CUSTOMER_PROFIT;
    }

static class Customer {
	
    private static int idCounter = 1;
    private int id;
    private int arrivalTime;
    private boolean isSitting;
    private boolean hasOrdered;
    private boolean hasCooked;
    private boolean hasEaten;
    private int departedTime;

    public Customer(int arrivalTime) {
        this.id = idCounter++;
        this.arrivalTime = arrivalTime;
        this.departedTime = 0; // Müşteri kuyruktan ayrılma süresi
        this.isSitting = false;
        this.hasOrdered = false;
        this.hasCooked = false;
        this.hasEaten = false;
    }
    
    public int getDepartedTime() {
        return departedTime;
    }
    
    public void setDepartedTime(int departedTime) {
        this.departedTime = departedTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public boolean sitAtTable() {
        // Müşteri bir masaya oturabilirse true döner, aksi halde false
        if (!isSitting) {
            isSitting = true;
            return true;
        }
        return false;
    }

    public void placeOrder() {
        // Müşteri henüz sipariş vermediyse, sipariş alır
        if (!hasOrdered) {
            hasOrdered = true;
        }
    }

    public void prepareFood() {
        // Müşteri henüz yemek yapmadıysa, yemek yapar
        if (!hasCooked) {
            hasCooked = true;
        }
    }

    public void eatFood() {
        // Müşteri henüz yemeği yemediyse, yemek yer
        if (!hasEaten) {
            hasEaten = true;
        }
    }
}
}



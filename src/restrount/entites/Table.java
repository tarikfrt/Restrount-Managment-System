package restrount.entites;

public class Table {
	
	int id;
	boolean isFree;
	int waiterId;
	int customerID;
	
	public Table(int id, boolean isFree, int waiterId, int customerID) {
		super();
		this.id = id;
		this.isFree = isFree;
		this.waiterId = waiterId;
		this.customerID = customerID;
		
		System.out.println("Masa "+id+" hazir");
	}
	

}

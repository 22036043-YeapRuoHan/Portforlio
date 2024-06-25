
public class Products extends Outlets{
    private String product;
	private double price;
	private int id;
	private String returnpolicy;
	
	public Products(int id, String product, double price, String outletname, String returnpolicy) {
		super(outletname);
		this.id = id;
		this.price = price;
		this.product = product;
		this.returnpolicy = returnpolicy;
		// TODO Auto-generated constructor stub
	}
	public String getName() {
		return product;
	}
	public double getPrice(){
		return price;
	}
    
	public int getproductID() {
		return id;
	}
	public String getreturnpolicy() {
		return returnpolicy;
	}
}

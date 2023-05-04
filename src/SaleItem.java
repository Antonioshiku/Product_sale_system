
public class SaleItem {
	private String itemName;
	private int price;
	private int qty;

	public SaleItem() {
	};

	public SaleItem(String itemName, int price, int qty) {
		this.itemName = itemName;
		this.price = price;
		this.qty = qty;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

}

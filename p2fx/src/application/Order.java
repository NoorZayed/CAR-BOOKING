package application;

public class Order extends Car {
	private String customerName;
	private String customerMobile;

	private String orderDate;
	private String orderStatus;
	private Car car;

	public Order(String customerName, String customerMobile, String brand, String model, int year, String color,
			double price, String orderDate, String orderStatus) {
		super(brand, model, year, color, price);
		car = new Car(brand, model, year, color, price);
		this.customerName = customerName;
		this.customerMobile = customerMobile;
		this.orderDate = orderDate;
		this.orderStatus = orderStatus;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerMobile() {
		return customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	@Override
	public String toString() {
		return "order [customerName=" + customerName + ", customerMobile=" + customerMobile + ", orderStatus="
				+ orderStatus + ", orderDate=" + orderDate + "]";
	}

}

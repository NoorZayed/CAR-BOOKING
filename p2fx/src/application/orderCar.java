package application;

public abstract class orderCar {

	private String brandName;
	private String modelName;
	private String color;
	private int year;
	private double price;

	// Constructor
	public orderCar(String brandName, String modelName, String color, int year, double price) {
		this.brandName = brandName;
		this.modelName = modelName;
		this.color = color;
		this.year = year;
		this.price = price;
	}

	// Getters and setters for the properties

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}

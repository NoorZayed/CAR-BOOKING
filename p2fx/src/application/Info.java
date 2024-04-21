package application;



public class Info {
	private String brand;
	private double highestPrice;
	private double lowestPrice;
	private String highestModel;
	private String lowestModel;

	public Info(String brand) {
		this.brand = brand;
		this.highestPrice = Double.MIN_VALUE;
		this.lowestPrice = Double.MAX_VALUE;
		this.highestModel = "";
		this.lowestModel = "";
	}

	public String getBrand() {
		return brand;
	}

	public double getHighestPrice() {
		return highestPrice;
	}

	public double getLowestPrice() {
		return lowestPrice;
	}

	public String getHighestModel() {
		return highestModel;
	}

	public String getLowestModel() {
		return lowestModel;
	}

	public void updateCarInfo(Car car) {
		if (car.getPrice() > highestPrice) {
			highestPrice = car.getPrice();
		}
		if (car.getPrice() < lowestPrice) {
			lowestPrice = car.getPrice();
		}
		if (car.getModel().compareTo(highestModel) > 0) {
			highestModel = car.getModel();
		}
		if (car.getModel().compareTo(lowestModel) < 0 || lowestModel.isEmpty()) {
			lowestModel = car.getModel();
		}
	}
}

package application;

public class Brand {
	private String brand;
	private SingleLinkedList cars;

	public Brand(String brand) {
		this.brand = brand;
		this.cars = new SingleLinkedList();
	}

	public String getBrand() {
		return brand;
	}

	public void addCar(Car car) {
		cars.add(car);
	}

	public void displayCars() {
		System.out.println("Brand: " + brand);
		cars.found();
		System.out.println();
	}

	public SingleLinkedList getCars() {
		return cars;
	}

	public void setCars(SingleLinkedList cars) {
		this.cars = cars;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	@Override
	public String toString() {
		return "Brand [brand=" + brand + ", cars=" + cars + "]";
	}

}

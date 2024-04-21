package application;



public class carNode {
	private Object element;
	private carNode next;
	private Car car;
	public SingleLinkedList list = new SingleLinkedList();

	public carNode(Object element) {
		super();
		this.element = element;
		this.next = null;
		this.list = new SingleLinkedList();
	}

	public Object getElement() {
		return element;
	}

	public void setElement(Object element) {
		this.element = element;
	}

	public carNode getNext() {
		return next;
	}

	public void setNext(carNode next) {
		this.next = next;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public SingleLinkedList getList() {
		return list;
	}

	public void setList(SingleLinkedList list) {
		this.list = list;
	}
}

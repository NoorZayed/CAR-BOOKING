package application;

public class brandNode {
	private brandNode prev;
	private brandNode next;
	private Object b;
	private String brand;

	public SingleLinkedList list = new SingleLinkedList();

	public brandNode(Object brand) {
		this.prev = null;
		this.next = null;
		this.b = brand;
	}

	public brandNode getPrev() {
		return prev;
	}

	public void setPrev(brandNode prev) {
		this.prev = prev;
	}

	public brandNode getNext() {
		return next;
	}

	public void setNext(brandNode next) {
		this.next = next;
	}

	

	public Object getB() {
		return b;
	}

	public void setB(Object b) {
		this.b = b;
	}

	public SingleLinkedList getList() {
		return list;
	}

	public void setList(SingleLinkedList list) {
		this.list = list;
	}
	@Override
	public boolean equals(Object obj) {
		if (b.equals(obj))
			return true;
		return false;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	
}

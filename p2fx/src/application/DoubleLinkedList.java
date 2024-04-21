package application;

public class DoubleLinkedList {

	public static brandNode first, current;

	public brandNode last;

	private int count = 0;

	public DoubleLinkedList() {
		this.first = null;
		this.last = null;
	}

//	public boolean isEmpty() {
//		return first == null;
//	}
	public void addFirst(Brand Brand) {
		if (first == null) {
			first = last = new brandNode(Brand);

		} else {
			brandNode temp = new brandNode(Brand);
			first.setPrev(temp);
			temp.setNext(first);
			temp.setPrev(last);
			last.setNext(temp);
			first = temp;

		}
		count++;

	}

	public void addFirst(Object Brand) {
		if (first == null) {
			first = last = new brandNode(Brand);

		} else {
			brandNode temp = new brandNode(Brand);
			first.setPrev(temp);
			temp.setNext(first);
			temp.setPrev(last);
			last.setNext(temp);
			first = temp;

		}
		count++;

	}

	public void addLast(Brand Brand) {
		if (count == 0) {
			first = last = new brandNode(Brand);

		} else {
			brandNode temp = new brandNode(Brand);
			last.setNext(temp);
			temp.setNext(first);
			first.setPrev(temp);
			temp.setPrev(last);
			last = temp;
		}
		count++;

	}

	public void add(Car car) {
//		carNode c = new carNode(car);
//
//		if (first == null) {
//			first = c;
//			last = c;
//			c.next = c;
//			c.prev = c;
//		} else {
//			carNode curr = first;
//			while (curr != last && car.getBrand().compareTo(curr.car.getBrand()) > 0) {
//				curr = curr.next;
//			}
//
//			if (car.getBrand().compareTo(curr.car.getBrand()) <= 0) {
//				c.next = curr;
//				c.prev = curr.prev;
//				curr.prev.next = c;
//				curr.prev = c;
//
//				if (curr == first) {
//					first = c;
//				}
//			} else {
//				c.next = curr.next;
//				c.prev = curr;
//				curr.next.prev = c;
//				curr.next = c;
//				last = c;
//			}
//		}
	}

	public void printList() {
		if (count == 0) {
			return;
		}
		brandNode current = first;
		for (int i = 0; i < count; i++) {
			// System.out.println(current.getBrand());
			current = current.getNext();
		}
	}

	public brandNode get(Brand x) {
		brandNode current = this.getFirst();
		for (int i = 0; i < count; i++) {
			if (current.getB().equals(x.getBrand())) {
				return current;
			}
			current = current.getNext();
		}
		return null;
	}

	public brandNode get(Object x) {
		brandNode current = this.getFirst();
		for (int i = 0; i < count; i++) {
			if (current.getB().equals(x)) {
				return current;
			}
			current = current.getNext();
		}
		return null;
	}
	public boolean found(Brand x) {
	    brandNode current = this.getFirst();
	    for (int i = 0; i < count; i++) {
	        if (current.getBrand() != null && current.getBrand().equals(x.getBrand())) {
	            return true;
	        }
	        current = current.getNext();
	    }
	    return false;
	}

//	public boolean found(Brand x) {
//		brandNode current = this.getFirst();
//		for (int i = 0; i < count; i++) {
//			if (current != null && current.getBrand() != null && current.getBrand().equals(x.getBrand())) {
//				return true;
//			}
//			current = current.getNext();
//		}
//		return false;
//	}

//	public boolean found(Brand x) {
//		brandNode current = this.getFirst();
//		for (int i = 0; i < count; i++) {
//			if (current.getBrand().equals(x.getBrand())) {
//				return true;
//			}
//			current = current.getNext();
//		}
//		return false;
//	}
	public boolean founds(Object element) {
		brandNode current = this.getFirst();
		for (int i = 0; i < count; i++) {
			if (current.getB().equals(element)) {
				return true;
			}
			current = current.getNext();
		}
		return false;
	}

//	public void found() {
//		if (first == null) {
//			System.out.println("No cars in the list.");
//		} else {
//			brandNode curr = first;
//			do {
//				System.out.println(curr.car);
//				curr = curr.next;
//			} while (curr != first);
//		}
//	}
	public boolean removeFirst() {
		if (count == 0) {
			return false;
		}
		if (count == 1) {
			first = last = null;
		} else {
			brandNode c = first;
			first = first.getNext();
			first.setPrev(null);
			c = null;
		}
		count--;
		return true;
	}

	public boolean removeLast() {
		if (count == 0) {
			return false;
		}
		if (count == 1) {
			first = last = null;
		} else {
			brandNode current = first;
			for (int i = 0; i < count; i++) {
				current = current.getNext();
			}
			last.setPrev(current.getPrev());
			last = current;
			last.setNext(null);
		}
		count--;
		return true;
	}

	public boolean remove(int index) {
		System.out.println(count);
		brandNode prev = first;
		if (index == 1) {
			return removeFirst();
		}
		if (index == count) {
			return removeLast();
		}
		if (index <= 0 || index > count) {
			return false;
		} else {
			brandNode current = first;
			for (int i = 1; i < index; i++) {
				prev = current;
				current = current.getNext();
			}
			prev.setNext(current.getNext());
			current.getNext().setPrev(prev);
			count--;
			return true;
		}
	}

	public boolean remove(Brand brand) {
		if (count == 0)
			return false;
		if (brand.getBrand().equals(first.getBrand()))
			return removeFirst();
		if (brand.getBrand().equals(last.getBrand()))
			return removeLast();
		else {
			brandNode current = first.getNext();
			for (int i = 1; i < count - 1; i++) {
				if (current.getBrand().equals(brand.getBrand()))
					return remove(i);
				current = current.getNext();
			}
			return false;
		}
	}

	public boolean isEmpty() {
		return first == null && last == null;
	}

	public static brandNode getFirst() {
		return first;
	}

	public static void setFirst(brandNode first) {
		DoubleLinkedList.first = first;
	}

	public static brandNode getCurrent() {
		return current;
	}

	public static void setCurrent(brandNode current) {
		DoubleLinkedList.current = current;
	}

	public brandNode getLast() {
		return last;
	}

	public void setLast(brandNode last) {
		this.last = last;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}

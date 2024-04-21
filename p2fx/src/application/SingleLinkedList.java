package application;

public class SingleLinkedList {
	public carNode first, last;
	int count = 0;

	public void addFirst(Object element) {
		if (first == null) {
			first = last = new carNode(element);
		} else {
			carNode temp = new carNode(element);
			temp.setNext(first);
			first = temp;
		}
	}

	public void addLast(Object element) {
		if (count == 0) {
			first = last = new carNode(element);
		} else {
			carNode temp = new carNode(element);
			last.setNext(temp);
			last = temp;
		}
	}

	public void add(Car car) {
		carNode newNode = new carNode(car);

		if (first == null) {
			first = newNode;
		} else if (car.getBrand().equals(first.getCar().getBrand())) {
			newNode.setNext(first);
			first = newNode;
		} else {
			carNode curr = first;
			while (curr.getNext() != null && !car.getBrand().equals(curr.getNext().getCar().getBrand())) {
				curr = curr.getNext();
			}
			newNode.setNext(curr.getNext());
			curr.setNext(newNode);
		}
	}

	public void found() {
		if (first == null) {
			System.out.println("No cars in the list.");
		} else {
			carNode curr = first;
			while (curr != null) {
				System.out.println(curr.getCar());
				curr = curr.getNext();
			}
		}
	}

	public boolean removeFirst() {
		if (count == 0) {
			return false;
		}
		if (count == 1) {
			first = last = null;
		} else {
			carNode current = first;
			first = first.getNext();
			current = null;
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
			carNode current = first;
			for (int i = 0; i < count; i++) {
				current = current.getNext();
			}
			last = current;
			last.setNext(null);
		}
		count--;
		return true;
	}

	public boolean remove(int index) {
		carNode prev = first;
		if (count == 1) {
			return removeFirst();
		}
		if (index == count) {
			return removeLast();
		}
		if (index <= 0 || index > count) {
			return false;
		} else {
			carNode current = first.getNext();
			for (int i = 1; i < index; i++) {
				prev = current;
				current = current.getNext();
			}
			prev.setNext(current.getNext());
			current.setNext(null);
			count--;
			return true;
		}
	}

	public boolean remove(Object element) {
		if (count == 0)
			return false;
		if (element.equals(first.getCar()))
			return removeFirst();
		if (element.equals(last.getCar()))
			return removeLast();
		else {
			carNode current = first.getNext();
			for (int i = 1; i < count - 1; i++) {
				System.out.println(i + "  " + current.getCar());
				if (current.getCar().equals(element))
					return remove(i);
				current = current.getNext();
			}
			return false;
		}
	}

	public void printList() {
		if (count == 0) {
			return;
		}
		carNode current = first;
		for (int i = 0; i < count; i++) {
			System.out.println(current.getCar().toString());
			current = current.getNext();
		}
	}

//	public boolean foundm(Car x) {
//		carNode current = this.getFirst();
//		for (int i = 0; i < count; i++) {
//			if (current.getCar().equals(x.getBrand())) {
//				return true;
//			}
//			current = current.getNext();
//		}
//		return false;
//	}
	public boolean found(Car x) {
		carNode current = this.getFirst();
		for (int i = 0; i < count; i++) {
			if (current.getCar().getBrand().equals(x.getBrand())) {
				return true;
			}
			current = current.getNext();
		}
		return false;
	}

	public carNode getFirst() {
		return first;
	}

	public void setFirst(carNode first) {
		this.first = first;
	}

	public carNode getLast() {
		return last;
	}

	public void setLast(carNode last) {
		this.last = last;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}

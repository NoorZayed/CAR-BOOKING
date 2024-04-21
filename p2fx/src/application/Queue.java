package application;

public class Queue {
	int front, rear, capacity;
	Order[] d;

	public Queue() {
//		this.capacity = 10; // Set a default capacity value
//	    d = new Order[capacity];
//	    front = rear = capacity - 1;
	}

	public Queue(int capacity) {
		this.capacity = capacity;
		d = new Order[capacity];
		front = rear = capacity - 1;
	}

	public boolean isEmpty() {
		return (front == rear);
	}

	public boolean isFull() {
		if (nextRear() == front)
			return true;
		return false;
	}

	public int nextRear() {
		if (rear == capacity - 1) {
			// rear = 0; // Increment the rear after returning its value
			return 0;
		}
		return ++rear;
	}

	public int nextFront() {
		if (front == capacity - 1)
			return 0;
		return front++;
	}

	public void enQueue(Order order) {
		if (isFull()) {
			System.out.println("Queue is Full!");
		}
		rear = nextRear();
		d[rear] = order;
	}

	public Order deQueue() {
		if (isEmpty()) {
			System.out.println("Queue is Empty!");
			return null;
		}
		Order temp = d[front];
		front = nextFront();
		return temp;
	}

	public Order deQueue(Order order) {
		if (isEmpty()) {
			System.out.println("Queue is Empty!");
			return null;
		}
		Order temp = d[front];
		front = nextFront();
		return temp;
	}

	public void printQueue() {
		if (isEmpty()) {
			System.out.println("Queue is Empty");
		}
		if (front <= rear) {
			for (int i = front; i <= rear; i++) {
				System.out.println(d[i] + " ");
			}
		} else {
			for (int i = front; i < capacity; i++) {
				System.out.println(d[i] + " ");
			}
			for (int i = 0; i <= rear; i++) {
				System.out.println(d[i] + " ");
			}

		}
	}

}
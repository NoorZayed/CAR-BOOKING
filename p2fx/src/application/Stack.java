package application;

import java.util.Arrays;

import java.util.NoSuchElementException;

public class Stack {
	Object[] stack;
	final static int size = 100;
	int top = -1;

	public Stack() {
		this(size);
	}

	public Stack(int size) {
		super();
		this.stack = new Object[size];
	}

	@Override
	public String toString() {
		return "Stack [stack=" + Arrays.toString(stack) + ", top=" + top + "]";
	}

	void push(Object data) {
		if (!isFull()) {
			stack[++top] = data;
		}
	}

	public Object peek() {
		if (!isEmpty()) {
			return stack[top];
		}
		return null;
	}

	public boolean isEmpty() {
		return top == -1;
	}

	public boolean isFull() {
		return top == size - 1;
	}

	Object pop() {
		if (isEmpty()) {
			throw new NoSuchElementException("Stack is empty");
		}
		Object temp = stack[top--];
		return temp;
	}
}

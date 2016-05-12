package ru.lagranj.save;

public class SaveException extends Exception {
	private static final long serialVersionUID = 1L;

	public SaveException() {
		super();
	}
	
	public SaveException(String message) {
		super(message);
	}
}

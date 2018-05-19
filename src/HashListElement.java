public class HashListElement {
	private String _message;
	private HashListElement _next;
	private int _counter;
	
	public HashListElement(String msg) {
		_message = msg;
		_counter = 1;
	}

	public void setNext(HashListElement nxt) {
		_next = nxt;
	}

	public void updateCounter() {
		_counter++;		
	}

	public int getCounter() {
		return _counter;
	}
	
	public String getMessage() {
		return _message;
	}

	public HashListElement getNext() {
		return _next;
	}
	
	
	
	

	
}

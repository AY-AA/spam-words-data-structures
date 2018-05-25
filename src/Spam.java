
public class Spam {

	private String _word;
	private int _percent;
	
	public Spam(String word, String perc) {
		_word = word;
		_percent = Integer.parseInt(perc);
	}
	
	// --- Getters --- //
	public String getWord()
	{
		return _word;
	}
	public int getPercent()
	{
		return _percent;
	}


}

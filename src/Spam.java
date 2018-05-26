
public class Spam {

	private String _word;
	private int _percent;
	
	public Spam(String word, String perc) {
		_word = word;
		_percent = Integer.parseInt(perc);
	}
	
	// --- Getters --- //
	/**
	 * gets the spam word
	 * @return
	 */
	public String getWord()
	{
		return _word;
	}
	/**
	 * gets the repeats percent allowed 
	 * @return
	 */
	public int getPercent()
	{
		return _percent;
	}


}

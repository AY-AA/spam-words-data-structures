

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Messages implements Iterable<Message>
{
	private Message[] _list;
	private int _size = 0;
	private String _fileName; 		// file name
	private FileReader _fileReader;
	private BufferedReader _bufferedReader;
	private HashTable[] _messagesHashTable;

	/**
	 * initializes the Messages list creation
	 * @param fileName
	 */
	public void generateMessages(String fileName)
	{
		_fileName = fileName;
		try {
			_fileReader = new FileReader(_fileName);
			_bufferedReader = new BufferedReader(_fileReader);
			getListSize();
			_list = new Message[_size];
			buildArray();
			_bufferedReader.close();
		} 
		catch(IOException ex) {
			System.out.println("error: unable to open file '" + _fileName + "'");
			_size = 0;
		}		
	}
	/**
	 * adds the Messages from the file to the list
	 */
	private void buildArray() {
		if (_list.length == 1)
		{
			System.out.println("no messages found in '" + _fileName + "'");
			_size = 0;
			return;
		}
		String currentMessage;
		int msgNumber = 0;
		try {

			while(msgNumber < _size && (currentMessage = _bufferedReader.readLine()) != null) 
			{
				String sender = currentMessage.substring(5);
				String receiver = _bufferedReader.readLine().substring(3);
				String message = _bufferedReader.readLine();
				while ((currentMessage = _bufferedReader.readLine()) != null && !currentMessage.equals("#"))
					message += '\n' + currentMessage;
				_list [msgNumber] = new Message (sender,receiver,message);
				msgNumber++;
			}   
			_bufferedReader.close();  
		}
		catch(IOException ex) {
			System.out.println("error: unable to read file '"  + _fileName + "'");   
		}
	}
	/**
	 * counts the times '#' appears in the txt file
	 * @return number '#' is written
	 */
	private void getListSize() throws IOException {
		String currentMessage;
		int counter = 0;
		try 
		{
			while((currentMessage = _bufferedReader.readLine()) != null) 
				if (currentMessage.equals("#"))
					counter++;
			counter++;
		}
		catch(IOException ex) 
		{
			System.out.println("error: unable to read file '"  + _fileName + "'");   
		}
		if (counter > 0)
			_size = counter;
		// we have popped all lines in the file, so we must reopen it
		_fileReader = new FileReader(_fileName);
		_bufferedReader = new BufferedReader(_fileReader);
	}
	/**
	 * creates a hash table based on the words in the message.
	 */
	public void createHashTables(int m)
	{
		_messagesHashTable = new HashTable[_size];
		int currTable = 0;
		Iterator<Message> mItr = iterator();
		while (mItr.hasNext())
		{
			_messagesHashTable[currTable] = new HashTable(m);
			Message curr = (Message) mItr.next();
			String[] splittedMsg = splitMessage(curr);
			for (int i=0; i<splittedMsg.length; i++)
				_messagesHashTable[currTable].insert(splittedMsg[i]);
			currTable ++;
		}	
	}
	/**
	 * splits a message into an array of strings which represents the message's word
	 * @param curr
	 * @return
	 */
	private String[] splitMessage(Message curr) {
		String msg = curr.getContent();
		String[] words = msg.split(" |\\W");
		return examineWords(words);
	}
	/**
	 * deletes the empty strings
	 * @param words
	 * @return
	 */
	private String[] examineWords(String[] words)
	{
		String temp="";
		for (int i=0; i< words.length; i++)
		{
			if (words[i].isEmpty() || words[i].equals(" "))
				continue;
			temp += words[i] + " ";
		}
		return temp.split(" ");
	}


	public String findSpams(String fileName,BTree bTree)
	{
		String[][] spams = readSpams(fileName); // up is the word, down is the reps number allowed
		String spamLines = "";
		Iterator<Message> mItr = iterator();
		int currMsg = 0;
		while (mItr.hasNext())
		{
			Message curr = (Message) mItr.next();
			String currSender = curr.getSender();
			String currReceiver = curr.getReceiver();
			if (!bTree.areFriends(currSender,currReceiver))
			{
				boolean isSpam = false;
				for (int i=0; i<spams.length && !isSpam ; i++)
					isSpam = checkSpam(currMsg,spams[i][0],spams[i][1]);
				if (isSpam)
					spamLines += " " + currMsg;
			}
			currMsg ++;			
		}
		return spamLines;
	}

	private boolean checkSpam(int currMsg,String spam, String number) {
		HashListElement currElem = _messagesHashTable[currMsg].search(spam);
		int numAllowed = Integer.parseInt(number);
		if (currElem != null)
		{
			return currElem.getCounter() > numAllowed;
		}
		return false;
	}

	
	
	
	// --- ITERATOR ---
	@Override
	public Iterator<Message> iterator() { return new MessageIterator(); }
	/**
	 * creates a new MessageIterator which iterates over the Messages interior array
	 */
	class MessageIterator implements Iterator<Message>
	{
		Message _current = null;
		int currentIndex;
		@Override
		public boolean hasNext() 
		{
			if (_size == 0 || !(currentIndex+1 < _size) || _list == null)
				return false;
			return true;
		}
		@Override
		public Message next() 
		{
			if (_current == null && _list != null)
			{
				_current = _list[0];
				currentIndex = 0;
				return _current;
			}
			else if (_current != null && currentIndex +1 < _size)
			{
				currentIndex ++;
				_current = _list[currentIndex];
				return _current;
			}
			throw new NoSuchElementException();
		}
	}


	/////////////// TESTS
	/**
	 *  prints all messages in the given order, including '#'
	 */
	public String toString()
	{
		if (_size == 0)
			return "error: no messages found";
		String ans = "";
		for (int i=0; i<_size;i++)
		{
			if (i != _size-1)
				ans += _list[i].toString() + '\n' + "#"  + '\n';
			else
				ans += _list[i].toString();
		}
		return ans;
	}

	public int getSize()
	{
		return _list.length;
	}

}

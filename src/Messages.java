import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Messages 
{
	private Message[] _list;
	private int size = 0;
	private String _fileName; 		// file name
	private FileReader _fileReader;
	private BufferedReader _bufferedReader;

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
			_list = new Message[size];
			buildArray();
			_bufferedReader.close();
		} 
		catch(IOException ex) {
			System.out.println("error: unable to open file '" + _fileName + "'");
		}		
	}
	/**
	 * adds the Messages to the list
	 */
	private void buildArray() {
		if (_list.length == 1)
		{
			System.out.println("no messages found in '" + _fileName + "'");
			return;
		}
		String currentMessage;
		int msgNumber = 0;
		try {

			currentMessage = _bufferedReader.readLine();
			while(msgNumber < size && currentMessage != null) 
			{
				String sender = _bufferedReader.readLine().substring(5);
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
		}
		catch(IOException ex) 
		{
			System.out.println("error: unable to read file '"  + _fileName + "'");   
		}
		if (counter > 0)
			size = counter;
		// we have popped all lines in the file, so we must reopen it
		_fileReader = new FileReader(_fileName);
		_bufferedReader = new BufferedReader(_fileReader);
	}

	
	/**
	 *  prints all messages in the given order, including '#'
	 */
	public String toString()
	{
		if (size == 0)
			return "error: no messages found";
		String ans = "";
		for (int i=0; i<size;i++)
		{
			if (i != size-1)
				ans += _list[i].toString() + '\n' + "#"  + '\n';
			else
				ans += _list[i].toString();
		}
		return ans;
	}
	public static void main(String args[])
	{
//		Messages msgs = new Messages();
		Messages messages = new Messages();
		messages.generateMessages(System.getProperty("user.dir")+"/messages.txt");
		String ans = messages.toString();
		System.out.println(ans);
	}

}

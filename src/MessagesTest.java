import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertEquals;

import java.util.Iterator;

import org.junit.Test;

public class MessagesTest {

	public static Messages msg1 = new Messages();
	

	public static void main(String[] args) {
		

	}
	public void test1()
	{
		msg1.generateMessages("messages.txt");
		Iterator m1 = msg1.iterator();
		
		int count = 0;

		while (m1.hasNext())
		{
			count++;
			System.out.println(m1.next());
			
		}
		int size = msg1.getSize();
		assertEquals(size,count);
	}
	@Test
	public void test2()
	{
		msg1.generateMessages("messages.txt");
		msg1.createHashTables("16");
		String spamMessages = msg1.findSpams("spam_words.txt");
		System.out.println(spamMessages);
		
		
		
	}

}

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MessagesTest {

	public static Messages msg1 = new Messages();

//	public void test1()
//	{
//		msg1.generateMessages("messages.txt");
//		Iterator m1 = msg1.iterator();
//		
//		int count = 0;
//
//		while (m1.hasNext())
//		{
//			count++;
//			System.out.println(m1.next());
//			
//		}
//		int size = msg1.getSize();
//		assertEquals(size,count);
//	}
	@SuppressWarnings("deprecation")
	@Test
	public void test2()
	{
		msg1.generateMessages("messages.txt");
		msg1.createHashTables("16");
		BTree btree = new BTree("2"); 
		btree.createFullTree("friends.txt");
		String spamMessages = msg1.findSpams("spam_words.txt", btree);
		System.out.println(btree.toString());
		
		System.out.println(spamMessages);
		String outp = "Lennon & McCartney#Bert & Ernie|Peanut_Butter & Jelly,Spongebob & Patrick#Batman & Robin|Chip & Dale,Han_Solo & Chewbacca^Miss_Piggy & Kermit|Scooby_Doo & Shaggy,Simon & Garfunkel|Tom & Jerry" + 
				'\n' + "4,6,10,12";
		System.out.println(outp);
		String ans =  btree.toString()+ '\n' + spamMessages ;
		assertEquals(outp,ans);
		
	}

}

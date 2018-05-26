import org.junit.Test;

public class BTreeTest {

	public static BTree bt = new BTree("2");

	@Test
	public void testBT()
	{
		bt.createFullTree("friends.txt");
		boolean b = bt.search("Lennon & McCartney");
	}
}

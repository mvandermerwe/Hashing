/**
 * 
 */
package hash_tables;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author markvandermerwe
 *
 */
public class Hash_Maps_Test {

	private Hash_Map<String, Integer> hashMap;

	@Before
	public void setUp() {
		// Change which is commented to test different implementations of
		// Hash_Map.
		hashMap = new Hash_Table_Linear_Probing<String, Integer>(24);
		// hashMap = new Hash_Table_Quadtratic_Probing<String, Integer>(24);
		// hashMap = new Hash_Table_Chaining<String, Integer>(24);
	}

	/**
	 * Test setting of capacity in constructor.
	 */
	@Test
	public void testGetCapacity() {
		assertEquals(Primes.next_prime(24), hashMap.capacity());
	}

	/**
	 * Make sure sizing returns correctly.
	 */
	@Test
	public void testGetSize() {
		assertEquals(0, hashMap.size());

		hashMap.insert("one", 1);

		assertEquals(1, hashMap.size());
	}

	/**
	 * Test adding values into the hash map.
	 */
	@Test
	public void testInsert() {
		hashMap.insert("one", 1);
		hashMap.insert("two", 6);
		hashMap.insert("three", 19);
		hashMap.insert("four", -2);
		hashMap.insert("five", 3);
		
		assertEquals(5, hashMap.size());
		assertEquals(19, (int) hashMap.find("three"));
		assertEquals(-2, (int) hashMap.find("two"));
	}

}

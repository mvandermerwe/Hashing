/**
 * 
 */
package hash_tables;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Test the functionality of all of our Hash_Map implementations.
 * 
 * @author Mark Van der Merwe and Andrew Haas
 */
public class Hash_Maps_Test {

	private Hash_Map<String, Integer> hashMap;

	@Before
	public void setUp() {
		// Change which is commented to test different implementations of
		// Hash_Map.
		//hashMap = new Hash_Table_Linear_Probing<String, Integer>(24);
<<<<<<< HEAD
		// hashMap = new Hash_Table_Quadtratic_Probing<String, Integer>(24);
=======
		//hashMap = new Hash_Table_Quadtratic_Probing<String, Integer>(24);
>>>>>>> branch 'master' of https://andhaas1@bitbucket.org/markvandermerwe/hashing.git
		hashMap = new Hash_Table_Chaining<String, Integer>(24);
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

		// Make sure five elements have been added.
		assertEquals(5, hashMap.size());
	}

	@Test
	public void testFind() {
		hashMap.insert("one", 1);
		hashMap.insert("two", 6);
		hashMap.insert("three", 19);
		hashMap.insert("four", -2);
		hashMap.insert("five", 3);

		// Test some generic finds.
		assertEquals(1, (int) hashMap.find("one"));
		assertEquals(-2, (int) hashMap.find("four"));
		assertEquals(3, (int) hashMap.find("five"));

		// Test finding something that isn't there.
		assertEquals(null, hashMap.find("nothere"));
	}

	@Test
	public void testClear() {
		hashMap.insert("one", 1);
		hashMap.insert("two", 6);
		hashMap.insert("three", 19);
		hashMap.insert("four", -2);
		hashMap.insert("five", 3);

		// Check size before clear.
		assertEquals(5, hashMap.size());

		hashMap.clear();

		// Check size to make sure we cleared values.
		assertEquals(0, hashMap.size());
	}

	@Test
	public void testResize() {
		hashMap.resize(Primes.next_prime(48));

		// resize then check capacity.
		assertEquals(Primes.next_prime(48), hashMap.capacity());
	}

	@Test
	public void testStartsResize() {
		for (int index = 0; index < 150; index++) {
			hashMap.insert(String.valueOf(index), index);
		}

		assertEquals(Primes.next_prime(58), hashMap.capacity());
	}
	
	@Test
	public void testToString() {
		for (int index = 0; index < 120; index++) {
			hashMap.insert(String.valueOf(index), index);
		}
		System.out.print(hashMap.toString());
		
	}

}

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
	private Hash_Map<String, Integer> oneMap;
	/**
	 * Sets up the Hash_Tables that are going to be used in the tests.
	 */
	@Before
	public void setUp() {
		// Change which is commented to test different implementations of
		// Hash_Map.
		//hashMap = new Hash_Table_Linear_Probing<String, Integer>(24);
		hashMap = new Hash_Table_Quadtratic_Probing<String, Integer>(24);
		// hashMap = new Hash_Table_Chaining<String, Integer>(24);

		//oneMap = new Hash_Table_Linear_Probing<String, Integer>(1);
		oneMap = new Hash_Table_Quadtratic_Probing<String, Integer>(1);
		// oneMap = new Hash_Table_Chaining<String, Integer>(1);
		oneMap.insert(String.valueOf(1), 1);
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

		assertEquals(1, oneMap.size());
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

		// Make sure only adds as many as spaces given full array.
		oneMap.set_resize_allowable(false);
		oneMap.clear();
		oneMap.resize(Primes.next_prime(8));
		for (int index = 0; index < 13; index++) {
			oneMap.insert(String.valueOf(index+1), index);
		}
		assertEquals(11, oneMap.size());
	}
	
	/**
	 * Tests the find method to make sure that it can find
	 * and return the correct value of the pair it is looking for.
	 */
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

		// System.out.println(hashMap.toString());

		assertEquals(1, (int) oneMap.find(String.valueOf(1)));
	}
	
	/**
	 * Tests the clear method to make sure it gets rid of all
	 * elements in the array.
	 */
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

		// Check size of one size, then clear and make sure.
		assertEquals(1, oneMap.size());
		oneMap.clear();
		assertEquals(0, oneMap.size());
	}

	/**
	 * Tests the resize method to make sure that it correctly
	 * resizes the array and then reinserts all of the 
	 * pairs correctly.
	 */
	@Test
	public void testResize() {
		hashMap.resize(Primes.next_prime(48));

		// resize then check capacity.
		assertEquals(Primes.next_prime(48), hashMap.capacity());
	}
	
	/**
	 * Tests to make sure that the Resize array is being called
	 * correctly when inserting a pair.
	 */
	@Test
	public void testStartsResize() {
		for (int index = 0; index < 150; index++) {
			hashMap.insert(String.valueOf(index), index);
		}

		if (hashMap instanceof Hash_Table_Chaining) {
			assertEquals(Primes.next_prime(58), hashMap.capacity());
		} else {
			assertEquals(Primes.next_prime(520), hashMap.capacity());
		}

	}

	/**
	 * Tests to make sure that the toString method correctly 
	 * outputs the required data.
	 */
	@Test
	public void testToString() {
		for (int index = 0; index < 120; index++) {
			hashMap.insert(String.valueOf(index), index);
		}
		System.out.print(hashMap.toString());

	}

}

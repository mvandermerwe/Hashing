package hash_tables;

import static org.junit.Assert.*;

import org.junit.Test;
/**
 * Test the functionality of all of our Prime implementations.
 * 
 * @author Mark Van der Merwe and Andrew Haas
 */
public class PrimesTest {
	
	/**
	 * Tests the is_prime method to make sure
	 * that it correctly checks if a value is
	 * prime or not.
	 */
	@Test
	public void testIsPrime() {
		assertTrue(Primes.is_prime(13));
		assertTrue(Primes.is_prime(5));
		assertFalse(Primes.is_prime(4));
		assertTrue(Primes.is_prime(0));
		assertFalse(Primes.is_prime(-4));
		
	}
	/**
	 * Tests to make sure that the next_prime
	 * method correctly returns the next prime 
	 * and tests negative numbers to make sure 
	 * they return -1.
	 */
	@Test
	public void testNextPrime() {
		assertEquals(5, Primes.next_prime(4));
		assertEquals(13, Primes.next_prime(12));
		assertEquals(7, Primes.next_prime(6));
		assertEquals(3, Primes.next_prime(3));
		assertEquals(1, Primes.next_prime(1));
		assertEquals(-1, Primes.next_prime(-5));
		
	}

}

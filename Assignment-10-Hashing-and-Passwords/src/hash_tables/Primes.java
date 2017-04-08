package hash_tables;

/**
 * @author germain
 *
 * some helper methods dealing with prime numbers.  These methods can be "brute force"
 */
public class Primes
{
	

	/**
	 * @param value
	 *            - to check if prime
	 * @return - return true if value is prime
	 */
	public static boolean is_prime( int value )
	{
		if(value<0){
			return false;
		}
		for(int i = 2; i < (int) Math.sqrt(value) + 1; i++){
			if(value%i==0){
				return false;
			}
		}
		return true;
	}

	/**
	 * next_prime
	 * 
	 * Note: static function
	 * 
	 * @param value
	 *            - the starting point to search for a prime
	 * @return - the value if prime, otherwise the next prime after value, if negative value provided returns -1
	 */
	public static int next_prime( int value )
	{
		
		
		for(int i = value; i< value*2; i++) { //Bertrand's postulate
			if(is_prime(i)){
				return i;
			}
		}	
		return -1;
	}


}

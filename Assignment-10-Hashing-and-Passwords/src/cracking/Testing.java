/**
 * 
 */
package cracking;

/**
 * Class to encapsulate all of our testing stuff.
 * 
 * @author Mark Van der Merwe and Andrew Haas.
 */
public class Testing {
	
	public static void main(String[] args) {
		Crack crack = new Crack();
		
		StringBuilder so_far = new StringBuilder();
		crack.brute_force_attack(null, null, so_far, 1, 4);
	}

}

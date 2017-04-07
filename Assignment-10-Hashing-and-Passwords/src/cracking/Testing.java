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
	
	public void main(String[] args) {
		Crack.multi_thread_brute_force_attack(3, Crack.read_file_into_hash_set("Resources/a_few_hashes"));
	}

}

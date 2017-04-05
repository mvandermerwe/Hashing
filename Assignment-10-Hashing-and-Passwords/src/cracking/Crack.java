/**
 * 
 */
package cracking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Class that does several attempts to crack password files.
 * 
 * @author Mark Van der Merwe and Andrew Haas.
 */
public class Crack {

	static public ArrayList<String> read_file_into_array(String file_name) {
		return null;
	}

	static public HashSet<String> read_file_into_hash_set(String file_name) {
		return null;
	}

	/**
	 * 
	 * This method compares (hashes of) all permutations of up to "Max_Length"
	 * characters vs the given list of hashes (the password file)
	 * 
	 * @param hashes
	 *            - hashes that you are seeing if you can find matches to
	 * @param max_length
	 *            - how many characters the passwords can be (in length)
	 * @return the list of found passwords and their corresponding md5 hashes
	 *         (e.g., [ "cat :
	 *         d077f244def8a70e5ea758bd8352fcd8AB3293292CEF2342ACD32342" ])
	 */
	static public ArrayList<String> brute_force_attack(Collection<String> hashes, int max_length) {
		return null;
	}

	/**
	 * Finds all words of the provided length by recursively adding every
	 * possible character onto the word.
	 * 
	 * @param hashes
	 *            - the collection of hashes to compare to to find password
	 *            cracks.
	 * @param successes
	 *            - arraylist of successes.
	 * @param so_far
	 *            - the word we are building so far to be tested.
	 * @param depth
	 *            - the position in the word we are working on.
	 * @param max_length
	 *            - the length of the word we are trying to crack.
	 */
	static public void brute_force_attack(Collection<String> hashes, ArrayList<String> successes, StringBuilder so_far,
			int depth, int max_length) {
		for (char letter = 'a'; letter <= 'z'; letter++) {
			// Add a letter at our current position.
			so_far.append(letter);
			// System.out.println(so_far);
			if (depth == max_length) {
				// Hash and test.

				// Delete last one so we can add the next letter.
				so_far.deleteCharAt(so_far.length() - 1);
			} else {
				// recurse towards depth of the word.
				brute_force_attack(hashes, successes, so_far, depth + 1, max_length);
				// Delete last one so we can add the next letter.
				so_far.deleteCharAt(so_far.length() - 1);
			}
		}
	}

	/**
	 * compare all words in the given list (dictionary) to the password
	 * collection we wish to crack
	 *
	 * @param dictionary
	 *            - The list of likely passwords
	 * @param hashes
	 *            - Collection of the hashwords we are trying to break
	 * @return the list of found passwords and their corresponding md5 hashes
	 *         (e.g., "cat :
	 *         d077f244def8a70e5ea758bd8352fcd8AB3293292CEF2342ACD32342")
	 */
	static public ArrayList<String> dictionary_attack(ArrayList<String> dictionary, Collection<String> hashes) {
		return null;
	}

	/**
	 * Begin a brute force attack on the password hashfile
	 * 
	 * Use up to 8 threads
	 * 
	 * compute all permutations of strings and compare them to a list of already
	 * hashed passwords to see if there is a match
	 * 
	 * @param max_permutation_length
	 *            - how long of passwords to attempt (suggest 6 or less)
	 * @return a list of successfully cracked passwords
	 */
	public static ArrayList<ArrayList<String>> multi_thread_brute_force_attack(int max_permutation_length,
			Collection<String> hashes) {
		long start_time = System.nanoTime();
		System.out.println("starting computation");

		ArrayList<Thread> threads = new ArrayList<>();

		int count = 0;
		int AVAILABLE_THREADS = 8;

		ExecutorService thread_pool = Executors.newFixedThreadPool(AVAILABLE_THREADS);
		ArrayList<ArrayList<String>> successes = new ArrayList<ArrayList<String>>();

		for (int i = 0; i < 26; i++) {
			successes.add(new ArrayList<>());
		}

		for (int i = 0; i < 26; i++) {
			int temp = i;

			thread_pool.execute(new Runnable() {

				@Override
				public void run() {
					System.out.println("working on permutation " + temp);
					brute_force_attack(hashes, successes.get(temp), new StringBuilder("" + (char) ('a' + temp + 1)), 1,
							max_permutation_length);
				}
			});
			;
		}

		try {
			thread_pool.shutdown();
			thread_pool.awaitTermination(1, TimeUnit.DAYS);
		} catch (Exception e) {
			e.printStackTrace();
		}

		long total_time = System.nanoTime() - start_time;
		System.out.println("done: ( " + (total_time / 1000000000.0) + " seconds )");

		return null;

	}

}

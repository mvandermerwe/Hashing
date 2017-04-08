/**
 * 
 */
package cracking;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import hash_tables.Hash_Table_Chaining;
import hash_tables.My_String;

/**
 * Class to encapsulate all of our testing stuff.
 * 
 * @author Mark Van der Merwe and Andrew Haas.
 */
public class Testing {

	public static void main(String[] args) {
		runTest(true);
	}

	public static void runTest(boolean resizable) {
		StringBuilder data = new StringBuilder();

		ArrayList<String> array = Crack.read_file_into_array("Resources/names");
		My_String[] names = new My_String[array.size()];
		// Change which is commented to test different implementations of

		for (int capacity = 10; capacity < 1000; capacity += 10) {
			long startTime = System.nanoTime();
			// Switch comments to run tests on different implementations.
			// Hash_Table_Linear_Probing<My_String, Integer> hashMap = new
			// Hash_Table_Linear_Probing<My_String, Integer>(capacity);
			// Hash_Table_Quadtratic_Probing<My_String, Integer> hashMap = new Hash_Table_Quadtratic_Probing<My_String, Integer>(
			//		capacity);
			Hash_Table_Chaining<My_String, Integer> hashMap = new Hash_Table_Chaining<>(capacity);
			hashMap.set_resize_allowable(resizable);

			Scanner scanner = null;

			for (int i = 1; i < array.size(); i++) {
				String line = array.get(i);

				scanner = new Scanner(line);

				My_String name = new My_String(scanner.next() + " " + scanner.next());
				names[i] = name;
				Integer info = scanner.nextInt();
				// System.out.println(name);
				hashMap.insert(name, info);
			}

			scanner.close();
			long endTime = System.nanoTime();
			System.out.println("Starting capacity: " + capacity);
			System.out.println("Time to add vals: " + (endTime - startTime));
			data.append(capacity + "," + (endTime - startTime) + "\n");
			System.out.println(hashMap.toString());

			hashMap.reset_stats();

			startTime = System.nanoTime();
			for (int index = 0; index < array.size(); index++) {
				if (names[index] != null) {
					hashMap.find(names[index]);
				}
			}

			endTime = System.nanoTime();
			System.out.println("Starting capacity: " + capacity);
			System.out.println("Time to add vals: " + (endTime - startTime));
			// data.append(capacity + "," + (endTime-startTime));
			System.out.println(hashMap.toString());
		}

		sendToFile(data, "Chaining.csv");
	}

	/**
	 * Helper method for writing our data to a CSV file.
	 * 
	 * @param fileData
	 *            - the data to be put into the file.
	 * @param filename
	 *            - the name of the file to write to.
	 */
	private static void sendToFile(StringBuilder fileData, String filename) {
		try {
			FileWriter csvWriter = new FileWriter(filename);
			csvWriter.write(fileData.toString());
			csvWriter.close();
		} catch (IOException e) {
			System.out.println("Unable to write to file. Here is the test data, though:");
			System.out.print(fileData.toString());
		}
	}
}

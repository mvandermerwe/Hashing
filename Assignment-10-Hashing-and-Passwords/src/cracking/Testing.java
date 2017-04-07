/**
 * 
 */
package cracking;

import java.util.ArrayList;

import hash_tables.Hash_Map;
import hash_tables.Hash_Table_Chaining;

/**
 * Class to encapsulate all of our testing stuff.
 * 
 * @author Mark Van der Merwe and Andrew Haas.
 */
public class Testing {
	
	public void main(String[] args) {
		ArrayList<String> array=Crack.read_file_into_array("Resources/a_few_hashes");
		// Change which is commented to test different implementations of
		Hash_Map<String, Integer> hashMap;
		//hashMap = new Hash_Table_Linear_Probing<String, Integer>(79);
		// hashMap = new Hash_Table_Quadtratic_Probing<String, Integer>(79);
		hashMap = new Hash_Table_Chaining<String, Integer>(79);
		hashMap.set_resize_allowable(false);
		for(int i = 0; i<79;i++){
			hashMap.insert(array.get(i), i);
		}
	}

}

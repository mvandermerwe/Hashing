/**
 * 
 */
package cracking;

import java.util.ArrayList;

import hash_tables.Hash_Map;
import hash_tables.Hash_Table_Chaining;
import hash_tables.Hash_Table_Linear_Probing;
import hash_tables.Hash_Table_Quadtratic_Probing;

/**
 * Class to encapsulate all of our testing stuff.
 * 
 * @author Mark Van der Merwe and Andrew Haas.
 */
public class Testing {
	
	public void main(String[] args) {
		ArrayList<String> array=Crack.read_file_into_array("Resources/names");
		// Change which is commented to test different implementations of
		Hash_Map<String, Integer> hashMap = new Hash_Table_Linear_Probing<String, Integer>(1000);
		Hash_Map<String, Integer> hashMap2= new Hash_Table_Quadtratic_Probing<String, Integer>(1000);
		Hash_Map<String, Integer>	hashMap3 = new Hash_Table_Chaining<String, Integer>(1000);
		hashMap.set_resize_allowable(false);
		for(int i = 0; i<array.size();i++){
			hashMap.insert(array.get(i)+array.get(i+1), Integer.parseInt(array.get(i+2)));
			hashMap2.insert(array.get(i)+array.get(i+1), Integer.parseInt(array.get(i+2)));
		}
	}

}

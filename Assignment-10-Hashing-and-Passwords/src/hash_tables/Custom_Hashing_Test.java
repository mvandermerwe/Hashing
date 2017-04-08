package hash_tables;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class Custom_Hashing_Test {
	
	private Hash_Map<My_String, Integer> hashMap;
	
	@Test
	public void testHashFunction() {
		My_String word = new My_String("a;lkjd");
		System.out.println(word.hashCode());
	}
	
	@Before
	public void testHashMap() {
		hashMap = new Hash_Table_Quadtratic_Probing<My_String, Integer>(24);
	}
	
	@Test
	public void testHashFunctionInMap() {
		hashMap.insert(new My_String("wordsman"), 4);
		hashMap.insert(new My_String("theyre"), 4);
		hashMap.insert(new My_String("amazing"), 4);
		hashMap.insert(new My_String("were"), 4);
		hashMap.insert(new My_String("perhaps"), 4);
		hashMap.insert(new My_String("trains"), 4);
		hashMap.insert(new My_String("blinders"), 4);
		
		System.out.println(hashMap.toString());
	}

}

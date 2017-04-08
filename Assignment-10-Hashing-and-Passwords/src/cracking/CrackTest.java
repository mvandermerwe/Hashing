package cracking;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Test;
/**
 * Test the functionality of all of our Crack implementations.
 * 
 * @author Mark Van der Merwe and Andrew Haas
 */
public class CrackTest {

	/**
	 * Tests the read_file_into_array method to make sure
	 * that it is reading the file correctly.
	 */
	@Test
	public void testArrayReadFile() {
		System.out.println(Crack.read_file_into_array("Resources/a_few_words").toString());
		assertEquals("[a, cat, and, dog, went, for, ride, on, green, van]", Crack.read_file_into_array("Resources/a_few_words").toString());
		System.out.println(Crack.read_file_into_array("Resources/a_few_hashes").toString());
		assertEquals("[0cc175b9c0f1b6a831c399e269772661, d077f244def8a70e5ea758bd8352fcd8, be5d5d37542d75f93a87094459f76678, 06d80eb0c50b49a509b49f2424e8c805, dd22b70914cd2243e055d2e118741186, d55669822f1a8cf72ec1911e462a54eb, 059763450f095b4973b450eaf58399c1, ed2b5c0139cec8ad2873829dc1117d50, 9f27410725ab8cc8854a2769c7a516b8, 957d2fa52c19a5aff4ccf5d9a959adab]", Crack.read_file_into_array("Resources/a_few_hashes").toString());
	}
	
	/**
	 * Tests the read_file_into_hash_set method to make sure
	 * that it is reading the file correctly.
	 */
	@Test
	public void testHashSetReadFile() {
		System.out.println(Crack.read_file_into_hash_set("Resources/a_few_words").toString());
		assertEquals("[a, van, went, green, and, cat, for, dog, ride, on]", Crack.read_file_into_hash_set("Resources/a_few_words").toString());
		System.out.println(Crack.read_file_into_hash_set("Resources/a_few_hashes").toString());
		assertEquals("[9f27410725ab8cc8854a2769c7a516b8, 06d80eb0c50b49a509b49f2424e8c805, dd22b70914cd2243e055d2e118741186, 059763450f095b4973b450eaf58399c1, 957d2fa52c19a5aff4ccf5d9a959adab, d55669822f1a8cf72ec1911e462a54eb, 0cc175b9c0f1b6a831c399e269772661, d077f244def8a70e5ea758bd8352fcd8, ed2b5c0139cec8ad2873829dc1117d50, be5d5d37542d75f93a87094459f76678]", Crack.read_file_into_hash_set("Resources/a_few_hashes").toString());
	}
	/**
	 * Tests the brute_force_attack method to make
	 * sure that it can solve all of the given hashes and
	 * returns the correct Array of strings.
	 */
	@Test
	public void testBruteForceAttack() {
		HashSet<String> array = Crack.read_file_into_hash_set("Resources/a_few_hashes");
		ArrayList<String> cracked = Crack.brute_force_attack(array, 5);
		System.out.println(cracked.toString());
		// Outcomes checked w/ reverse MD5 tools.
		assertEquals("[[ and : be5d5d37542d75f93a87094459f76678 ], [ a : 0cc175b9c0f1b6a831c399e269772661 ], [ cat : d077f244def8a70e5ea758bd8352fcd8 ], [ dog : 06d80eb0c50b49a509b49f2424e8c805 ], [ for : d55669822f1a8cf72ec1911e462a54eb ], [ green : 9f27410725ab8cc8854a2769c7a516b8 ], [ on : ed2b5c0139cec8ad2873829dc1117d50 ], [ ride : 059763450f095b4973b450eaf58399c1 ], [ van : 957d2fa52c19a5aff4ccf5d9a959adab ], [ went : dd22b70914cd2243e055d2e118741186 ]]",cracked.toString());
	}
	/**
	 * Tests the hash method to make sure that it
	 * correctly hashes the string.
	 */
	@Test
	public void testHash() {
		assertEquals("06d80eb0c50b49a509b49f2424e8c805",Crack.hash("dog").toString());
		assertEquals("d077f244def8a70e5ea758bd8352fcd8",Crack.hash("cat").toString());
		assertEquals("d41d8cd98f00b204e9800998ecf8427e",Crack.hash("").toString());
	}
	/**
	 * Tests the dictionary_attack method to make sure that it can 
	 * test each string in the dictionary array of strings and
	 * compares the hashes to see if any of the given strings are
	 * any of the passwords.
	 */
	@Test
	public void testDictionary(){
		//Crack cocaine = new Crack();// NM
		
		ArrayList<String> hashes = Crack.read_file_into_array("Resources/a_few_hashes");
		ArrayList<String> dictionary = Crack.read_file_into_array("Resources/a_few_words");
		ArrayList<String> cracked = Crack.dictionary_attack(dictionary, hashes);
		System.out.println(cracked.toString());
		// Outcomes checked w/ reverse MD5 tools.
		assertEquals("[[ a : 0cc175b9c0f1b6a831c399e269772661 ], [ cat : d077f244def8a70e5ea758bd8352fcd8 ], [ and : be5d5d37542d75f93a87094459f76678 ], [ dog : 06d80eb0c50b49a509b49f2424e8c805 ], [ went : dd22b70914cd2243e055d2e118741186 ], [ for : d55669822f1a8cf72ec1911e462a54eb ], [ ride : 059763450f095b4973b450eaf58399c1 ], [ on : ed2b5c0139cec8ad2873829dc1117d50 ], [ green : 9f27410725ab8cc8854a2769c7a516b8 ], [ van : 957d2fa52c19a5aff4ccf5d9a959adab ]]", cracked.toString());
	}
	
	/**
	 * Tests the multi_thread_brute_force_attack method to make
	 * sure that it can solve all of the given hashes and
	 * returns the correct Array of strings.
	 */
	@Test
	public void testMultiThreadBruteForceAttack() {
		ArrayList<String> array = Crack.read_file_into_array("Resources/a_few_hashes");
		ArrayList<ArrayList<String>> cracked = Crack.multi_thread_brute_force_attack(5, array);
		String string = "";
		for(ArrayList<String> array2 :cracked){
			string += array2.toString();
		System.out.print(array2.toString());
		}
		// Outcomes checked w/ reverse MD5 tools.
		assertEquals("[[ a : 0cc175b9c0f1b6a831c399e269772661 ], [ and : be5d5d37542d75f93a87094459f76678 ]][][[ cat : d077f244def8a70e5ea758bd8352fcd8 ]][[ dog : 06d80eb0c50b49a509b49f2424e8c805 ]][][[ for : d55669822f1a8cf72ec1911e462a54eb ]][[ green : 9f27410725ab8cc8854a2769c7a516b8 ]][][][][][][][][[ on : ed2b5c0139cec8ad2873829dc1117d50 ]][][][[ ride : 059763450f095b4973b450eaf58399c1 ]][][][][[ van : 957d2fa52c19a5aff4ccf5d9a959adab ]][[ went : dd22b70914cd2243e055d2e118741186 ]][][][]",string);
		
	}

}

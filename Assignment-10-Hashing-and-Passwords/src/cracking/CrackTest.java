package cracking;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class CrackTest {

	@Test
	public void testArrayReadFile() {
		System.out.println(Crack.read_file_into_array("Resources/a_few_words").toString());
		assertEquals("[a, cat, and, dog, went, for, ride, on, green, van]", Crack.read_file_into_array("Resources/a_few_words").toString());
		System.out.println(Crack.read_file_into_array("Resources/a_few_hashes").toString());
		assertEquals("[0cc175b9c0f1b6a831c399e269772661, d077f244def8a70e5ea758bd8352fcd8, be5d5d37542d75f93a87094459f76678, 06d80eb0c50b49a509b49f2424e8c805, dd22b70914cd2243e055d2e118741186, d55669822f1a8cf72ec1911e462a54eb, 059763450f095b4973b450eaf58399c1, ed2b5c0139cec8ad2873829dc1117d50, 9f27410725ab8cc8854a2769c7a516b8, 957d2fa52c19a5aff4ccf5d9a959adab]", Crack.read_file_into_array("Resources/a_few_hashes").toString());
	}
	
	@Test
	public void testHashSetReadFile() {
		System.out.println(Crack.read_file_into_hash_set("Resources/a_few_words").toString());
		assertEquals("[a, van, went, green, and, cat, for, dog, ride, on]", Crack.read_file_into_hash_set("Resources/a_few_words").toString());
		System.out.println(Crack.read_file_into_hash_set("Resources/a_few_hashes").toString());
		assertEquals("[9f27410725ab8cc8854a2769c7a516b8, 06d80eb0c50b49a509b49f2424e8c805, dd22b70914cd2243e055d2e118741186, 059763450f095b4973b450eaf58399c1, 957d2fa52c19a5aff4ccf5d9a959adab, d55669822f1a8cf72ec1911e462a54eb, 0cc175b9c0f1b6a831c399e269772661, d077f244def8a70e5ea758bd8352fcd8, ed2b5c0139cec8ad2873829dc1117d50, be5d5d37542d75f93a87094459f76678]", Crack.read_file_into_hash_set("Resources/a_few_hashes").toString());
	}
	
//	@Test
//	public void testBruteForceAttack() {
//		ArrayList<String> array = crack.read_file_into_array("Resources/a_few_hashes");
//		ArrayList<String> cracked = crack.brute_force_attack(array, 5);
//		System.out.println(cracked.toString());
//		assertEquals("[[ and : be5d5d37542d75f93a87094459f76678 ], [ a : 0cc175b9c0f1b6a831c399e269772661 ], [ cat : d077f244def8a70e5ea758bd8352fcd8 ], [ dog : 06d80eb0c50b49a509b49f2424e8c805 ], [ for : d55669822f1a8cf72ec1911e462a54eb ], [ green : 9f27410725ab8cc8854a2769c7a516b8 ], [ on : ed2b5c0139cec8ad2873829dc1117d50 ], [ ride : 059763450f095b4973b450eaf58399c1 ], [ van : 957d2fa52c19a5aff4ccf5d9a959adab ], [ went : dd22b70914cd2243e055d2e118741186 ]]",cracked.toString());
//	}
	
	@Test
	public void testHash() {
		
		assertEquals("06d80eb0c50b49a509b49f2424e8c805",Crack.hash("dog").toString());
		assertEquals("d077f244def8a70e5ea758bd8352fcd8",Crack.hash("cat").toString());
		assertEquals("d41d8cd98f00b204e9800998ecf8427e",Crack.hash("").toString());
	}

}

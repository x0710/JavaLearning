public class TestLettersEncryption
{
	public static void main(String[] args)
	{
		String v = "65";
		char[] val1 = v.toCharArray();
		int key = 10;
		
		char[] val2 = LettersEncryption.encryptionA(val1, key);
		char[] val3 = LettersEncryption.decryptionA(val2, key);
		
		System.out.println("------------------------------------------------------");
		for(char c : val2) {System.out.print(c);}
		System.out.println();
		System.out.println("------------------------------------------------------");
		for(char c : val3) {System.out.print(c);}
	}
}
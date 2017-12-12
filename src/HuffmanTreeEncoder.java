import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Scanner;

public class HuffmanTreeEncoder {
	// Generates code_table.txt and encoded.bin
	static void encode(HuffmanTree root) throws IOException 
	{
		//Get code in HashMap
		HashMap<String, String> codes = encodeRecursive(root, "", new HashMap<String, String>());
		
		//Write codes to file
        java.io.File file1 = new java.io.File("code_table.txt");
        java.io.PrintWriter output1 = new java.io.PrintWriter(file1);
		for(String s : codes.keySet())
		{
			output1.print(s + " ");
            output1.println(codes.get(s));
		}
		output1.close();
		
		//Generate binary
		String content = new Scanner(new File("../ADSProjectFinal/Project/sample2/sample_input_large.txt")).useDelimiter("\\Z").next();
		String lines[] = content.split("\\r?\\n");
		
		
		System.out.println("Here");
		String encoded=encodeMessage(codes,lines);
		//System.out.println(encoded);
		
		
		//Write binary to file
		BitSet bitSet = new BitSet(encoded.length());
		int bitcounter = 0;
		for(Character c : encoded.toCharArray()) {
		    if(c.equals('1')) {
		        bitSet.set(bitcounter);
		    }
		    bitcounter++;
		}
		System.out.println("XXXXXX:");
		System.out.println(bitSet.size());
		FileOutputStream fos = new FileOutputStream("encoded.bin");
		fos.write(bitSet.toByteArray());
		fos.close();
		
		System.out.println("Encoding Done!!!!!!!");
		}

	
    private static String encodeMessage(HashMap<String, String> charCode, String sentence[]) {
        final StringBuilder stringBuilder = new StringBuilder();
        //System.out.println("Sentence:" +sentence.toString());
        for (int i = 0; i < sentence.length; i++) {
            stringBuilder.append(charCode.get(sentence[i]));
        }
        return stringBuilder.toString();
    }
    
    private static String decodeMessage(HuffmanTree node,BitSet set) throws FileNotFoundException, IOException,ClassNotFoundException  {
            final BitSet bitSet = set;
            final StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < (bitSet.length() - 1);) {
                HuffmanTree temp = node;
                // since huffman code generates full binary tree, temp.right is certainly null if temp.left is null.
                while (temp.left != null) {
                    if (!bitSet.get(i)) {
                        temp = temp.left;
                    } else {
                        temp = temp.right;
                    }
                    i = i + 1;
               }
                stringBuilder.append(temp.key);
            }
            
            return stringBuilder.toString();
        }
    
    
	
	
	private static HashMap<String, String> encodeRecursive(HuffmanTree node, String code, HashMap<String, String> encoding) 
	{
		if (node.isLeaf())
			encoding.put(node.key, code);
		else {
			if (node.left != null)
				encodeRecursive(node.left, code + "0", encoding);
			if (node.right != null)
				encodeRecursive(node.right, code + "1", encoding);
		}
		return encoding;
	}
}

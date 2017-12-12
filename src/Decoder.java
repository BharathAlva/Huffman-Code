import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Decoder {
	
	private static String readBits(String args) throws IOException {
		BufferedInputStream b = new BufferedInputStream(new FileInputStream(args));
		int r = -1;
		StringBuffer input = new StringBuffer("");
		while ((r = b.read()) != -1) {
			int m = 1;
			while (m <= 8) {
				input.append((r >> (8 - m)) & 0x1);
				m++;
			}
		}
		b.close();
		return input.toString();
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new FileReader("../ADSProjectFinal/Project/sample2/code_table.txt"));
		HashMap<String, String> map = new HashMap<>();
		String sentence;
		Node root = new Node();
		while ((sentence = br.readLine()) != null){
			String[] temp = sentence.split(" ");
			String value = temp[0];
			String code = temp[1];
			map.put(code, value);
			
			Node x = root;

			for (int i = 0; i < code.length(); i++) {
				char c = code.charAt(i);
				if (c == '0') {
					if (x.left == null) {
						x.left = new Node();
					}
					x = x.left;
				} else if (c == '1') {
					if (x.right == null) {
						x.right = new Node();
					}
					x = x.right;
				}
			}
			x.value = Integer.parseInt(value);
		}
		br.close();
		
		
	
		
		System.out.println("Code table reading done!!!!");
		
		String encoded_text = readBits("../ADSProjectFinal/Project/sample2/encoded.bin");
		
		System.out.println("Reading encoded.bin done!!!!");
		
		ArrayList<Integer> final_output = new ArrayList<Integer>();
		
		Node y = root;
		for(int i=0; i<encoded_text.length(); i++){
			char c = encoded_text.charAt(i);
			//System.out.println(c);
			//System.out.println(x.value);
			if(c == '1'){
				y = y.right;
			}else{
				y = y.left;
			}
			if(y.value != Integer.MIN_VALUE){
				final_output.add(y.value);
				y = root;
			}
		}	
		
		/*while(encoded_text.length() > 0){
			//System.out.println(encoded_text);
			//System.out.println(encoded_text.length());
			Iterator it = map.entrySet().iterator();
			while(it.hasNext()){
				String split = null;
				Map.Entry pair = (Map.Entry)it.next();
				String st = pair.getKey().toString();
				//System.out.println(st);
				if(st.length() > encoded_text.length()){
					split = encoded_text.substring(0, encoded_text.length());
				}else{
					split = encoded_text.substring(0, st.length());
				}
				//System.out.println(split);
				if(split.equalsIgnoreCase(st)){
					final_output.add(pair.getValue().toString());
					encoded_text.delete(0, split.length());
					break;
				}
			}
		}*/

		PrintWriter writer = new PrintWriter("decoded.txt", "UTF-8");
		for(int i=0; i<final_output.size(); i++){
			writer.println(final_output.get(i));
		}
		writer.close();
	}
	
	static class Node {
		int value = Integer.MIN_VALUE;
		Node left;
		Node right;
	}

}

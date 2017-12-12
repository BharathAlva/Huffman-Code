import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Huffman 
{
public static void main(String args[]) throws IOException
{
	
	/*
	String content = new Scanner(new File("C:\\Users\\bhara_000\\Desktop\\sampleinputsmall.txt")).useDelimiter("\\Z").next();
	String lines[] = content.split("\\r?\\n");
	final HashMap<String, Integer> map = new HashMap<String, Integer>();

    for (int i = 0; i < lines.length; i++) {
    	String ch = lines[i];
        if (!map.containsKey(ch)) {
            map.put(ch, 1);
        } else {
            int val = map.get(ch);
            map.put(ch, ++val);
        }
    }	
	*/
	
	
	
	HashMap<String,Integer> map=new HashMap<String,Integer>();
    BufferedReader br=new BufferedReader(new FileReader("../ADSProjectFinal/Project/sample2/sample_input_large.txt"));
    String line=null;
    while((line=br.readLine())!=null)
    {
    	line = line.trim();
    	if(!line.equals("")){
    		if(map.containsKey(line)){
                map.put(line,map.get(line)+1);
            }
            else{
                map.put(line,1);
            }
    	}
    }
    br.close();

    

    HuffmanTree left_child;
    HuffmanTree right_child;
    HuffmanTree result;

    
    long startTime = System.currentTimeMillis();
    //Heap<HuffmanNode> heap = new PairingHeap<HuffmanNode>();
    BinaryHeapTemp<HuffmanNode> heap = new BinaryHeapTemp<HuffmanNode>();
    
    for(String c: map.keySet())
    {
        if(c!=null){
        	//System.out.println("XXXX:" +c + ":" +map.get(c));
        	heap.add(new HuffmanNode( new HuffmanTree(c, map.get(c)) ));
        }
            
    }

    while(heap.size() > 1)
    {
    	//System.out.println(">>>>>>>>>>>>>>>>" + heap.size());
    	left_child = heap.remove().root;
        left_child.Printoror();
        right_child = heap.remove().root;
        right_child.Printoror();
        result = new HuffmanTree("--", left_child.frequency + right_child.frequency);
        result.left = left_child;
        result.right = right_child;
        heap.add(new HuffmanNode(result));
    }

    HuffmanTree theTree = heap.remove().root;
    theTree.Printoror();
    long stopTime = System.currentTimeMillis();
    System.out.println("Elapsed time was " + (stopTime - startTime) + " miliseconds.");
    HuffmanTreeEncoder.encode(theTree);
    System.out.println("Encoding done");
}
}
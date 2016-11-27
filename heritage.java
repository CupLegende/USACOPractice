
import java.io.*;
import java.math.BigInteger;
import java.util.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeSet;

/*
 ID: ywt99111
 LANG: JAVA
 PROG: heritage										
*/
//Very interesting problem, use the information of hierarchy and order to get the whole tree and use recursion to printout
//many data structures to optimize 
//consider the order in the first sequence for two consecutive nodes in the second sequence

class binaryTree {
    binaryTree mom;
    char value;
    binaryTree right;
    binaryTree left;

    public binaryTree(binaryTree mother, char current) {
	value = current;
	mom = mother;
    }

    public binaryTree(char current) {
	mom = null;
	value = current;
    }

}

public class heritage {
    public static String printTree(binaryTree i) {
	String out = "";
	if (i.left != null) {
	    out += printTree(i.left);
	}
	if (i.right != null) {
	    out += printTree(i.right);
	}
	return out + i.value;
    }

    public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new FileReader(new File("heritage.in")));
	PrintWriter pw = new PrintWriter(new File("heritage.out"));
	// BufferedReader br = new BufferedReader(new
	// InputStreamReader(System.in));

	// PrintWriter pw = new PrintWriter(System.out, true);

	String order = br.readLine();
	String hierachy = br.readLine();
	char[] firstSet = order.toCharArray();
	char[] secondSet = hierachy.toCharArray();
	Map<Character, Integer> dictionary = new HashMap<Character, Integer>();
	for (int v = 0; v < firstSet.length; v++) {
	    dictionary.put(firstSet[v], v);
	}
	binaryTree[] tree = new binaryTree[firstSet.length];
	// boolean []check = new boolean[firstSet.length];
	binaryTree root = new binaryTree(secondSet[0]);
	tree[dictionary.get(secondSet[0])] = root;
	// check [dictionary.get(secondSet[0])]=true;
	for (int k = 1; k < firstSet.length; k++) {
	    char now = secondSet[k];
	    binaryTree upper = tree[dictionary.get(secondSet[k - 1])];

	    // boolean changed =false;
	    if (dictionary.get(now) > dictionary.get(upper.value)) {
		int index = dictionary.get(now) - 1;
		while (tree[index] == null) {

		    index--;
		}
		upper = tree[index];
		binaryTree newNode = new binaryTree(upper, now);
		upper.right = newNode;
		tree[dictionary.get(now)] = newNode;
	    } else {
		int index = dictionary.get(now) + 1;
		while (tree[index] == null) {
		    index++;
		}
		upper = tree[index];
		binaryTree newNode = new binaryTree(upper, now);
		upper.left = newNode;
		tree[dictionary.get(now)] = newNode;
	    }

	}

	pw.println(printTree(root));
	br.close();
	pw.close();
    }

}


import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeSet;
/*
ID: ywt99111
LANG: JAVA
PROG: range										
*/
public class range {
public static void main(String[]args)throws Exception{
    BufferedReader br = new BufferedReader(new FileReader(new File("range.in")));
	PrintWriter pw = new PrintWriter(new File("range.out"));
	// BufferedReader br = new BufferedReader(new
	 //InputStreamReader(System.in));

	 //PrintWriter pw = new PrintWriter(System.out, true);
	int size = Integer.parseInt(br.readLine());
	int[][] map = new int[size][size];
	for(int c=0;c<size;c++){
	    String current = br.readLine();
	    for(int k=0;k<size;k++){
		map[c][k]=current.charAt(k)-48;
		//System.out.println(map[c][k]);
	    }
	}
	
	for(int c=size-1;c>=0;c--){
	    for(int k=size-1;k>=0;k--){
		
		if(!(c==size-1 || k==size-1) && map[c][k]==1){
		    map[c][k] = Math.min(Math.min(map[c][k+1], map[c+1][k]),map[c+1][k+1])+1;
		    
		}
	    }
	}
	int [] result = new int[size+1];
	for(int c=0;c<size;c++){
	    for(int k=0;k<size;k++){
		for(int n=2;n<=map[c][k];n++){
		    result[n]++;
		 //   System.out.println(map[c][k]);
		}
	    }
	}
	for(int g=0;g<result.length;g++){
	    if(result[g]!=0){
		pw.println(g+" "+result[g]);
	    }
	}
	
	pw.close();
	br.close();
}
}

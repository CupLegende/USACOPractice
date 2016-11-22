
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
 PROG: game1										
*/
//Basically DP idea + BFS + brute force. Initially want to use recursion, but stack may overflow...
public class game1 {
    static int [] board;
    static int [][] score;

public static void main(String[]args)throws Exception{
    BufferedReader br = new BufferedReader(new FileReader(new File("game1.in")));
  	PrintWriter pw = new PrintWriter(new File("game1.out"));
  	// BufferedReader br = new BufferedReader(new
  	 //InputStreamReader(System.in));

  	 //PrintWriter pw = new PrintWriter(System.out, true);
int size = Integer.parseInt(br.readLine());
ArrayList<Integer>bfBegin = new ArrayList<Integer>();
boolean [][]inbf = new boolean [ size][size];
ArrayList<Integer>bfEnd = new ArrayList<Integer>();
board = new int [size];
score = new int [size][size];
String receptor = "";
int count =0;
while((receptor = br.readLine())!=null){
    StringTokenizer tok = new StringTokenizer (receptor);
    while(tok.hasMoreTokens()){
	board [count]= Integer.parseInt(tok.nextToken());
	count++;
    }
}
inbf[0][size-1]=true;
bfBegin.add(0);
bfEnd.add(size-1);
for(int c=0;c<score.length;c++){
    for(int k=0;k<score[0].length;k++){
	if(c<=k){
	    if(c==k){
		score[c][k]=board[c];
	    }
	    else if (k-c==1){
		score[c][k]=Math.max(board[c], board[k]);
	    }
	}
    }
}
while(bfBegin.size()!=0){
    int first = bfBegin.remove(0);
    int second = bfEnd.remove(0);
    inbf[first][second]=false;
    if(second-first>1){
   	if(score[first+1][second]!=0 && score[first][second-1]!=0 && score[first+2][second]!=0 && score[first][second-2]!=0 && score[first+1][second-1]!=0){
	    if(score[first+1][second]>score[first][second-1]){
		score[first][second]=Math.min(score[first+1][second-1], score[first][second-2])+board[second];
	    }
	    else{
		score[first][second]=Math.min(score[first+1][second-1], score[first+2][second])+board[first];
	    }
	}
   	else{
   	    if(score[first+1][second]==0 && !inbf[first+1][second]){
   		inbf[first+1][second]=true;
   		bfBegin.add(first+1);
   		bfEnd.add(second);
   	    }
   	 if(score[first][second-1]==0 && !inbf[first][second-1]){
		inbf[first][second-1]=true;
		bfBegin.add(first);
		bfEnd.add(second-1);
	    }
   	if(score[first+2][second]==0 && !inbf[first+2][second]){
		inbf[first+2][second]=true;
		bfBegin.add(first+2);
		bfEnd.add(second);
	    }
   	if(score[first][second-2]==0 && !inbf[first][second-2]){
		inbf[first][second-2]=true;
		bfBegin.add(first);
		bfEnd.add(second-2);
	    }
   	if(score[first+1][second-1]==0 && !inbf[first+1][second-1]){
		inbf[first+1][second-1]=true;
		bfBegin.add(first+1);
		bfEnd.add(second-1);
	    }
   	inbf[first][second]=true;
   	bfBegin.add(first);
   	bfEnd.add(second);
   	}
	    
	    
	    
    
    
    }
}

//score[0][size-1]= dp(0,size-1);  	
pw.println(score[0][size-1]+" "+Math.min(score[1][size-1], score[0][size-2]));

  	br.close();
pw.close();
}
}


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
 PROG: camelot										
*/

public class camelot {
    static int kingR = 0;
    static int kingC = 0;
    static int row = 0;
    static int col = 0;
    static int[][][] map;
    static int[][] finalmap;
    static int[][][][] aroundKing;

    public static void BFS(int[][] mp, ArrayList<Integer> xQueue, ArrayList<Integer> yQueue) {
	int c = xQueue.remove(0);
	int r = yQueue.remove(0);
	int nextVal = mp[r][c] + 1;
	int[] xchange = { -2, -1, 1, 2, 2, 1, -1, -2 };
	int[] ychange = { 1, 2, 2, 1, -1, -2, -2, -1 };

	for (int v = 0; v < xchange.length; v++) {
	    int cNext = c + xchange[v];
	    int rNext = r + ychange[v];
	    if (cNext >= 0 && cNext < col && rNext >= 0 && rNext < row) {
		if (nextVal < mp[rNext][cNext]) {
		    mp[rNext][cNext] = nextVal;
		    xQueue.add(cNext);
		    yQueue.add(rNext);
		}
	    }
	}
    }

    // for any given point in the graph, treat it as a knight and expand the mp
    // with BFS
    public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new FileReader(new File("camelot.in")));
	PrintWriter pw = new PrintWriter(new File("camelot.out"));
	// BufferedReader br = new BufferedReader(new
	// InputStreamReader(System.in));

	// PrintWriter pw = new PrintWriter(System.out, true);
	StringTokenizer tok = new StringTokenizer(br.readLine());
	row = Integer.parseInt(tok.nextToken());
	col = Integer.parseInt(tok.nextToken());
	finalmap = new int[row][col];
	aroundKing = new int[5][5][row][col];
	// 5 is arbitrarily defined after some tests
	tok = new StringTokenizer(br.readLine());
	char temp = tok.nextToken().charAt(0);
	kingC = (int) temp - 65;
	kingR = Integer.parseInt(tok.nextToken()) - 1;
	ArrayList<Integer> knightR = new ArrayList<Integer>();
	ArrayList<Integer> knightC = new ArrayList<Integer>();
	String trail = "";

	// while(!(trail=br.readLine()).equals(""))
	while ((trail = br.readLine()) != null) {
	    // System.out.println(kingC);
	    tok = new StringTokenizer(trail);
	    while (tok.hasMoreTokens()) {

		knightC.add((int) tok.nextToken().charAt(0) - 65);
		knightR.add(Integer.parseInt(tok.nextToken()) - 1);
	    }
	}
	int numKnight = knightC.size();
	if (numKnight == 0) {
	    pw.println(0);
	} else {// handle extreme case
	    map = new int[numKnight][row][col];
	    for (int v = 0; v < map.length; v++) {
		for (int vv = 0; vv < map[0].length; vv++) {
		    Arrays.fill(map[v][vv], Integer.MAX_VALUE);
		}
	    }
	    for (int v = 0; v < map.length; v++) {
		ArrayList<Integer> xQ = new ArrayList<Integer>();
		ArrayList<Integer> yQ = new ArrayList<Integer>();
		map[v][knightR.get(v)][knightC.get(v)] = 0;
		xQ.add(knightC.get(v));
		yQ.add(knightR.get(v));
		while (!xQ.isEmpty()) {
		    BFS(map[v], xQ, yQ);
		}
//BFS the map for every knight
	    }
	    for (int r = 0; r < map[0].length; r++) {
		for (int c = 0; c < map[0][0].length; c++) {
		    for (int k = 0; k < map.length; k++) {
			if (map[k][r][c] == Integer.MAX_VALUE) {
			    finalmap[r][c] = Integer.MAX_VALUE;
			    break;
			} else {
			    // System.out.println(r+" "+c);
			    // System.out.println(finalmap[r][c]);
			    finalmap[r][c] += map[k][r][c];
			}
		    }
		}
	    }
	    //compile the result to get finalmap
	    for (int m = 0; m < aroundKing.length; m++) {
		for (int n = 0; n < aroundKing[0].length; n++) {
		    for (int c = 0; c < aroundKing[0][0].length; c++) {
			Arrays.fill(aroundKing[m][n][c], Integer.MAX_VALUE);
		    }
		}
	    }
	    for (int k = 0; k < aroundKing.length; k++) {
		for (int i = 0; i < aroundKing[0].length; i++) {
		    ArrayList<Integer> xQ = new ArrayList<Integer>();
		    ArrayList<Integer> yQ = new ArrayList<Integer>();
		    int aroundY = kingR + k - 2;
		    int aroundX = kingC + i - 2;
		    if (aroundY >= 0 && aroundY < row && aroundX >= 0 && aroundX < col) {
			aroundKing[k][i][aroundY][aroundX] = Math.max(Math.abs(aroundY - kingR),
				Math.abs(aroundX - kingC));
			// precalculate how king is gonna get there
			xQ.add(aroundX);
			yQ.add(aroundY);
			while (xQ.size() != 0) {
			    BFS(aroundKing[k][i], xQ, yQ);

			}
		    } // for every point around king, treat it as a knight and BFS
		}
	    }
	    int minOutput = Integer.MAX_VALUE;
	    for (int i = 0; i < row; i++) {
		for (int k = 0; k < col; k++) {
		    int current = 0;
		    for (int x = 0; x < map.length; x++) {
			if (map[x][i][k] != Integer.MAX_VALUE) {
			    current += map[x][i][k];
			}

		    }
		    if (current <= minOutput) {
			int minRider = 0;
			// int minRiderIndex = 0;
			for (int xx = 0; xx < map.length; xx++) {
			    for (int y = 0; y < aroundKing.length; y++) {
				int aroundY = kingR + y - 2;
				for (int z = 0; z < aroundKing[0].length; z++) {
				    int aroundX = kingC + z - 2;
				    if (aroundY >= 0 && aroundY < row && aroundX >= 0 && aroundX < col)
				    // this line is used to prevent the king from being in the edge
				    {// System.out.println(aroundKing[y][z][i][k]);

					if (aroundKing[y][z][i][k] != Integer.MAX_VALUE
						&& map[xx][aroundY][aroundX] != Integer.MAX_VALUE
						&& (minRider = current - map[xx][i][k] + map[xx][aroundY][aroundX]
							+ aroundKing[y][z][i][k]) < minOutput) {
					    // System.out.println(aroundKing[y][z][i][k]);
					    minOutput = minRider;
					}
				    }
				}
			    }
			}
		    }
		}
	    }//loop around every point and then every knight(king picker) and then every point around king 
//for every point in the graph, its result = shorest path for every knight(except one) + path for one of them go to pick the king
	    pw.println(minOutput);
	}
	br.close();
	pw.close();
    }
}

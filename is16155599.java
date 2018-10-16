import java.io.*;
import java.util.*;

public class is16155599
{
	private boolean adjMatrix[][];
	private int numVertices;
	public void Graph(int numVertices) {
          this.numVertices = numVertices;
          adjMatrix = new boolean[numVertices][numVertices];
    }
	public void addEdge(int i, int j) {
                adjMatrix[i][j] = true;
                adjMatrix[j][i] = true;
    }
	public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < numVertices; i++) {
           // s.append(i + ": ");
            for (boolean j : adjMatrix[i]) {
               // s.append((j?1:0) + " ");
            }
            s.append("\n");
        }
        return s.toString();
    }
	public static void main (String [] args) throws IOException
	{
		int matrixSize = sizeOfMatrix("GA2018-19.txt");
		Graph g = new Graph(matrixSize);
		int [][] edges;
		File in = new File("GA2018-19.txt");
			Scanner reader = new Scanner(in);
			int count = 0;
			while(reader.hasNextLine()){
				String cursor = reader.nextLine();
				count += cursor.split(" ").length - 1;
			}
			reader.close();
			
			edges = new int[count][];
			//System.out.println(edges.length);
			int i = 0;
			reader = new Scanner(in);
			while(reader.hasNextLine()){
				String cursor = reader.nextLine();
				String [] split = cursor.split(" ");
				for(int j=1;j<split.length;j++)
				{
					edges[i + j - 1] = new int[2];
					edges[i + j - 1][0] = Integer.parseInt(split[0]);
                    edges[i + j - 1][1] = Integer.parseInt(split[j]);
				 
				}
				i += split.length - 1;
				
			}
			reader.close();
				for(int k=0;k< edges.length;k++)
				{
					for(int n=1;n<edges[k].length;n++)
					{
						g.addEdge(edges[k][0],edges[0][n]);
				
					}
				}
		 System.out.print(g.toString());
	}
	
	
	public static int sizeOfMatrix(String filename) throws IOException
	{
		File file = new File(filename);
		Scanner reader = new Scanner(file);
		int size = 0;
		while(reader.hasNextLine()){
			String cursor = reader.nextLine();
			String [] split = cursor.split(" ");
			for(int i = 0;i<split.length;i++)
			{
				int temp = Integer.parseInt(split[i]);
				if(temp>size)
				{
					size = temp + 1;
				}
			}	
		}
		reader.close();
		return size;
	}
}

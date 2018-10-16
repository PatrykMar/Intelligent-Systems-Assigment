import java.io.*;
import java.util.*;

public class is16155599
{
	private boolean adjMatrix[][];
	private int numVertices;
	public  is16155599(int numVertices) {
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
            for (boolean j : adjMatrix[i]) {
               s.append((j?1:0) + " ");
            }
            s.append("\n");
        }
        return s.toString();
    }
	public static void main (String [] args) throws IOException
	{
		File file = new File("GA2018-19.txt");
		Scanner reader = new Scanner(file);
		int size = 0;
		int count= 0;
		while(reader.hasNextLine()){
			String cursor = reader.nextLine();
			String [] split = cursor.split(" ");
			count += split.length -1;
			for(int i = 0;i<split.length;i++)
			{
				int temp = Integer.parseInt(split[i]);
				if(temp>size)
				{
					size = temp + 1;
				}
			}
		}
		int matrixSize = size;
		is16155599 g = new is16155599(matrixSize);
		int [][] edges;
			edges = new int[count][];
			int i = 0;
			reader = new Scanner(file);
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
				for(int k=0;k<edges.length;k++)
				{
					for(int n=1;n<edges[k].length;n++)
					{
						g.addEdge(edges[k][n-1],edges[k][n]);
					}
				}
		System.out.print(g.toString());
		
		Scanner user = new Scanner(System.in);
		System.out.println("Press the Enter key to continue:");
		String input = user.nextLine();
		if (input.equals(""))
		{
			ArrayList <Integer> order = new ArrayList <Integer>();
			for (int l = 0; l < matrixSize; l++)
			{
				int temp = (int) (Math.random() * (matrixSize ));
				while (order.contains(temp))
				{
					temp = (int) (Math.random() * (matrixSize));
				}
				
				order.add(temp);
			}
			
			for (int p = 0; p < order.size(); p++)
			{
				System.out.print(order.get(p) + " ");
			}
		}
	}
}

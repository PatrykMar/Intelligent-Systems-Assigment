import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.math.*;

public class is16155599
{
	private static int [][] adjMatrix;

	private static double temperature,coolRate,fitness;
	private static double chunk;
	private static final double cool = 5.0;

	private static JTextField tempField;
	private static JTextField coolField;
	private static JPanel mainPanel = new JPanel();

	private static ArrayList <Integer> order;
	private static ArrayList <Double> evo = new ArrayList<Double>();

	public static void main (String [] args) throws IOException
	{
		run();
	}
	public static void run() throws IOException
	{
		JFrame mainFrame = new JFrame();
		mainFrame.setTitle("Simulated Annealing-CS4006_Project");

		JPanel tempPanel = new JPanel();
		JLabel tempLabel = new JLabel("Temperature: ");
		tempField= new JTextField("",10);
		tempPanel.add(tempLabel);
		tempPanel.add(tempField);

		JPanel coolPanel = new JPanel();
		JLabel coolLabel = new JLabel("Cooling Rate: ");
		coolField = new JTextField("",10);
		coolPanel.add(coolLabel);
		coolPanel.add(coolField);

		mainPanel.add(tempPanel);
		mainPanel.add(coolPanel);


		int res = JOptionPane.showOptionDialog(null, mainPanel, "SA", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
		if(res==0){
			try{
				temperature = Double.parseDouble(tempField.getText());
				coolRate = Double.parseDouble(coolField.getText());
				String fileName = "GA2018-19.txt";
				File file = new File(fileName);

				int matrixSize = 0;

				Scanner reader = new Scanner(file);

				while(reader.hasNextLine())
				{
					String cursor = reader.nextLine();
					String split[] = cursor.split(" ");
					for(int i =0;i<split.length;i++)
					{
						int temp = Integer.parseInt(split[i]);
						if(temp>matrixSize)
						{
							matrixSize = temp + 1;
						}
					}
						
				}
				adjMatrix = new int [matrixSize][matrixSize];

				reader = new Scanner(file);
				while(reader.hasNextLine()){
				String cursor = reader.nextLine();
				String [] split = cursor.split(" ");
					if(split.length == 2)
					{
						int number1 = Integer.parseInt(split[0]);
						int number2 = Integer.parseInt(split[1]);
						adjMatrix[number1][number2]=1;
						adjMatrix[number2][number1]=1;

					}
				}
				order = new ArrayList<Integer>();
				for(int i = 0;i < matrixSize ; i++)
				{
					int temp = (int)(Math.random() * (matrixSize ));
					while(order.contains(temp))
					{
						temp = (int) (Math.random() * (matrixSize ));
					}
					order.add(temp);
				}

				double newFitness;
				newFitness = calculateFitness(order);

				while(temperature>0)
				{
					ArrayList<Integer>tempOrder = order;
					double tempFitness;
					for(int i = 0;i<temperature;i++)
					{
						tempOrder = mutate(tempOrder);
						tempFitness = calculateFitness(tempOrder);
						if (tempFitness < newFitness)
						{
							newFitness = tempFitness;
							order = tempOrder;
						}
					}
					temperature -= coolRate;
					evo.add(newFitness);
				}	
			
			printToFile(evo);
			show(adjMatrix,order,chunk);	
		}catch(NumberFormatException ex)
			{
				System.err.println("NUMBERS ONLY");
			}
			catch(IOException ex)
			{
				System.err.println("Error IO exception");
			}
	}
}

	public static double calculateFitness(ArrayList <Integer>order)
	{
		double points[][] = new double[order.size()][2];
		fitness = 0.0;
		chunk = (2*Math.PI)/Math.abs(order.size());

		for(int i=0;i<order.size();i++)
		{
			double x= Math.cos(i*chunk);
			double y= Math.sin(i*chunk);
			points[i][0] = x;
			points[i][1] = y;
		}

		for(int i=0;i<order.size();i++)
		{
			for(int j = i+1;j<order.size();j++)
			{
				if(adjMatrix[order.get(i)][order.get(j)] == 1)
				{
					double x1 = points[i][0];
					double y1 = points[i][1];
					double x2 = points[j][0];
					double y2 = points[j][1];
					
					double edgeLength = Math.sqrt(Math.pow((y2 - y1), 2) + Math.pow((x2 - x1), 2));
					fitness += edgeLength;
				}
			}
		}
		return fitness;
	}

	public static ArrayList<Integer> mutate(ArrayList<Integer> temp)
	{
		int fIndex = (int) (Math.random() * temp.size());
		int sIndex = (int) (Math.random() * temp.size());
		
		while (fIndex == sIndex)
		{
			fIndex = (int) (Math.random() * temp.size());
			sIndex = (int) (Math.random() * temp.size());
		}
	
		int temp1 = temp.get(fIndex);
		int temp2 = temp.get(sIndex);
		temp.set(fIndex,temp2);
		temp.set(sIndex,temp1);
		
		return temp;
	}

	public static void show(int[][] adjMatrix, ArrayList <Integer> order, double chunk){
		new Visualisation(adjMatrix,order,chunk);
	}

	public static void printToFile(ArrayList<Double> evo)
	{
		try{
			File file2 = new File("out.txt");
			PrintWriter write = new PrintWriter(file2);
			for(int i=0;i<evo.size();i++)
			{
				System.out.println(evo.get(i));
				write.println(evo.get(i));
			}
			write.close();
		}catch(IOException e){
			e.printStackTrace();
			System.err.println("Can't create out file");
		}
	}
}

class Visualisation extends JFrame
{
	private int[][] adjMatrix;
	private ArrayList <Integer> current_order;
	private int v = 0;
	private double chunk;

	public Visualisation(int[][] adjMatrix, ArrayList <Integer> current_order, double chunk)
	{
		this.adjMatrix = adjMatrix;
		this.current_order = current_order;
		this.chunk = chunk;
		this.v = current_order.size();
		this.setSize(700,700);
		this.setTitle("Visual");
		setVisible(true);
	}

	public void paint(Graphics g)
	{
		super.paint(g);
		int radius = 100;
		int mov = 200;
		
		for (int i = 0; i < v; i++)
		{
			for (int j = i + 1; j < v; j++)
			{
				if(adjMatrix[current_order.get(i)][current_order.get(j)] == 1)
				{
					g.drawLine(
						(int)(((double) Math.cos(i * chunk)) * radius + mov),
						(int)(((double) Math.sin(i * chunk)) * radius + mov),
						(int)(((double) Math.cos(j * chunk)) * radius + mov),
						(int)(((double) Math.sin(j * chunk)) * radius + mov));
				}
			}
		}
	}
}
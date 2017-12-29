import java.io.*;
import java.util.*;
 
public class NeuralNetworks {

	/**********************************/
	//training record calss
	private class Record
	{
		private double[] input;
		private double[] output;
		
		//Constructor of recrord
		private Record(double[] input, double[] output)
		{
			this.input = input;
			this.output = output;
		}
	}
	/*************************************/
	private int numberRecords;
	private int numberInputs;
	private int numberOutputs;
	
	private int numberMiddle;
	private int numberIterations;
	private double rate;
	
	private ArrayList<Record> records;
	
	private double[] input;
	private double[] middle;
	private double[] output;
	
	private double[] errorMiddle;
	private double[] errorOut;
	
	private double[] thetaMiddle;
	private double[] thetaOut;
	
	private double[][] matrixMiddle;
	private double[][] matrixOut;
	
	/***********************************************************/
	
	//constructor of neural network
	public NeuralNetworks()
	{
		//parameters are zero
		numberRecords = 0;
		numberInputs = 0;
		numberOutputs = 0;
		numberMiddle = 0;
		numberIterations = 0;
		rate = 0;
		
		//arrays are empty
		records = null;
		input = null;
		middle = null;
		output = null;
		errorMiddle = null;
		errorOut = null;
		thetaMiddle = null;
		thetaOut = null;
		matrixMiddle = null;
		matrixOut = null;
		
	}
	
 	public void loadTraningData(String traningFile) throws IOException
	{
 		Scanner inFile = new Scanner(new File(traningFile));

 		numberInputs = 4;
 		numberOutputs = 1;
 		numberRecords = 80;
 		
 		//empty list of records
		records = new ArrayList<Record>();
 
  		//for each training record
		for(int i=0; i<numberRecords; i++)
		{
			//read inputs
			double[] input = new double[numberInputs];
 			for(int j=0; j<numberInputs; j++)
			{
  				input[j]  = inFile.nextDouble();
   			}

			double[] output = new double[numberOutputs];
			
			for(int j=0; j<numberOutputs; j++)
			{
				output[j] = inFile.nextDouble();
 			}
 
			//create training record  
			Record record = new Record(input, output);
			
 			//add record to list
			records.add(record);
  
		}

  		inFile.close();
  		
 
  	}
 	
 
 
	
	/**********************************************/
	
	//method sets parameters of neural network
	public void setParameters(int numberMiddle, int numberIterations, int seed, double rate)
	{
 
		
 		//set hidden noes, iterations, rate
		this.numberMiddle = numberMiddle;
		this.numberIterations = numberIterations;
		this.rate = rate;
		
 		//intialize random number generations
		Random rand = new Random(seed);

  		//create input/output = arrays
		input = new double[numberInputs];
		middle = new double[numberMiddle];
		output = new double[numberOutputs];
		
		//create error arrays
		errorMiddle = new double[numberMiddle];
		errorOut = new double[numberOutputs];
 
		
		//intialize thetas at hidden nodes
		thetaMiddle = new double[numberMiddle];
		
		for(int i=0; i<numberMiddle; i++)
		{
			thetaMiddle[i] = 2*rand.nextDouble() -1;
		}
 		
		//initalize theta as output nodes
		thetaOut = new double[numberOutputs];
		for(int i=0; i<numberOutputs; i++)
		{
			thetaOut[i] = 2*rand.nextDouble() -1; 

		}
		
		//intialize weights between input/hidden nodes
		matrixMiddle = new double[numberInputs][numberMiddle];
		for(int i=0; i<numberInputs; i++)
		{
			for(int j=0; j<numberMiddle; j++)
			{
				matrixMiddle[i][j] = 2*rand.nextDouble() - 1;
 			}
		}
		
		//intialize weights between hidden.output nodes
		matrixOut = new double[numberMiddle][numberOutputs];
		for(int i=0; i<numberMiddle; i++)
		{
			for(int j=0; j<numberOutputs; j++)
			{
				matrixOut[i][j] = 2*rand.nextDouble() - 1;
 			}
		}
		
	}
	
	/**************************************************************/
	
	//method trains neural network
	public void train()
	{
		//repeat iterations number of times
		for(int i=0; i<numberIterations; i++)
		{
			//for each training record
			for(int j=0; j<numberRecords; j++)
			{
				//calculate input/output
				forwardCalculation(records.get(j).input);
				
 				
				//compute errors, update wieghts/thetas
				backwardCalculation(records.get(j).output);
			}
 		}
	}
	
	/**************************************************************/
	
	//method performs forward pass - computes input/output
	private void forwardCalculation(double[] trainingInput)
	{
  		//feed inputs of record
		for(int i=0; i<numberInputs; i++)
		{
			input[i] = trainingInput[i]; 
 		}
 
		//for each hidden node
		for(int i=0; i<numberMiddle; i++)
		{
			double sum = 0;
			
			//omute input at hidden node
			for(int j=0; j<numberInputs; j++)
			{
				sum = sum + input[j]*matrixMiddle[j][i];
 			}
			
			//add theta
			sum = sum + thetaMiddle[i];
 			
			//compute output at hidden nodes
			middle[i] = 1/(1+ Math.exp(-sum));

		}
		
		//for each input node
		for(int i=0; i<numberOutputs; i++)
		{
			double sum = 0;
			
 			//compute input at output node
			for(int j=0; j<numberMiddle; j++)
			{
				sum = sum + middle[j]*matrixOut[j][i];
 			}
			
			//add theta
			sum += thetaOut[i];
			
			//compute output at output node
			output[i] = 1/(1+Math.exp(-sum));
 
 
		}
 		
	}
	
	/****************************************************/
	//method performs backward pass - computes errors, updates weights/thetas
	private void backwardCalculation(double[] trainingOutput)
	{
		//compute error at each output node
		for(int i=0; i<numberOutputs; i++)
		{
			errorOut[i] = output[i]*(1-output[i]) *(trainingOutput[i]-output[i]);
		}
		
		//compute error at each hidden node
		for(int i=0; i<numberMiddle; i++)
		{
			double sum = 0;
			
			for(int j=0; j<numberOutputs; j++)
			{
				sum = sum + matrixOut[i][j]*errorOut[j];
			}
			
			errorMiddle[i] = middle[i]*(1-middle[i])*sum;
			
 		}
		
		//update weights between input/hidden nodes
		for(int i=0; i<numberMiddle; i++)
		{
			for(int j=0; j<numberOutputs; j++)
			{
				matrixOut[i][j] += rate*middle[i]*errorOut[j];
			}
		}
		
		//updatew weihts between input/hidden nodes
		for(int i=0; i<numberInputs; i++)
		{
			for(int j=0; j<numberMiddle; j++)
			{
				matrixMiddle[i][j] += rate*input[i]*errorMiddle[j]; 
			}
		}
		
		//update thetas at output nodes
		for(int i=0; i<numberOutputs; i++)
		{
			thetaOut[i] += rate*errorOut[i];
		}
		
		
		//update thetas at hidden nodes
		for(int i=0; i<numberMiddle; i++)
		{
			thetaMiddle[i] += rate*errorMiddle[i];
		}
	}
	
	/***************************************************/
	//method computes output of an input
	private double[] test(double[] input)
	{
		//forward pass input
		forwardCalculation(input);
		
		//return output produced
		return output;
		
	}
	
	/**************************************************/
	
	//method reads inputs from input file and writes outputs to output ile
	public void testData(String inputFile, String outputFile) throws IOException
	{
 
		Scanner inFile = new Scanner(new File(inputFile));
 		PrintWriter outFile = new PrintWriter(new FileWriter(outputFile));
   		
 		//for each record
		for(int i=0 ; i<8; i++)
		{
 			double[] input = new double[numberInputs];
 
 			//read input from file
			for(int j=0; j<numberInputs; j++)
			{
				input[j] = inFile.nextDouble();
 			}
			//System.out.println();
 
			//find output using nerual network
			double[] output = test(input);
			
			
			
  			//write output to output file
			for(int j=0; j<numberOutputs; j++)
			{
				//high is 1, medium is .66, low is .33 and 0 is undertemined 
				//outFile.print(rescaleData(output[j]));
				
				double x = rescaleData(output[j]);
				
				
				if(x > 0.75)
				{
					outFile.print("high");

				}
				else if(x<0.75 && x> 0.50 )
				{
					outFile.print("medium");
 				}
				else if(x < 0.50 &&  x>0.15)
				{
					outFile.print("low");
 				}
				else if(x< 0.15)
				{
					outFile.print("undetermined");
				}
				
  			}
			outFile.println();
 
 		}
		
		
		inFile.close();
		outFile.close();
		
		
	}
	
	public double rescaleData(double output2) throws IOException
	{
		NeuralNetworkTester sim = new NeuralNetworkTester();
		
		double maxCreditScore = NeuralNetworkTester.findMaxCreditScoreTestData();
		//System.out.println("max credit = " + maxCreditScore);
		
		double minCreditScore = NeuralNetworkTester.findMinCreditScoreTestData(); 
		
		double maxIncome = NeuralNetworkTester.findMaxIncomeTestData();
		
		double minIncome = NeuralNetworkTester.findMinIncomeTestData();
		
		double minAge = NeuralNetworkTester.findMinAgeTestData();

		double maxAge = NeuralNetworkTester.findMaxAgeTestData();
 		
 		double x = (output2 -0)/(1-0);
		
		
		return x;
 	}
	
	
	/*******************************************************/
	
	//method validates the network using the data fom a file
	public void validate(String validationFile) throws IOException
	{
		Scanner inFile = new Scanner(new File (validationFile));
		
		int numberRecords = inFile.nextInt();
		
		//for each record
		for(int i =0; i<numberRecords; i++)
		{
			//read inputs
			double [] input = new double[numberInputs];
			
			for(int j=0; j<numberInputs; j++)
			{
				input[j] = inFile.nextDouble();
			}
			
			//read actual outputs
			double actualOutput[] = new double[numberOutputs];
			for(int j=0; j<numberOutputs; j++)
			{
				actualOutput[j] = inFile.nextDouble(); 
			}
			
			//find predicted output
			double[] predictedOutput = test(input);
			
			//write actual and predicted outputs
			for(int j=0; j<numberOutputs; j++)
			{
				System.out.println(actualOutput[j] + " " + predictedOutput[j]);
			}
		}
		
		inFile.close();
	}
 
	
	/*********************************************/
	
	 private double computeError(double[] actualOutput, double[] predictedOutput)
	 {
		 double error = 0;
		 
		 // sum of squares of errors
		 for(int i=0; i<actualOutput.length; i++)
		 {
			 error += Math.pow(actualOutput[i] - predictedOutput[i], 2);
		 }
		 
		 //root mean squares error
		 return Math.sqrt(error/actualOutput.length);
	 }
	 

	
}


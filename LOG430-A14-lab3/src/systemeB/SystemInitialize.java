package systemeB;

import java.io.PipedWriter;

/**
 * This class contains the main method for assignment 3. The program consists of
 * these files:<br>
 * <br>
 * 
 * 1) SystemInitialize: instantiates all filters and pipes, starts all filters.<br>
 * 2) FileReaderFilter: reads input file and sends each line to its output pipe.<br>
 * 3) StatusFilter: separates the input stream in two project types (REG, CRI)
 * and writes lines to the appropriate output pipe.<br>
 * 4) StateFilter: determines if an entry contains a particular state specified
 * at instantiation. If so, sends the whole line to its output pipe.<br>
 * 5) MergeFilter: accepts inputs from 2 input pipes and writes them to its
 * output pipe.<br>
 * 6) FileWriterFilter: sends its input stream to a text file.<br>
 * <br>
 * 
 * Pseudo Code:
 * 
 * <pre>
 * instantiate all filters and pipes
 * start FileReaderFilter
 * start StatusFilter
 * start StateFilter for RIS
 * start StateFilter for DIF
 * start MergeFilter
 * start FileWriterFilter
 * </pre>
 * 
 * Running the program:
 * 
 * <pre>
 * java SystemInitialize InputFile OutputFile > DebugFile
 * 
 * SystemInitialize - Program name
 * InputFile - Text input file (see comments below)
 * OutputFile - Text output file with students
 * DebugFile - Optional file to direct debug statements
 * </pre>
 * 
 * @author A.J. Lattanze
 */

public class SystemInitialize
{

	public static void main(String argv[])
	{
		// Let's make sure that input and output files are provided on the
		// command line

		if (argv.length != 2)
		{

			/*
			 * System.out.println("\n\nNombre incorrect de parametres d'entree. Utilisation:");
			 * System.out.println("\njava SystemInitialize <fichier d'entree> <fichier de sortie>");
			 */
			argv = new String[2];
			argv[0] = "datainB.txt";
			argv[1] = "resultats.txt";
		}

		// These are the declarations for the pipes.
		PipedWriter pipe01 = new PipedWriter();
		PipedWriter pipe02 = new PipedWriter();
		PipedWriter pipe03 = new PipedWriter();
		PipedWriter pipe04 = new PipedWriter();
		PipedWriter pipe05 = new PipedWriter();
		//PipedWriter pipe06 = new PipedWriter();

		// Instantiate Filter Threads
		Thread fileReaderFilter = new FileReaderFilter(argv[0], pipe01);
		Thread statusFilter = new StatusFilter(pipe01, pipe02, pipe03);
		Thread stateFilter1 = new StateFilter("1", pipe02, pipe04);
		Thread stateFilter2 = new StateFilter("2", pipe03, pipe05);
		Thread fileWriterFilter1 = new FileWriterFilter(argv[1]+"_REG", pipe04);
		Thread fileWriterFilter2 = new FileWriterFilter(argv[1]+"_CRI", pipe05);

		// Start the threads
		fileReaderFilter.start();
		statusFilter.start();
		stateFilter1.start();
		stateFilter2.start();
		fileWriterFilter1.start();
		fileWriterFilter2.start();

	} // main

} // SystemInitialize
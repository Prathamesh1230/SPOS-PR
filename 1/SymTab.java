import java.io.*;

class SymTab {
	public static void main(String args[]) throws Exception {
		// Check if input file is provided
		if (args.length == 0) {
			System.out.println("Error: No input file provided.");
			System.out.println("Usage: java SymTab <input_file>");
			return;
		}

		FileReader FP = new FileReader(args[0]);
		BufferedReader bufferedReader = new BufferedReader(FP);

		String line;
		int line_count = 0, LC = 0, symTabLine = 0, opTabLine = 0, litTabLine = 0, poolTabLine = 0;

		// Data Structures
		final int MAX = 100;
		String SymbolTab[][] = new String[MAX][3];
		String OpTab[][] = new String[MAX][3];
		String LitTab[][] = new String[MAX][2];
		int PoolTab[] = new int[MAX];

		System.out.println("___________________________________________________");

		while ((line = bufferedReader.readLine()) != null) {
			String[] tokens = line.split("\t");

			if (line_count == 0 && tokens.length > 1 && tokens[1].equalsIgnoreCase("START")) {
				// If START is found on the first line, set LC to the specified address
				if (tokens.length > 2) {
					LC = Integer.parseInt(tokens[2]);
				} else {
					System.out.println("Warning: START directive has no address; defaulting LC to 0.");
					LC = 0;
				}
			} else if (line_count == 0) {
				System.out.println("No START directive found on the first line; starting LC at 0.");
				LC = 0;
			}

			for (String token : tokens) // Print input program
				System.out.print(token + "\t");
			System.out.println("");

			// Symbol table entry for labels
			if (!tokens[0].equals("")) {
				SymbolTab[symTabLine][0] = tokens[0];
				SymbolTab[symTabLine][1] = Integer.toString(LC);
				SymbolTab[symTabLine][2] = Integer.toString(1);
				symTabLine++;
			}

			// Declarative statements (DS or DC)
			if (tokens.length > 1 && (tokens[1].equalsIgnoreCase("DS") || tokens[1].equalsIgnoreCase("DC"))) {
				SymbolTab[symTabLine][0] = tokens[0];
				SymbolTab[symTabLine][1] = Integer.toString(LC);
				SymbolTab[symTabLine][2] = Integer.toString(1);
				symTabLine++;
			}

			// Literal table entry
			if (tokens.length == 3 && tokens[2].startsWith("=")) {
				LitTab[litTabLine][0] = tokens[2];
				LitTab[litTabLine][1] = Integer.toString(LC);
				litTabLine++;
			}

			// Opcode table entry
			if (tokens.length > 1 && tokens[1] != null) {
				OpTab[opTabLine][0] = tokens[1];

				if (tokens[1].equalsIgnoreCase("START") || tokens[1].equalsIgnoreCase("END") ||
						tokens[1].equalsIgnoreCase("ORIGIN") || tokens[1].equalsIgnoreCase("EQU") ||
						tokens[1].equalsIgnoreCase("LTORG")) {
					OpTab[opTabLine][1] = "AD";
					OpTab[opTabLine][2] = "R11";
				} else if (tokens[1].equalsIgnoreCase("DS") || tokens[1].equalsIgnoreCase("DC")) {
					OpTab[opTabLine][1] = "DL";
					OpTab[opTabLine][2] = "R7";
				} else {
					OpTab[opTabLine][1] = "IS";
					OpTab[opTabLine][2] = "(04,1)";
				}
				opTabLine++;
			}

			line_count++;
			LC++;
		}

		System.out.println("___________________________________________________");

		// Print Symbol Table
		System.out.println("\n\n\tSYMBOL TABLE\t\t");
		System.out.println("--------------------------");
		System.out.println("SYMBOL\tADDRESS\tLENGTH");
		System.out.println("--------------------------");
		for (int i = 0; i < symTabLine; i++)
			System.out.println(SymbolTab[i][0] + "\t" + SymbolTab[i][1] + "\t" + SymbolTab[i][2]);
		System.out.println("--------------------------");

		// Print Opcode Table
		System.out.println("\n\n\tOPCODE TABLE\t\t");
		System.out.println("----------------------------");
		System.out.println("MNEMONIC\tCLASS\tINFO");
		System.out.println("----------------------------");
		for (int i = 0; i < opTabLine; i++)
			System.out.println(OpTab[i][0] + "\t\t" + OpTab[i][1] + "\t" + OpTab[i][2]);
		System.out.println("----------------------------");

		// Print Literal Table
		System.out.println("\n\n   LITERAL TABLE\t\t");
		System.out.println("-----------------");
		System.out.println("LITERAL\tADDRESS");
		System.out.println("-----------------");
		for (int i = 0; i < litTabLine; i++)
			System.out.println(LitTab[i][0] + "\t" + LitTab[i][1]);
		System.out.println("------------------");

		// Initialize Pool Table
		for (int i = 0; i < litTabLine; i++) {
			if (LitTab[i][0] != null && (i + 1 < litTabLine) && LitTab[i + 1][0] != null) {
				if (i == 0) {
					PoolTab[poolTabLine] = i + 1;
					poolTabLine++;
				} else if (Integer.parseInt(LitTab[i][1]) < (Integer.parseInt(LitTab[i + 1][1])) - 1) {
					PoolTab[poolTabLine] = i + 2;
					poolTabLine++;
				}
			}
		}

		// Print Pool Table
		System.out.println("\n\n   POOL TABLE\t\t");
		System.out.println("-----------------");
		System.out.println("LITERAL NUMBER");
		System.out.println("-----------------");
		for (int i = 0; i < poolTabLine; i++)
			System.out.println(PoolTab[i]);
		System.out.println("------------------");

		// Close file reader
		bufferedReader.close();
	}
}

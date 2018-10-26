package move.lang.keys;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class MoveLangKeys {

	public static void main(String[] args) {
		boolean incorrect = true; 
		Scanner scanner = new Scanner(System.in);

		// Enter source folder

		String source = "";
		File sourceDir = null;

		while (incorrect) {
			System.out.println("Enter the source directory: ");
			source = scanner.nextLine();
			sourceDir = new File(source);

			if (!sourceDir.exists()) {
				System.out.println("Invalid path!");
			} else {
				incorrect = false;
			}
		}

		// Enter destination folder

		String dest = "";
		File destDir = new File(dest);
		incorrect = true; 

		while (incorrect) {
			System.out.println("Enter the destination directory: ");
			dest = scanner.nextLine();
			destDir = new File(dest);

			if (!destDir.exists()) {
				System.out.println("Invalid path!");
			} else {
				incorrect = false;
			}
		}

		// Enter language key

		String fullKey = "";
		String key = "";
		incorrect = true;

		while (incorrect) {
			System.out.println("Enter the full language key: ");
			fullKey = scanner.nextLine();

			if (fullKey.contains("=")) {
				key = fullKey.substring(0, fullKey.indexOf("="));
				incorrect = false;
			} else {
				System.out.println("Invalid key! Use the following format: key=value");
			}
		}

		// Select copy/move

		System.out.println("(1) Copy, (2) Move");
		incorrect = true;
		boolean move = false;

		while (incorrect) {
			String method = scanner.nextLine();
			if (method.equals("1")) {
				incorrect = false;
			} else if (method.equals("2")) {
				move = true;
				incorrect = false;
			} else {
				System.out.println("Incorrect input. Enter 1 for copying or 2 for moving the language keys.");
			}
		}

		scanner.close();

		// Migrate language keys

		System.out.println("Key: " + key);
		System.out.println("Processing lang key files");

		File[] sourceFiles = sourceDir.listFiles();

		try {
			System.out.println("Number of files: " + sourceFiles.length);
			for(File sourceFile : sourceFiles) {
				System.out.println(sourceFile.getName());

				FileReader input = new FileReader(sourceFile);
				BufferedReader bufferReader = new BufferedReader(input);

				String line = "";

				while ( (line = bufferReader.readLine()) != null) {
					if(line.startsWith(key)) {
						System.out.println("    " + line);
						File destFile = new File(dest + "\\" + sourceFile.getName());

						replaceLine(destFile, fullKey + " (Automatic Copy)", line);

						break;
					}
				}

				bufferReader.close();

				if (move) {
					replaceLine(sourceFile, line, "");
				}
			}


		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Processing finished");
	}

	public static void replaceLine(File file, String sourceLine, String destLine) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = "";
			String oldText = "";
			while((line = reader.readLine()) != null) {
				if (line.startsWith(sourceLine)) {
					if (!destLine.equals("")) {
						oldText += destLine + "\n";
					}
				}
				else {
					oldText += line + "\n";
				}
			}

			reader.close();

			String newtext = oldText.trim();

			FileWriter writer = new FileWriter(file);
			writer.write(newtext);
			writer.close();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public static void addLine(File file, String destLine) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = "";
			String oldText = "";
			while((line = reader.readLine()) != null) {
				oldText += line + "\n";
			}

			oldText += destLine + "\n";

			reader.close();

			String newtext = oldText.trim();

			FileWriter writer = new FileWriter(file);
			writer.write(newtext);
			writer.close();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}

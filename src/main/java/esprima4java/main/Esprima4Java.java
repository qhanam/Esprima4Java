package esprima4java.main;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class Esprima4Java {

    public static void main(String[] args) {

	/* The test files. */
	Esprima4JavaOptions options = new Esprima4JavaOptions();
	CmdLineParser cmdParser = new CmdLineParser(options);

	try {
	    cmdParser.parseArgument(args);
	} catch (CmdLineException e) {
	    Esprima4Java.printUsage(e.getMessage(), cmdParser);
	    return;
	}

	Path path = Paths.get(options.getAstPath());

	try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {

	    JsonParser parser = new JsonParser();
	    JsonElement tree = parser.parse(reader);

	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    /**
     * Prints the usage of main.
     * 
     * @param error
     *            The error message that triggered the usage message.
     * @param parser
     *            The args4j parser.
     */
    private static void printUsage(String error, CmdLineParser parser) {
	System.out.println(error);
	System.out.print("Usage: Esprima4Java ");
	parser.printSingleLineUsage(System.out);
	System.out.println("");
    }

}

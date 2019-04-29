package esprima4java.main;

import org.kohsuke.args4j.Option;

public class Esprima4JavaOptions {

    @Option(name = "-t", aliases = { "--ast" },
	    usage = "The path to the serialized (JSON) Esprima-generated AST.")
    private String astPath = null;

    @Option(name = "-h", aliases = { "--help" }, usage = "Display the help file.")
    private boolean help = false;

    public String getAstPath() {
	return astPath;
    }

    public boolean getHelp() {
	return help;
    }

}
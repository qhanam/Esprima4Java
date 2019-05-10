package esprima4java.cfg;

import java.util.LinkedList;
import java.util.Queue;

import esprima4java.ast.Function;
import esprima4java.ast.Program;

/**
 * A CFG factory for JavaScript.
 */
public class CfgFactory {

    public CfgMap createCfgs(Program program) {
	CfgBuilder builder = new CfgBuilder(program);
	return builder.getCfgMap();
    }

    private class CfgBuilder {

	/* Stores the CFGs from all the functions. */
	private CfgMap cfgMap;

	/* Stores the un-built `FunctionNode`s. */
	private Queue<Function> unbuilt;

	/**
	 * @param root
	 *            the script.
	 */
	public CfgBuilder(Program root) {
	    cfgMap = new CfgMap();
	    unbuilt = new LinkedList<Function>();
	    build(root);
	}

	/**
	 * @return The list of CFGs for the script.
	 */
	public CfgMap getCfgMap() {
	    return cfgMap;
	}

	private void build(Program script) {

	    /* Start by getting the CFG for the script. */
	    cfgMap.addCfg(script, script.buildCfg());

	    /* Get the list of functions in the script. */
	    for (Function function : FunctionVisitor.getFunctions(script)) {
		unbuilt.add(function);
	    }

	    /* For each function, generate its CFG. */
	    while (!unbuilt.isEmpty()) {
		Function function = unbuilt.remove();
		cfgMap.addCfg(function, function.buildCfg());
	    }

	}
    }
}
package esprima4java.cfg;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import esprima4java.ast.Node;

/**
 * A singleton map of {@code Node}s to their CFGs.
 * 
 * This is used by the interpreter to add new frames to the call stack when a
 * call site is encountered.
 * 
 * This class is not thread safe (ie. it limits the analysis to inspecting one
 * program at a time).
 */
public class CfgMap {

    private static CfgMap instance = null;

    private Map<Node, Cfg> cfgMap;

    public CfgMap() {
	this.cfgMap = new HashMap<Node, Cfg>();
    }

    public CfgMap instance() {
	if (instance == null)
	    instance = new CfgMap();
	return instance;
    }

    /**
     * Returns the CFG created from the given function definition.
     * 
     * @return CFG or {@code null} if no CFG exists for the function definition.
     */
    public Cfg getCfgFor(Node function) {
	return cfgMap.get(function);
    }

    /**
     * Adds a function -> CFG mapping.
     * 
     * @param function
     *            The function the CFG was built from.
     * @param cfg
     *            The CFG for the function.
     */
    public void addCfg(Node function, Cfg cfg) {
	cfgMap.put(function, cfg);
    }

    /**
     * Returns the CFGs as a collection.
     */
    public Collection<Cfg> getCfgs() {
	return cfgMap.values();
    }

}
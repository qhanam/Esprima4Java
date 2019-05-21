package esprima4java.cfg.builders;

import esprima4java.ast.Node;
import esprima4java.cfg.Cfg;
import esprima4java.cfg.CfgAssertNode;
import esprima4java.cfg.CfgNode;

public class CfgBuilderUtils {

    static void addEdge(CfgNode from, CfgNode to) {
	from.addOutgoing(to);
	to.addIncoming(from);
    }

    static void addTrueAssertion(Node condition, CfgNode from, CfgNode to) {
	Cfg cfg = CfgBuilderForCallSites.build(condition);
	if (cfg == null) {
	    // Add an assert node between `from` and `to`.
	    new CfgAssertNode(condition, from, to, true);
	} else {
	    // Add an assert node and its call sites between `from` and `to`.
	    addEdge(from, cfg.getEntryNode());
	    new CfgAssertNode(condition, cfg.getExitNode(), to, true);
	}
    }

    static void addFalseAssertion(Node condition, CfgNode from, CfgNode to) {
	Cfg cfg = CfgBuilderForCallSites.build(condition);
	if (cfg == null) {
	    // Add an assert node between `from` and `to`.
	    new CfgAssertNode(condition, from, to, false);
	} else {
	    // Add an assert node and its call sites between `from` and `to`.
	    addEdge(from, cfg.getEntryNode());
	    new CfgAssertNode(condition, cfg.getExitNode(), to, false);
	}
    }

}

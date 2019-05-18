package esprima4java.cfg.builders;

import esprima4java.ast.Node;
import esprima4java.cfg.CfgAssertNode;
import esprima4java.cfg.CfgNode;

public class CfgBuilderUtils {

    static void addEdge(CfgNode from, CfgNode to) {
	from.addOutgoing(to);
	to.addIncoming(from);
    }

    static void addTrueAssertion(Node condition, CfgNode from, CfgNode to) {
	new CfgAssertNode(condition, from, to, true);
    }

    static void addFalseAssertion(Node condition, CfgNode from, CfgNode to) {
	new CfgAssertNode(condition, from, to, false);
    }

}

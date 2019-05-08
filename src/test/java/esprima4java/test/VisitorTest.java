package esprima4java.test;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableMap;

import esprima4java.ast.ArrayExpression;
import esprima4java.ast.AssignmentExpression;
import esprima4java.ast.AssignmentExpression.AssignmentOperator;
import esprima4java.ast.Identifier;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;
import esprima4java.utilities.NodeVisitor;

class VisitorTest {

    void test(Node node, Map<NodeType, Integer> expected) {
	Map<NodeType, Integer> actual = new HashMap<NodeType, Integer>();

	NodeVisitor typeVisitor = new NodeVisitor() {

	    @Override
	    public boolean visit(Node node) {
		Integer count = actual.containsKey(node.type()) ? actual.get(node.type()) + 1 : 1;
		actual.put(node.type(), count);
		return true;
	    }

	};

	node.accept(typeVisitor);

	assertEquals(expected, actual);
    }

    @Test
    void testArrayExpression() {
	Node node = ArrayExpression.create(Collections.singletonList(Identifier.create("a")));
	Map<NodeType, Integer> expected = new ImmutableMap.Builder<NodeType, Integer>()
		.put(NodeType.ARRAY_EXPRESSION, 1).put(NodeType.IDENTIFIER, 1).build();
	test(node, expected);
    }

    @Test
    void testAssignmentExpression() {
	Node node = AssignmentExpression.create(AssignmentOperator.ASSIGN, Identifier.create("a"),
		Identifier.create("b"));
	Map<NodeType, Integer> expected = new ImmutableMap.Builder<NodeType, Integer>()
		.put(NodeType.ASSIGNMENT_EXPRESSION, 1).put(NodeType.IDENTIFIER, 2).build();
	test(node, expected);
    }

}

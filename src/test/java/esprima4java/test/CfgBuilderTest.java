package esprima4java.test;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import esprima4java.ast.EmptyStatement;
import esprima4java.ast.ExpressionStatement;
import esprima4java.ast.Identifier;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;

class CfgBuilderTest {

    void test(Node node, List<NodeType> expected) {
	List<NodeType> actual = node.buildCfg().depthFirstTrace();
	assertEquals(expected, actual);
    }

    @Test
    void testEmptyStatement() {
	Node node = EmptyStatement.create();
	List<NodeType> expected = Arrays.asList(NodeType.EMPTY_STATEMENT);
	test(node, expected);
    }

    @Test
    void testExpressionStatement() {
	Node node = ExpressionStatement.create(Identifier.create("a"), null);
	List<NodeType> expected = Arrays.asList(NodeType.EXPRESSION_STATEMENT);
	test(node, expected);
    }

}

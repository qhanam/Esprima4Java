package esprima4java.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import esprima4java.ast.BlockStatement;
import esprima4java.ast.BreakStatement;
import esprima4java.ast.ContinueStatement;
import esprima4java.ast.EmptyStatement;
import esprima4java.ast.ExpressionStatement;
import esprima4java.ast.Identifier;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;
import esprima4java.ast.ReturnStatement;
import esprima4java.cfg.Cfg;

class CfgBuilderTest {

    Cfg test(Node node, List<NodeType> expected) {
	Cfg cfg = node.buildCfg();
	List<NodeType> actual = cfg.depthFirstTrace();
	assertEquals(expected, actual);
	return cfg;
    }

    @Test
    void testEmptyStatement() {
	Node node = EmptyStatement.create();
	List<NodeType> expected = Arrays.asList(NodeType.EMPTY_STATEMENT);
	Cfg cfg = test(node, expected);
	assertNotNull(cfg.getExitNode());
    }

    @Test
    void testExpressionStatement() {
	Node node = ExpressionStatement.create(Identifier.create("a"), null);
	List<NodeType> expected = Arrays.asList(NodeType.EXPRESSION_STATEMENT);
	Cfg cfg = test(node, expected);
	assertNotNull(cfg.getExitNode());
    }

    @Test
    void testBreakStatement() {
	Node node = BreakStatement.create(null);
	List<NodeType> expected = Arrays.asList(NodeType.BREAK_STATEMENT);
	Cfg cfg = test(node, expected);
	assertEquals(1, cfg.getBreakNodes().size());
    }

    @Test
    void testContinueStatement() {
	Node node = ContinueStatement.create(null);
	List<NodeType> expected = Arrays.asList(NodeType.CONTINUE_STATEMENT);
	Cfg cfg = test(node, expected);
	assertEquals(1, cfg.getContinueNodes().size());
    }

    @Test
    void testReturnStatement() {
	Node node = ReturnStatement.create(null);
	List<NodeType> expected = Arrays.asList(NodeType.RETURN_STATEMENT);
	Cfg cfg = test(node, expected);
	assertEquals(1, cfg.getReturnNodes().size());
    }

    @Test
    void testBlockStatement() {
	Node node = BlockStatement.create(Arrays.asList(EmptyStatement.create()));
	List<NodeType> expected = Arrays.asList(NodeType.EMPTY_STATEMENT, NodeType.LITERAL,
		NodeType.EMPTY_STATEMENT);
	Cfg cfg = test(node, expected);
	assertNotNull(cfg.getExitNode());
    }
}

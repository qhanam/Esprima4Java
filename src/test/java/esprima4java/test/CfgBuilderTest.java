package esprima4java.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import esprima4java.ast.BlockStatement;
import esprima4java.ast.BreakStatement;
import esprima4java.ast.ContinueStatement;
import esprima4java.ast.EmptyStatement;
import esprima4java.ast.ExpressionStatement;
import esprima4java.ast.Identifier;
import esprima4java.ast.IfStatement;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;
import esprima4java.ast.ReturnStatement;
import esprima4java.ast.SwitchCase;
import esprima4java.ast.SwitchStatement;
import esprima4java.ast.WhileStatement;
import esprima4java.ast.WithStatement;
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

    @Test
    void testWithStatement() {
	Node node = WithStatement.create(Identifier.create("a"), EmptyStatement.create());
	List<NodeType> expected = Arrays.asList(NodeType.WITH_STATEMENT, NodeType.LITERAL,
		NodeType.EMPTY_STATEMENT);
	Cfg cfg = test(node, expected);
	assertNotNull(cfg.getExitNode());
    }

    @Test
    void testIfStatement() {
	Node node = IfStatement.create(Identifier.create("a"), EmptyStatement.create(),
		EmptyStatement.create());
	List<NodeType> expected = Arrays.asList(NodeType.EMPTY_STATEMENT, NodeType.IDENTIFIER,
		NodeType.UNARY_EXPRESSION, NodeType.EMPTY_STATEMENT, NodeType.LITERAL,
		NodeType.EMPTY_STATEMENT, NodeType.EMPTY_STATEMENT, NodeType.LITERAL,
		NodeType.EMPTY_STATEMENT);
	Cfg cfg = test(node, expected);
	assertNotNull(cfg.getExitNode());
    }

    @Test
    void testSwitchCaseWithBreak() {
	Node node = SwitchCase.create(Identifier.create("a"),
		Collections.singletonList(BreakStatement.create(null)));
	List<NodeType> expected = Arrays.asList(NodeType.EMPTY_STATEMENT, NodeType.LITERAL,
		NodeType.BREAK_STATEMENT);
	Cfg cfg = test(node, expected);
	assertNull(cfg.getExitNode());
    }

    @Test
    void testSwitchCaseWithoutBreak() {
	Node node = SwitchCase.create(Identifier.create("a"),
		Collections.singletonList(EmptyStatement.create()));
	List<NodeType> expected = Arrays.asList(NodeType.EMPTY_STATEMENT, NodeType.LITERAL,
		NodeType.EMPTY_STATEMENT);
	Cfg cfg = test(node, expected);
	assertNotNull(cfg.getExitNode());
    }

    @Test
    void testSwitchStatementFallthrough() {
	Node node = SwitchStatement.create(Identifier.create("x"),
		Arrays.asList(SwitchCase.create(Identifier.create("a"),
			Collections.singletonList(EmptyStatement.create()))));
	List<NodeType> expected = Arrays.asList(NodeType.EMPTY_STATEMENT, NodeType.IDENTIFIER,
		NodeType.IDENTIFIER, NodeType.EMPTY_STATEMENT, NodeType.LITERAL,
		NodeType.EMPTY_STATEMENT, NodeType.LITERAL, NodeType.EMPTY_STATEMENT);
	Cfg cfg = test(node, expected);
	assertNotNull(cfg.getExitNode());
    }

    @Test
    void testSwitchStatementBreak() {
	Node node = SwitchStatement.create(Identifier.create("x"),
		Arrays.asList(SwitchCase.create(Identifier.create("a"),
			Collections.singletonList(BreakStatement.create(null)))));
	List<NodeType> expected = Arrays.asList(NodeType.EMPTY_STATEMENT, NodeType.IDENTIFIER,
		NodeType.IDENTIFIER, NodeType.EMPTY_STATEMENT, NodeType.LITERAL,
		NodeType.BREAK_STATEMENT, NodeType.LITERAL, NodeType.EMPTY_STATEMENT);
	Cfg cfg = test(node, expected);
	assertNotNull(cfg.getExitNode());
    }

    @Test
    void testSwitchStatementReturn() {
	Node node = SwitchStatement.create(Identifier.create("x"), Arrays.asList(
		SwitchCase.create(null, Collections.singletonList(ReturnStatement.create(null)))));
	List<NodeType> expected = Arrays.asList(NodeType.EMPTY_STATEMENT, NodeType.LITERAL,
		NodeType.EMPTY_STATEMENT, NodeType.LITERAL, NodeType.RETURN_STATEMENT);
	Cfg cfg = test(node, expected);
	assertNull(cfg.getExitNode());
    }

    @Test
    void testWhileStatement() {
	Node node = WhileStatement.create(Identifier.create("x"), EmptyStatement.create());
	List<NodeType> expected = Arrays.asList(NodeType.EMPTY_STATEMENT, NodeType.IDENTIFIER,
		NodeType.UNARY_EXPRESSION, NodeType.EMPTY_STATEMENT, NodeType.EMPTY_STATEMENT,
		NodeType.LITERAL);
	Cfg cfg = test(node, expected);
	assertNotNull(cfg.getExitNode());
    }

    @Test
    void testWhileStatementReturn() {
	Node node = WhileStatement.create(Identifier.create("x"), ReturnStatement.create(null));
	List<NodeType> expected = Arrays.asList(NodeType.EMPTY_STATEMENT, NodeType.IDENTIFIER,
		NodeType.UNARY_EXPRESSION, NodeType.EMPTY_STATEMENT, NodeType.RETURN_STATEMENT);
	Cfg cfg = test(node, expected);
	assertNotNull(cfg.getExitNode());
	assertNotNull(cfg.getReturnNodes());
    }
}

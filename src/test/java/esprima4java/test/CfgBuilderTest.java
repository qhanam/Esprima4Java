package esprima4java.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import esprima4java.ast.BlockStatement;
import esprima4java.ast.BreakStatement;
import esprima4java.ast.CallExpression;
import esprima4java.ast.ContinueStatement;
import esprima4java.ast.DoWhileStatement;
import esprima4java.ast.EmptyStatement;
import esprima4java.ast.ExpressionStatement;
import esprima4java.ast.ForStatement;
import esprima4java.ast.Identifier;
import esprima4java.ast.IfStatement;
import esprima4java.ast.Node;
import esprima4java.ast.ReturnStatement;
import esprima4java.ast.SwitchCase;
import esprima4java.ast.SwitchStatement;
import esprima4java.ast.WhileStatement;
import esprima4java.ast.WithStatement;
import esprima4java.cfg.Cfg;
import esprima4java.cfg.CfgAssertNode;
import esprima4java.cfg.CfgBlockScopeEntryNode;
import esprima4java.cfg.CfgBlockScopeExitNode;
import esprima4java.cfg.CfgBreakNode;
import esprima4java.cfg.CfgCallsiteNode;
import esprima4java.cfg.CfgContinueNode;
import esprima4java.cfg.CfgEmptyNode;
import esprima4java.cfg.CfgEvaluateNode;
import esprima4java.cfg.CfgFunctionEntryNode;
import esprima4java.cfg.CfgFunctionExitNode;
import esprima4java.cfg.CfgReturnNode;
import esprima4java.cfg.CfgThrowNode;

class CfgBuilderTest {

    enum T {
	ASSERT(CfgAssertNode.class.getSimpleName()), //
	BLOCK_ENTRY(CfgBlockScopeEntryNode.class.getSimpleName()), //
	BLOCK_EXIT(CfgBlockScopeExitNode.class.getSimpleName()), //
	BREAK(CfgBreakNode.class.getSimpleName()), //
	CALL(CfgCallsiteNode.class.getSimpleName()), //
	CONTINUE(CfgContinueNode.class.getSimpleName()), //
	EMPTY(CfgEmptyNode.class.getSimpleName()), //
	EVALUATE(CfgEvaluateNode.class.getSimpleName()), //
	FUNCTION_ENTRY(CfgFunctionEntryNode.class.getSimpleName()), //
	FUNCTION_EXIT(CfgFunctionExitNode.class.getSimpleName()), //
	RETURN(CfgReturnNode.class.getSimpleName()), //
	THROW(CfgThrowNode.class.getSimpleName());

	String type;

	T(String type) {
	    this.type = type;
	}

	String type() {
	    return type;
	}
    }

    Cfg test(Node node, List<T> expected) {
	Cfg cfg = node.buildCfg();
	List<String> expectedAsString = new ArrayList<>();
	expected.forEach(e -> expectedAsString.add(e.type));
	List<String> actual = cfg.depthFirstTrace();
	assertEquals(expectedAsString, actual);
	return cfg;
    }

    @Test
    void testEmptyStatement() {
	Node node = EmptyStatement.create();
	List<T> expected = Arrays.asList(T.EVALUATE);
	Cfg cfg = test(node, expected);
	assertNotNull(cfg.getExitNode());
    }

    @Test
    void testExpressionStatement() {
	Node node = ExpressionStatement.create(Identifier.create("a"), null);
	List<T> expected = Arrays.asList(T.EVALUATE);
	Cfg cfg = test(node, expected);
	assertNotNull(cfg.getExitNode());
    }

    @Test
    void testBreakStatement() {
	Node node = BreakStatement.create(null);
	List<T> expected = Arrays.asList(T.BREAK);
	Cfg cfg = test(node, expected);
	assertEquals(1, cfg.getBreakNodes().size());
    }

    @Test
    void testWhileStatement() {
	Node node = WhileStatement.create(Identifier.create("x"), EmptyStatement.create());
	List<T> expected = Arrays.asList(T.EMPTY, T.ASSERT, T.EMPTY, T.ASSERT, T.EVALUATE, T.EMPTY,
		T.ASSERT);
	Cfg cfg = test(node, expected);
	assertNotNull(cfg.getExitNode());
    }

    @Test
    void testWhileStatementReturn() {
	Node node = WhileStatement.create(Identifier.create("x"), ReturnStatement.create(null));
	List<T> expected = Arrays.asList(T.EMPTY, T.ASSERT, T.EMPTY, T.ASSERT, T.RETURN);
	Cfg cfg = test(node, expected);
	assertNotNull(cfg.getExitNode());
	assertNotNull(cfg.getReturnNodes());
    }

    @Test
    void testWhileStatementWithCallsites() {
	Node node = WhileStatement.create(
		CallExpression.create(Identifier.create("foo"), Collections.emptyList()),
		EmptyStatement.create());
	List<T> expected = Arrays.asList(T.EMPTY, T.CALL, T.ASSERT, T.EMPTY, T.CALL, T.ASSERT,
		T.EVALUATE, T.EMPTY, T.CALL, T.ASSERT);
	Cfg cfg = test(node, expected);
	assertNotNull(cfg.getExitNode());
    }

    @Test
    void testContinueStatement() {
	Node node = ContinueStatement.create(null);
	List<T> expected = Arrays.asList(T.CONTINUE);
	Cfg cfg = test(node, expected);
	assertEquals(1, cfg.getContinueNodes().size());
    }

    @Test
    void testReturnStatement() {
	Node node = ReturnStatement.create(null);
	List<T> expected = Arrays.asList(T.RETURN);
	Cfg cfg = test(node, expected);
	assertEquals(1, cfg.getReturnNodes().size());
    }

    @Test
    void testReturnStatementWithCallsites() {
	Node node = ReturnStatement
		.create(CallExpression.create(Identifier.create("foo"), Collections.emptyList()));
	List<T> expected = Arrays.asList(T.CALL, T.RETURN);
	Cfg cfg = test(node, expected);
	assertEquals(1, cfg.getReturnNodes().size());
    }

    @Test
    void testBlockStatement() {
	Node node = BlockStatement.create(Arrays.asList(EmptyStatement.create()));
	List<T> expected = Arrays.asList(T.BLOCK_ENTRY, T.EVALUATE, T.BLOCK_EXIT);
	Cfg cfg = test(node, expected);
	assertNotNull(cfg.getExitNode());
    }

    @Test
    void testWithStatement() {
	// With statements are not currently supported.
	Node node = WithStatement.create(Identifier.create("a"), EmptyStatement.create());
	List<T> expected = Arrays.asList(T.EVALUATE);
	Cfg cfg = test(node, expected);
	assertNotNull(cfg.getExitNode());
    }

    @Test
    void testIfStatement() {
	Node node = IfStatement.create(Identifier.create("a"), EmptyStatement.create(),
		EmptyStatement.create());
	List<T> expected = Arrays.asList(T.EMPTY, T.ASSERT, T.EVALUATE, T.EMPTY, T.ASSERT,
		T.EVALUATE);
	Cfg cfg = test(node, expected);
	assertNotNull(cfg.getExitNode());
    }

    @Test
    void testSwitchCaseWithBreak() {
	Node node = SwitchCase.create(Identifier.create("a"),
		Collections.singletonList(BreakStatement.create(null)));
	List<T> expected = Arrays.asList(T.EMPTY, T.BREAK);
	Cfg cfg = test(node, expected);
	assertNotNull(cfg.getBreakNodes());
	assertNull(cfg.getExitNode());
    }

    @Test
    void testSwitchCaseWithoutBreak() {
	Node node = SwitchCase.create(Identifier.create("a"),
		Collections.singletonList(EmptyStatement.create()));
	List<T> expected = Arrays.asList(T.EMPTY, T.EVALUATE);
	Cfg cfg = test(node, expected);
	assertNotNull(cfg.getExitNode());
    }

    @Test
    void testSwitchStatementFallthrough() {
	Node node = SwitchStatement.create(Identifier.create("x"),
		Arrays.asList(SwitchCase.create(Identifier.create("a"),
			Collections.singletonList(EmptyStatement.create()))));
	List<T> expected = Arrays.asList(T.EMPTY, T.ASSERT, T.EMPTY, T.EVALUATE, T.EMPTY, T.ASSERT);
	Cfg cfg = test(node, expected);
	assertNotNull(cfg.getExitNode());
    }

    @Test
    void testSwitchStatementBreak() {
	Node node = SwitchStatement.create(Identifier.create("x"),
		Arrays.asList(SwitchCase.create(Identifier.create("a"),
			Collections.singletonList(BreakStatement.create(null)))));
	List<T> expected = Arrays.asList(T.EMPTY, T.ASSERT, T.EMPTY, T.BREAK, T.EMPTY, T.ASSERT);
	Cfg cfg = test(node, expected);
	assertNotNull(cfg.getExitNode());
    }

    @Test
    void testSwitchStatementReturn() {
	Node node = SwitchStatement.create(Identifier.create("x"), Arrays.asList(
		SwitchCase.create(null, Collections.singletonList(ReturnStatement.create(null)))));
	List<T> expected = Arrays.asList(T.EMPTY, T.EMPTY, T.RETURN);
	Cfg cfg = test(node, expected);
	assertNotNull(cfg.getReturnNodes());
	assertNull(cfg.getExitNode());
    }

    @Test
    void testDoWhileStatement() {
	Node node = DoWhileStatement.create(Identifier.create("x"), EmptyStatement.create());
	List<T> expected = Arrays.asList(T.EMPTY, T.EVALUATE, T.EMPTY, T.ASSERT, T.EMPTY);
	Cfg cfg = test(node, expected);
	assertNotNull(cfg.getExitNode());
    }

    @Test
    void testForStatement() {
	Node node = ForStatement.create(Identifier.create("x"), Identifier.create("x"),
		Identifier.create("x"), EmptyStatement.create());
	List<T> expected = Arrays.asList(T.EVALUATE, T.EMPTY, T.ASSERT, T.EMPTY, T.ASSERT,
		T.EVALUATE, T.EVALUATE, T.EMPTY, T.ASSERT);
	Cfg cfg = test(node, expected);
	assertNotNull(cfg.getExitNode());
    }
}

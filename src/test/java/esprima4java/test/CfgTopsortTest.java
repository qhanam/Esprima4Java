package esprima4java.test;

import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

import org.junit.jupiter.api.Test;

import esprima4java.abstractstate.AnalysisState;
import esprima4java.abstractstate.BuiltinState;
import esprima4java.abstractstate.UserState;
import esprima4java.ast.CallExpression;
import esprima4java.ast.EmptyStatement;
import esprima4java.ast.Identifier;
import esprima4java.ast.Node;
import esprima4java.ast.ReturnStatement;
import esprima4java.ast.WhileStatement;
import esprima4java.cfg.Cfg;
import esprima4java.cfg.CfgNode;

class CfgTopsortTest {

    void test(Node node) {
	Cfg cfg = node.buildCfg();
	Set<CfgNode> visited = new HashSet<>();
	Queue<CfgNode> ordered = cfg.initializeControlState(
		AnalysisState.initializeAnalysisState(new BuiltinState(), new UserState[0]));
	// Check that order is sound.
	while (!ordered.isEmpty()) {
	    ordered.peek().incoming().forEach(parent -> assertTrue(visited.contains(parent)));
	    visited.add(ordered.remove());
	}
    }

    @Test
    void testWhileStatement() {
	test(WhileStatement.create(Identifier.create("x"), EmptyStatement.create()));
    }

    @Test
    void testWhileStatementReturn() {
	test(WhileStatement.create(Identifier.create("x"), ReturnStatement.create(null)));
    }

    @Test
    void testWhileStatementWithCallsites() {
	test(WhileStatement.create(
		CallExpression.create(Identifier.create("foo"), Collections.emptyList()),
		EmptyStatement.create()));
    }
}

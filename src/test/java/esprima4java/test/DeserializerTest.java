package esprima4java.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import esprima4java.Esprima2Java;
import esprima4java.ast.BlockStatement;
import esprima4java.ast.BreakStatement;
import esprima4java.ast.ContinueStatement;
import esprima4java.ast.EmptyStatement;
import esprima4java.ast.ExpressionStatement;
import esprima4java.ast.FunctionExpression;
import esprima4java.ast.Identifier;
import esprima4java.ast.LabeledStatement;
import esprima4java.ast.Literal;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;
import esprima4java.ast.Program;
import esprima4java.ast.RegExpLiteral;
import esprima4java.ast.ReturnStatement;
import esprima4java.ast.WithStatement;
import esprima4java.ast.deserialize.DeserializationException;

class DeserializerTest {

    Node test(String json, NodeType type, Node expected) {
	try (Reader reader = new StringReader(json)) {
	    Node actual = Esprima2Java.deserialize(reader);
	    assertEquals(type, actual.type());
	    assertEquals(expected, actual);
	    return actual;
	} catch (IOException e) {
	    e.printStackTrace();
	    fail("Parsing should succeed.");
	} catch (DeserializationException e) {
	    e.printStackTrace();
	    fail("Deserialization should succeed.");
	}
	return null;
    }

    @Test
    void testIdentifierParsed() {
	String json = "{ 'type': 'Identifier', 'name': 'foo' }";
	Identifier expected = Identifier.create("foo");
	test(json, NodeType.IDENTIFIER, expected);
    }

    @Test
    void testNumberLiteralParsed() {
	String json = "{ 'type': 'Literal', 'value': 1, 'raw': '1' }";
	Literal expected = Literal.createNumber(1, "1");
	test(json, NodeType.LITERAL, expected);
    }

    @Test
    void testStringLiteralParsed() {
	String json = "{ 'type': 'Literal', 'value': '0', 'raw': '\"0\"' }";
	Literal expected = Literal.createString("0", "\"0\"");
	test(json, NodeType.LITERAL, expected);
    }

    @Test
    void testBoolLiteralParsed() {
	String json = "{ 'type': 'Literal', 'value': true, 'raw': 'true' }";
	Literal expected = Literal.createBoolean(true, "true");
	test(json, NodeType.LITERAL, expected);
    }

    @Test
    void testRegExLiteralParsed() {
	String json = "{ 'type': 'Literal', 'value': '/.*/g', 'raw': '/.*/g', 'regex': { 'pattern': '.*', 'flags': 'g' } }";
	Literal expected = Literal.createRegEx(RegExpLiteral.create(".*", "g"), "/.*/g");
	test(json, NodeType.LITERAL, expected);
    }

    @Test
    void testNullLiteralParsed() {
	String json = "{ 'type': 'Literal', 'value': null, 'raw': 'null' }";
	Literal expected = Literal.createNull("null");
	test(json, NodeType.LITERAL, expected);
    }

    @Test
    void testEmptyStatementParsed() {
	String json = "{ 'type': 'EmptyStatement' }";
	EmptyStatement expected = EmptyStatement.create();
	test(json, NodeType.EMPTY_STATEMENT, expected);
    }

    @Test
    void testExpressionStatementParsed() {
	String json = "{ 'type': 'ExpressionStatement', 'expression': { 'type': 'Identifier', 'name': 'a' } }";
	ExpressionStatement expected = ExpressionStatement.create(Identifier.create("a"));
	test(json, NodeType.EXPRESSION_STATEMENT, expected);
    }

    @Test
    void testDirectiveParsed() {
	String json = "{ 'type': 'ExpressionStatement', 'expression': { 'type': 'Literal', 'value': 'use strict', 'raw': '\"use strict\"' }, 'directive': 'use strict' }";
	ExpressionStatement expected = ExpressionStatement
		.create(Literal.createString("use strict", "\"use strict\""), "use strict");
	test(json, NodeType.EXPRESSION_STATEMENT, expected);
    }

    @Test
    void testBlockStatementParsed() {
	String json = "{ 'type': 'BlockStatement', 'body': [ { 'type': 'EmptyStatement' } ] }";
	BlockStatement expected = BlockStatement
		.create(Collections.singletonList(EmptyStatement.create()));
	test(json, NodeType.BLOCK_STATEMENT, expected);
    }

    @Test
    void testProgramParsed() {
	String json = "{ 'type': 'Program', 'body': [ { 'type': 'EmptyStatement' } ] }";
	Program expected = Program.create(Collections.singletonList(EmptyStatement.create()));
	test(json, NodeType.PROGRAM, expected);
    }

    @Test
    void testFunctionExpressionParsed() {
	String json = "{ 'type': 'FunctionExpression', 'id': { 'type': 'Identifier', 'name': 'foo' }, params: [ { 'type': 'Identifier', 'name': 'a' } ], body: { 'type': 'BlockStatement', 'body': [ ] } }";
	FunctionExpression expected = FunctionExpression.create(Identifier.create("foo"),
		Collections.singletonList(Identifier.create("a")),
		BlockStatement.create(Collections.emptyList()));
	test(json, NodeType.FUNCTION_EXPRESSION, expected);
    }

    @Test
    void testAnonFunctionExpressionParsed() {
	String json = "{ 'type': 'FunctionExpression', params: [ { 'type': 'Identifier', 'name': 'a' } ], body: { 'type': 'BlockStatement', 'body': [ ] } }";
	FunctionExpression expected = FunctionExpression.create(
		Collections.singletonList(Identifier.create("a")),
		BlockStatement.create(Collections.emptyList()));
	test(json, NodeType.FUNCTION_EXPRESSION, expected);
    }

    @Test
    void testWithStatementParsed() {
	String json = "{ 'type': 'WithStatement', 'object': { 'type': 'Identifier', 'name': 'x' }, 'body': { 'type': 'EmptyStatement' } }";
	WithStatement expected = WithStatement.create(Identifier.create("x"),
		EmptyStatement.create());
	test(json, NodeType.WITH_STATEMENT, expected);
    }

    @Test
    void testReturnStatementParsed() {
	String json = "{ 'type': 'ReturnStatement' }";
	ReturnStatement expected = ReturnStatement.create();
	test(json, NodeType.RETURN_STATEMENT, expected);
    }

    @Test
    void testReturnStatementWithArgumentParsed() {
	String json = "{ 'type': 'ReturnStatement', 'argument': { 'type': 'Identifier', 'name': 'a' } }";
	ReturnStatement expected = ReturnStatement.create(Identifier.create("a"));
	test(json, NodeType.RETURN_STATEMENT, expected);
    }

    @Test
    void testLabeledStatementParsed() {
	String json = "{ 'type': 'LabeledStatement', 'label': { 'type': 'Identifier', 'name': 'a' }, 'body': { 'type': 'EmptyStatement' } }";
	LabeledStatement expected = LabeledStatement.create(Identifier.create("a"),
		EmptyStatement.create());
	test(json, NodeType.LABELED_STATEMENT, expected);
    }

    @Test
    void testBreakStatementParsed() {
	String json = "{ 'type': 'BreakStatement' }";
	BreakStatement expected = BreakStatement.create();
	test(json, NodeType.BREAK_STATEMENT, expected);
    }

    @Test
    void testBreakStatementWithLabelParsed() {
	String json = "{ 'type': 'BreakStatement', 'label': { 'type': 'Identifier', 'name': 'a' } }";
	BreakStatement expected = BreakStatement.create(Identifier.create("a"));
	test(json, NodeType.BREAK_STATEMENT, expected);
    }

    @Test
    void testContinueStatementParsed() {
	String json = "{ 'type': 'ContinueStatement' }";
	ContinueStatement expected = ContinueStatement.create();
	test(json, NodeType.CONTINUE_STATEMENT, expected);
    }

    @Test
    void testContinueStatementWithLabelParsed() {
	String json = "{ 'type': 'ContinueStatement', 'label': { 'type': 'Identifier', 'name': 'a' } }";
	ContinueStatement expected = ContinueStatement.create(Identifier.create("a"));
	test(json, NodeType.CONTINUE_STATEMENT, expected);
    }

}

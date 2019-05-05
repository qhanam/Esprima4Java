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
import esprima4java.ast.CatchClause;
import esprima4java.ast.ContinueStatement;
import esprima4java.ast.DoWhileStatement;
import esprima4java.ast.EmptyStatement;
import esprima4java.ast.ExpressionStatement;
import esprima4java.ast.ForInStatement;
import esprima4java.ast.ForStatement;
import esprima4java.ast.FunctionExpression;
import esprima4java.ast.Identifier;
import esprima4java.ast.IfStatement;
import esprima4java.ast.LabeledStatement;
import esprima4java.ast.Literal;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;
import esprima4java.ast.Program;
import esprima4java.ast.RegExpLiteral;
import esprima4java.ast.ReturnStatement;
import esprima4java.ast.SwitchCase;
import esprima4java.ast.SwitchStatement;
import esprima4java.ast.ThisExpression;
import esprima4java.ast.ThrowStatement;
import esprima4java.ast.TryStatement;
import esprima4java.ast.VariableDeclaration;
import esprima4java.ast.VariableDeclarator;
import esprima4java.ast.WhileStatement;
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

    @Test
    void testIfStatementParsed() {
	String json = "{ 'type': 'IfStatement', 'test': { 'type': 'Identifier', 'name': 'x' }, 'consequent': { 'type': 'EmptyStatement' } }";
	IfStatement expected = IfStatement.create(Identifier.create("x"), EmptyStatement.create());
	test(json, NodeType.IF_STATEMENT, expected);
    }

    @Test
    void testIfStatementWithAlternateParsed() {
	String json = "{ 'type': 'IfStatement', 'test': { 'type': 'Identifier', 'name': 'x' }, 'consequent': { 'type': 'EmptyStatement' }, 'alternate': { 'type': 'EmptyStatement' } }";
	IfStatement expected = IfStatement.create(Identifier.create("x"), EmptyStatement.create(),
		EmptyStatement.create());
	test(json, NodeType.IF_STATEMENT, expected);
    }

    @Test
    void testSwitchCaseParsed() {
	String json = "{ 'type': 'SwitchCase', 'test': { 'type': 'Identifier', 'name': 'x' }, 'consequent': [ { 'type': 'EmptyStatement' } ] }";
	SwitchCase expected = SwitchCase.create(Identifier.create("x"),
		Collections.singletonList(EmptyStatement.create()));
	test(json, NodeType.SWITCH_CASE, expected);
    }

    @Test
    void testDefaultSwitchCaseParsed() {
	String json = "{ 'type': 'SwitchCase', 'consequent': [ { 'type': 'EmptyStatement' } ] }";
	SwitchCase expected = SwitchCase.create(Collections.singletonList(EmptyStatement.create()));
	test(json, NodeType.SWITCH_CASE, expected);
    }

    @Test
    void testSwitchStatementParsed() {
	String json = "{ 'type': 'SwitchStatement', 'discriminant': { 'type': 'Identifier', 'name': 'x' }, 'cases': [ { 'type': 'SwitchCase', 'consequent': [ { 'type': 'EmptyStatement' } ] } ] }";
	SwitchStatement expected = SwitchStatement.create(Identifier.create("x"),
		Collections.singletonList(
			SwitchCase.create(Collections.singletonList(EmptyStatement.create()))));
	test(json, NodeType.SWITCH_STATEMENT, expected);
    }

    @Test
    void testThrowStatementParsed() {
	String json = "{ 'type': 'ThrowStatement', 'expression': { 'type': 'Identifier', 'name': 'a' } }";
	ThrowStatement expected = ThrowStatement.create(Identifier.create("a"));
	test(json, NodeType.THROW_STATEMENT, expected);
    }

    @Test
    void testCatchClauseParsed() {
	String json = "{ 'type': 'CatchClause', 'param': { 'type': 'Identifier', 'name': 'a' }, 'body': { 'type': 'BlockStatement', 'body': [ { 'type': 'EmptyStatement' } ] } }";
	CatchClause expected = CatchClause.create(Identifier.create("a"),
		BlockStatement.create(Collections.singletonList(EmptyStatement.create())));
	test(json, NodeType.CATCH_CLAUSE, expected);
    }

    @Test
    void testTryStatementParsed() {
	String json = " { 'type': 'TryStatement', 'block': { 'type': 'BlockStatement', 'body': [ { 'type': 'EmptyStatement' } ] } } ";
	TryStatement expected = TryStatement.create(
		BlockStatement.create(Collections.singletonList(EmptyStatement.create())), null,
		null);
	test(json, NodeType.TRY_STATEMENT, expected);
    }

    @Test
    void testTryStatementWithHandlerAndFinalizerParsed() {
	String json = " { 'type': 'TryStatement', 'block': { 'type': 'BlockStatement', 'body': [ ] }, 'handler': { 'type': 'CatchClause', 'param': { 'type': 'Identifier', 'name': 'a' }, 'body': { 'type': 'BlockStatement', 'body': [ ] } }, 'finalizer': { 'type': 'BlockStatement', 'body': [ ] } } ";
	TryStatement expected = TryStatement.create(BlockStatement.create(Collections.emptyList()),
		CatchClause.create(Identifier.create("a"),
			BlockStatement.create(Collections.emptyList())),
		BlockStatement.create(Collections.emptyList()));
	test(json, NodeType.TRY_STATEMENT, expected);
    }

    @Test
    void testWhileStatementParsed() {
	String json = " { 'type': 'WhileStatement', 'test': { 'type': 'Identifier', 'name': 'x' }, 'body': { 'type': 'EmptyStatement' } }";
	WhileStatement expected = WhileStatement.create(Identifier.create("x"),
		EmptyStatement.create());
	test(json, NodeType.WHILE_STATEMENT, expected);
    }

    @Test
    void testDoWhileStatementParsed() {
	String json = " { 'type': 'DoWhileStatement', 'test': { 'type': 'Identifier', 'name': 'x' }, 'body': { 'type': 'EmptyStatement' } }";
	DoWhileStatement expected = DoWhileStatement.create(Identifier.create("x"),
		EmptyStatement.create());
	test(json, NodeType.DO_WHILE_STATEMENT, expected);
    }

    @Test
    void testForStatementParsed() {
	String json = " { 'type': 'ForStatement', 'init': { 'type': 'Identifier', 'name': 'i' }, 'test': { 'type': 'Identifier', 'name': 'i' }, 'update': { 'type': 'Identifier', 'name': 'i'}, 'body': { 'type': 'EmptyStatement' } }";
	ForStatement expected = ForStatement.create(Identifier.create("i"), Identifier.create("i"),
		Identifier.create("i"), EmptyStatement.create());
	test(json, NodeType.FOR_STATEMENT, expected);
    }

    @Test
    void testEmptyForStatementParsed() {
	String json = " { 'type': 'ForStatement', 'body': { 'type': 'EmptyStatement' } }";
	ForStatement expected = ForStatement.create(null, null, null, EmptyStatement.create());
	test(json, NodeType.FOR_STATEMENT, expected);
    }

    @Test
    void testForInStatementParsed() {
	String json = " { 'type': 'ForInStatement', 'left': { 'type': 'Identifier', 'name': 'val' }, 'right': { 'type': 'Identifier', 'name': 'vals' }, 'body': { 'type': 'EmptyStatement' } }";
	ForInStatement expected = ForInStatement.create(Identifier.create("val"),
		Identifier.create("vals"), EmptyStatement.create());
	test(json, NodeType.FOR_IN_STATEMENT, expected);
    }

    @Test
    void testVariableDeclaratorParsed() {
	String json = " { 'type': 'VariableDeclarator', 'id': { 'type': 'Identifier', 'name': 'a' } }";
	VariableDeclarator expected = VariableDeclarator.create(Identifier.create("a"), null);
	test(json, NodeType.VARIABLE_DECLARATOR, expected);
    }

    @Test
    void testVariableDeclaratorWithInitParsed() {
	String json = " { 'type': 'VariableDeclarator', 'id': { 'type': 'Identifier', 'name': 'a' }, 'init': { 'type': 'Identifier', 'name': 'x' } }";
	VariableDeclarator expected = VariableDeclarator.create(Identifier.create("a"),
		Identifier.create("x"));
	test(json, NodeType.VARIABLE_DECLARATOR, expected);
    }

    @Test
    void testVariableDeclarationParsed() {
	String json = " { 'type': 'VariableDeclaration', 'declarations': [ { 'type': 'VariableDeclarator', 'id': { 'type': 'Identifier', 'name': 'a' } } ] }";
	VariableDeclaration expected = VariableDeclaration.create(
		Collections.singletonList(VariableDeclarator.create(Identifier.create("a"), null)));
	test(json, NodeType.VARIABLE_DECLARATION, expected);
    }

    @Test
    void testThisExpressionParsed() {
	String json = " { 'type': 'ThisExpression' }";
	ThisExpression expected = ThisExpression.create();
	test(json, NodeType.THIS_EXPRESSION, expected);
    }

}

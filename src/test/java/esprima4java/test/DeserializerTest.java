package esprima4java.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import esprima4java.Esprima2Java;
import esprima4java.ast.ArrayExpression;
import esprima4java.ast.ArrayPattern;
import esprima4java.ast.ArrowFunctionExpression;
import esprima4java.ast.AssignmentExpression;
import esprima4java.ast.AssignmentExpression.AssignmentOperator;
import esprima4java.ast.AssignmentPattern;
import esprima4java.ast.BinaryExpression;
import esprima4java.ast.BinaryExpression.BinaryOperator;
import esprima4java.ast.BlockStatement;
import esprima4java.ast.BreakStatement;
import esprima4java.ast.CallExpression;
import esprima4java.ast.CatchClause;
import esprima4java.ast.ClassBody;
import esprima4java.ast.ClassDeclaration;
import esprima4java.ast.ClassExpression;
import esprima4java.ast.ConditionalExpression;
import esprima4java.ast.ContinueStatement;
import esprima4java.ast.DoWhileStatement;
import esprima4java.ast.EmptyStatement;
import esprima4java.ast.ExpressionStatement;
import esprima4java.ast.ForInStatement;
import esprima4java.ast.ForOfStatement;
import esprima4java.ast.ForStatement;
import esprima4java.ast.FunctionExpression;
import esprima4java.ast.Identifier;
import esprima4java.ast.IfStatement;
import esprima4java.ast.LabeledStatement;
import esprima4java.ast.Literal;
import esprima4java.ast.LogicalExpression;
import esprima4java.ast.LogicalExpression.LogicalOperator;
import esprima4java.ast.MemberExpression;
import esprima4java.ast.MethodDefinition;
import esprima4java.ast.NewExpression;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;
import esprima4java.ast.ObjectExpression;
import esprima4java.ast.ObjectPattern;
import esprima4java.ast.Program;
import esprima4java.ast.Program.SourceType;
import esprima4java.ast.Property;
import esprima4java.ast.Property.Kind;
import esprima4java.ast.RegExpLiteral;
import esprima4java.ast.RestElement;
import esprima4java.ast.ReturnStatement;
import esprima4java.ast.SequenceExpression;
import esprima4java.ast.SpreadElement;
import esprima4java.ast.Super;
import esprima4java.ast.SwitchCase;
import esprima4java.ast.SwitchStatement;
import esprima4java.ast.TaggedTemplateExpression;
import esprima4java.ast.TemplateElement;
import esprima4java.ast.TemplateLiteral;
import esprima4java.ast.TemplateValue;
import esprima4java.ast.ThisExpression;
import esprima4java.ast.ThrowStatement;
import esprima4java.ast.TryStatement;
import esprima4java.ast.UnaryExpression;
import esprima4java.ast.UnaryExpression.UnaryOperator;
import esprima4java.ast.UpdateExpression;
import esprima4java.ast.UpdateExpression.UpdateOperator;
import esprima4java.ast.VariableDeclaration;
import esprima4java.ast.VariableDeclarator;
import esprima4java.ast.WhileStatement;
import esprima4java.ast.WithStatement;
import esprima4java.ast.YieldExpression;
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
	String json = "{ 'type': 'Program', 'sourceType': 'script', 'body': [ { 'type': 'EmptyStatement' } ] }";
	Program expected = Program.create(SourceType.SCRIPT,
		Collections.singletonList(EmptyStatement.create()));
	test(json, NodeType.PROGRAM, expected);
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
    void testForOfStatementParsed() {
	String json = " { 'type': 'ForOfStatement', 'left': { 'type': 'Identifier', 'name': 'val' }, 'right': { 'type': 'Identifier', 'name': 'vals' }, 'body': { 'type': 'EmptyStatement' } }";
	ForOfStatement expected = ForOfStatement.create(Identifier.create("val"),
		Identifier.create("vals"), EmptyStatement.create());
	test(json, NodeType.FOR_OF_STATEMENT, expected);
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
	String json = " { 'type': 'VariableDeclaration', 'kind': 'var', 'declarations': [ { 'type': 'VariableDeclarator', 'id': { 'type': 'Identifier', 'name': 'a' } } ] }";
	VariableDeclaration expected = VariableDeclaration.create(
		Collections.singletonList(VariableDeclarator.create(Identifier.create("a"), null)),
		VariableDeclaration.Kind.VAR);
	test(json, NodeType.VARIABLE_DECLARATION, expected);
    }

    @Test
    void testSuperParsed() {
	String json = " { 'type': 'Super' }";
	Super expected = Super.create();
	test(json, NodeType.SUPER, expected);
    }

    @Test
    void testSpreadElementParsed() {
	String json = "{ 'type': 'SpreadElement', 'argument': { 'type': 'Identifier', 'name': 'a' } }";
	SpreadElement expected = SpreadElement.create(Identifier.create("a"));
	test(json, NodeType.SPREAD_ELEMENT, expected);
    }

    @Test
    void testThisExpressionParsed() {
	String json = " { 'type': 'ThisExpression' }";
	ThisExpression expected = ThisExpression.create();
	test(json, NodeType.THIS_EXPRESSION, expected);
    }

    @Test
    void testArrayExpressionParsed() {
	String json = " { 'type': 'ArrayExpression', 'elements': [ { 'type': 'Identifier', 'name': 'a' } ] }";
	ArrayExpression expected = ArrayExpression
		.create(Collections.singletonList(Identifier.create("a")));
	test(json, NodeType.ARRAY_EXPRESSION, expected);
    }

    @Test
    void testObjectExpressionParsed() {
	String json = " { 'type': 'ObjectExpression', 'properties': [ { 'type': 'Property', key: { 'type': 'Identifier', 'name': 'a' }, 'value': { 'type': 'Identifier', 'name': 'b' }, 'kind': 'init', 'method': false, 'shorthand': false, 'computed': false } ] }";
	ObjectExpression expected = ObjectExpression
		.create(Collections.singletonList(Property.create(Identifier.create("a"),
			Identifier.create("b"), Kind.INIT, false, false, false)));
	test(json, NodeType.OBJECT_EXPRESSION, expected);
    }

    @Test
    void testPropertyParsed() {
	String json = " { 'type': 'Property', key: { 'type': 'Identifier', 'name': 'a' }, 'value': { 'type': 'Identifier', 'name': 'b' }, 'kind': 'init', 'method': false, 'shorthand': false, 'computed': false }";
	Property expected = Property.create(Identifier.create("a"), Identifier.create("b"),
		Kind.INIT, false, false, false);
	test(json, NodeType.PROPERTY, expected);
    }

    @Test
    void testFunctionExpressionParsed() {
	String json = "{ 'type': 'FunctionExpression', 'generator': false, 'id': { 'type': 'Identifier', 'name': 'foo' }, params: [ { 'type': 'Identifier', 'name': 'a' } ], body: { 'type': 'BlockStatement', 'body': [ ] } }";
	FunctionExpression expected = FunctionExpression.create(false, Identifier.create("foo"),
		Collections.singletonList(Identifier.create("a")),
		BlockStatement.create(Collections.emptyList()));
	test(json, NodeType.FUNCTION_EXPRESSION, expected);
    }

    @Test
    void testAnonFunctionExpressionParsed() {
	String json = "{ 'type': 'FunctionExpression', 'generator': false, params: [ { 'type': 'Identifier', 'name': 'a' } ], body: { 'type': 'BlockStatement', 'body': [ ] } }";
	FunctionExpression expected = FunctionExpression.create(false, null,
		Collections.singletonList(Identifier.create("a")),
		BlockStatement.create(Collections.emptyList()));
	test(json, NodeType.FUNCTION_EXPRESSION, expected);
    }

    @Test
    void testArrowFunctionExpressionParsed() {
	String json = "{ 'type': 'ArrowFunctionExpression', 'generator': false, params: [ { 'type': 'Identifier', 'name': 'a' } ], body: { 'type': 'BlockStatement', 'body': [ ] }, 'expression': false }";
	ArrowFunctionExpression expected = ArrowFunctionExpression.create(false,
		Collections.singletonList(Identifier.create("a")),
		BlockStatement.create(Collections.emptyList()), false);
	test(json, NodeType.ARROW_FUNCTION_EXPRESSION, expected);
    }

    @Test
    void testExpressionArrowFunctionExpressionParsed() {
	String json = "{ 'type': 'ArrowFunctionExpression', 'generator': false, params: [ { 'type': 'Identifier', 'name': 'a' } ], body: { 'type': 'BlockStatement', 'body': [ ] }, 'expression': true }";
	ArrowFunctionExpression expected = ArrowFunctionExpression.create(false,
		Collections.singletonList(Identifier.create("a")),
		BlockStatement.create(Collections.emptyList()), true);
	test(json, NodeType.ARROW_FUNCTION_EXPRESSION, expected);
    }

    @Test
    void testUnaryExpressionParsed() {
	String json = "{ 'type': 'UnaryExpression', 'operator': '+', 'prefix': true, 'argument': { 'type': 'Identifier', 'name': 'a' } }";
	UnaryExpression expected = UnaryExpression.create(UnaryOperator.ADD, true,
		Identifier.create("a"));
	test(json, NodeType.UNARY_EXPRESSION, expected);
    }

    @Test
    void testUpdateExpressionParsed() {
	String json = "{ 'type': 'UpdateExpression', 'operator': '++', 'prefix': true, 'argument': { 'type': 'Identifier', 'name': 'a' } }";
	UpdateExpression expected = UpdateExpression.create(UpdateOperator.INCREMENT, true,
		Identifier.create("a"));
	test(json, NodeType.UPDATE_EXPRESSION, expected);
    }

    @Test
    void testBinaryExpressionParsed() {
	String json = "{ 'type': 'BinaryExpression', 'operator': '+', 'left': { 'type': 'Identifier', 'name': 'a' }, 'right': { 'type': 'Identifier', 'name': 'b' } }";
	BinaryExpression expected = BinaryExpression.create(BinaryOperator.ADD,
		Identifier.create("a"), Identifier.create("b"));
	test(json, NodeType.BINARY_EXPRESSION, expected);
    }

    @Test
    void testAssignmentExpressionParsed() {
	String json = "{ 'type': 'AssignmentExpression', 'operator': '+=', 'left': { 'type': 'Identifier', 'name': 'a' }, 'right': { 'type': 'Identifier', 'name': 'b' } }";
	AssignmentExpression expected = AssignmentExpression.create(AssignmentOperator.ASSIGN_ADD,
		Identifier.create("a"), Identifier.create("b"));
	test(json, NodeType.ASSIGNMENT_EXPRESSION, expected);
    }

    @Test
    void testLogicalExpressionParsed() {
	String json = "{ 'type': 'LogicalExpression', 'operator': '||', 'left': { 'type': 'Identifier', 'name': 'a' }, 'right': { 'type': 'Identifier', 'name': 'b' } }";
	LogicalExpression expected = LogicalExpression.create(LogicalOperator.OR,
		Identifier.create("a"), Identifier.create("b"));
	test(json, NodeType.LOGICAL_EXPRESSION, expected);
    }

    @Test
    void testMemberExpressionParsed() {
	String json = "{ 'type': 'MemberExpression', 'object': { 'type': 'Identifier', 'name': 'a' }, 'property': { 'type': 'Identifier', 'name': 'b' }, 'computed': false }";
	MemberExpression expected = MemberExpression.create(Identifier.create("a"),
		Identifier.create("b"), false);
	test(json, NodeType.MEMBER_EXPRESSION, expected);
    }

    @Test
    void testConditionalExpressionParsed() {
	String json = "{ 'type': 'ConditionalExpression', 'test': { 'type': 'Identifier', 'name': 'a' }, 'alternate': { 'type': 'Identifier', 'name': 'b' }, 'consequent': { 'type': 'Identifier', 'name': 'c' } }";
	ConditionalExpression expected = ConditionalExpression.create(Identifier.create("a"),
		Identifier.create("b"), Identifier.create("c"));
	test(json, NodeType.CONDITIONAL_EXPRESSION, expected);
    }

    @Test
    void testCallExpressionParsed() {
	String json = "{ 'type': 'CallExpression', 'callee': { 'type': 'Identifier', 'name': 'foo' }, 'arguments': [ { 'type': 'Identifier', 'name': 'a' } ] }";
	CallExpression expected = CallExpression.create(Identifier.create("foo"),
		Collections.singletonList(Identifier.create("a")));
	test(json, NodeType.CALL_EXPRESSION, expected);
    }

    @Test
    void testNewExpressionParsed() {
	String json = "{ 'type': 'NewExpression', 'callee': { 'type': 'Identifier', 'name': 'foo' }, 'arguments': [ { 'type': 'Identifier', 'name': 'a' } ] }";
	NewExpression expected = NewExpression.create(Identifier.create("foo"),
		Collections.singletonList(Identifier.create("a")));
	test(json, NodeType.NEW_EXPRESSION, expected);
    }

    @Test
    void testSequenceExpressionParsed() {
	String json = "{ 'type': 'SequenceExpression', 'expressions': [ { 'type': 'Identifier', 'name': 'a' } ] }";
	SequenceExpression expected = SequenceExpression
		.create(Collections.singletonList(Identifier.create("a")));
	test(json, NodeType.SEQUENCE_EXPRESSION, expected);
    }

    @Test
    void testYieldExpressionParsed() {
	String json = "{ 'type': 'YieldExpression', 'delegate': false }";
	YieldExpression expected = YieldExpression.create(null, false);
	test(json, NodeType.YIELD_EXPRESSION, expected);
    }

    @Test
    void testYieldExpressionWithArgumentParsed() {
	String json = "{ 'type': 'YieldExpression', 'argument': { 'type': 'Identifier', 'name': 'a' }, 'delegate': false }";
	YieldExpression expected = YieldExpression.create(Identifier.create("a"), false);
	test(json, NodeType.YIELD_EXPRESSION, expected);
    }

    @Test
    void testTemplateElementParsed() {
	String json = "{ 'type': 'TemplateElement', 'tail': false, 'value': { 'cooked': 'x', 'raw': 'x' } }";
	TemplateElement expected = TemplateElement.create(false, TemplateValue.create("x", "x"));
	test(json, NodeType.TEMPLATE_ELEMENT, expected);
    }

    @Test
    void testTemplateLiteralParsed() {
	String json = "{ 'type': 'TemplateLiteral', quasis: [ { 'type': 'TemplateElement', 'tail': false, 'value': { 'cooked': 'x', 'raw': 'x' } } ], expressions: [ { 'type': 'Identifier', 'name': 'a' } ] }";
	TemplateLiteral expected = TemplateLiteral.create(
		Collections.singletonList(
			TemplateElement.create(false, TemplateValue.create("x", "x"))),
		Collections.singletonList(Identifier.create("a")));
	test(json, NodeType.TEMPLATE_LITERAL, expected);
    }

    @Test
    void testTaggedTemplateExpression() {
	String json = "{ 'type': 'TaggedTemplateExpression', 'tag': { 'type': 'Identifier', 'name': 'foo' }, 'quasi': { 'type': 'TemplateLiteral', quasis: [ ], expressions: [ ] } }";
	TaggedTemplateExpression expected = TaggedTemplateExpression.create(
		Identifier.create("foo"),
		TemplateLiteral.create(Collections.emptyList(), Collections.emptyList()));
	test(json, NodeType.TAGGED_TEMPLATE_EXPRESSION, expected);
    }

    @Test
    void testObjectPattern() {
	String json = "{ 'type': 'ObjectPattern', 'properties': [ { 'type': 'Property', 'computed': false, 'key': { 'type': 'Identifier', 'name': 'a' }, 'value': { 'type': 'Identifier', 'name': 'a' }, 'kind': 'init', 'method': false, 'shorthand': true } ] }";
	ObjectPattern expected = ObjectPattern
		.create(Collections.singletonList(Property.create(Identifier.create("a"),
			Identifier.create("a"), Kind.INIT, false, true, false)));
	test(json, NodeType.OBJECT_PATTERN, expected);
    }

    @Test
    void testArrayPattern() {
	String json = "{ 'type': 'ArrayPattern', 'elements': [ { 'type': 'Identifier', 'name': 'a' } ] }";
	ArrayPattern expected = ArrayPattern
		.create(Collections.singletonList(Identifier.create("a")));
	test(json, NodeType.ARRAY_PATTERN, expected);
    }

    @Test
    void testRestElement() {
	String json = "{ 'type': 'RestElement', 'argument': { 'type': 'Identifier', 'name': 'a' } }";
	RestElement expected = RestElement.create(Identifier.create("a"));
	test(json, NodeType.REST_ELEMENT, expected);
    }

    @Test
    void testAssignmentPattern() {
	String json = "{ 'type': 'AssignmentPattern', 'left': { 'type': 'ArrayPattern', 'elements': [ { 'type': 'Identifier', 'name': 'a' } ] }, 'right': { 'type': 'Identifier', 'name': 'b' } }";
	AssignmentPattern expected = AssignmentPattern.create(
		ArrayPattern.create(Collections.singletonList(Identifier.create("a"))),
		Identifier.create("b"));
	test(json, NodeType.ASSIGNMENT_PATTERN, expected);
    }

    @Test
    void testMethodDefinition() {
	String json = "{ 'type': 'MethodDefinition', 'key': { 'type': 'Identifier', 'name': 'foo' }, 'value': { 'type': 'FunctionExpression', 'generator': false, params: [ ], body: { 'type': 'BlockStatement', 'body': [ ] } }, 'kind': 'method', 'computed': false, 'static': false }";
	MethodDefinition expected = MethodDefinition.create(Identifier.create("foo"),
		FunctionExpression.create(false, null, Collections.emptyList(),
			BlockStatement.create(Collections.emptyList())),
		MethodDefinition.Kind.METHOD, false, false);
	test(json, NodeType.METHOD_DEFINITION, expected);
    }

    @Test
    void testClassBody() {
	String json = "{ 'type': 'ClassBody', 'body': [ { 'type': 'MethodDefinition', 'key': { 'type': 'Identifier', 'name': 'foo' }, 'value': { 'type': 'FunctionExpression', 'generator': false, params: [ ], body: { 'type': 'BlockStatement', 'body': [ ] } }, 'kind': 'method', 'computed': false, 'static': false } ] }";
	ClassBody expected = ClassBody
		.create(Collections.singletonList(MethodDefinition.create(Identifier.create("foo"),
			FunctionExpression.create(false, null, Collections.emptyList(),
				BlockStatement.create(Collections.emptyList())),
			MethodDefinition.Kind.METHOD, false, false)));
	test(json, NodeType.CLASS_BODY, expected);
    }

    @Test
    void testAnonClassExpression() {
	String json = "{ 'type': 'ClassExpression', 'body': { 'type': 'ClassBody', 'body': [ ] } }";
	ClassExpression expected = ClassExpression.create(null, null,
		ClassBody.create(Collections.emptyList()));
	test(json, NodeType.CLASS_EXPRESSION, expected);
    }

    @Test
    void testClassDefinition() {
	String json = "{ 'type': 'ClassDeclaration', 'id': { 'type': 'Identifier', 'name': 'Foo' }, 'superClass': { 'type': 'Identifier', 'name': 'Bar' }, 'body': { 'type': 'ClassBody', 'body': [ ] } }";
	ClassDeclaration expected = ClassDeclaration.create(Identifier.create("Foo"),
		Identifier.create("Bar"), ClassBody.create(Collections.emptyList()));
	test(json, NodeType.CLASS_DECLARATION, expected);
    }
}

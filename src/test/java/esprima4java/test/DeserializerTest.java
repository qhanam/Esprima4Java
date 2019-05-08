package esprima4java.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import esprima4java.Esprima2Java;
import esprima4java.addons.SourceLocation;
import esprima4java.ast.ArrayExpression;
import esprima4java.ast.ArrayPattern;
import esprima4java.ast.ArrowFunctionExpression;
import esprima4java.ast.AssignmentExpression;
import esprima4java.ast.AssignmentExpression.AssignmentOperator;
import esprima4java.ast.AssignmentPattern;
import esprima4java.ast.AwaitExpression;
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
import esprima4java.ast.MetaProperty;
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
    void testLocation() {
	String json = "{ 'type': 'Identifier', 'name': 'foo', 'range': [36, 39], 'loc': { 'start': { 'line': 4, 'column': 0 }, 'end': { 'line': 4, 'column': 3 } } }";
	Identifier expected = Identifier.create("foo");
	expected.setSourceLocation(SourceLocation.create(4, 4, 0, 3, 36, 39));
	Node actual = test(json, NodeType.IDENTIFIER, expected);
	assertEquals(expected.sourceLocation(), actual.sourceLocation());
    }

    @Test
    void testIdentifier() {
	String json = "{ 'type': 'Identifier', 'name': 'foo' }";
	Identifier expected = Identifier.create("foo");
	test(json, NodeType.IDENTIFIER, expected);
    }

    @Test
    void testNumberLiteral() {
	String json = "{ 'type': 'Literal', 'value': 1, 'raw': '1' }";
	Literal expected = Literal.createNumber(1, "1");
	test(json, NodeType.LITERAL, expected);
    }

    @Test
    void testStringLiteral() {
	String json = "{ 'type': 'Literal', 'value': '0', 'raw': '\"0\"' }";
	Literal expected = Literal.createString("0", "\"0\"");
	test(json, NodeType.LITERAL, expected);
    }

    @Test
    void testBoolLiteral() {
	String json = "{ 'type': 'Literal', 'value': true, 'raw': 'true' }";
	Literal expected = Literal.createBoolean(true, "true");
	test(json, NodeType.LITERAL, expected);
    }

    @Test
    void testRegExLiteral() {
	String json = "{ 'type': 'Literal', 'value': '/.*/g', 'raw': '/.*/g', 'regex': { 'pattern': '.*', 'flags': 'g' } }";
	Literal expected = Literal.createRegEx(RegExpLiteral.create(".*", "g"), "/.*/g");
	test(json, NodeType.LITERAL, expected);
    }

    @Test
    void testNullLiteral() {
	String json = "{ 'type': 'Literal', 'value': null, 'raw': 'null' }";
	Literal expected = Literal.createNull("null");
	test(json, NodeType.LITERAL, expected);
    }

    @Test
    void testEmptyStatement() {
	String json = "{ 'type': 'EmptyStatement' }";
	EmptyStatement expected = EmptyStatement.create();
	test(json, NodeType.EMPTY_STATEMENT, expected);
    }

    @Test
    void testExpressionStatement() {
	String json = "{ 'type': 'ExpressionStatement', 'expression': { 'type': 'Identifier', 'name': 'a' } }";
	ExpressionStatement expected = ExpressionStatement.create(Identifier.create("a"), null);
	test(json, NodeType.EXPRESSION_STATEMENT, expected);
    }

    @Test
    void testDirective() {
	String json = "{ 'type': 'ExpressionStatement', 'expression': { 'type': 'Literal', 'value': 'use strict', 'raw': '\"use strict\"' }, 'directive': 'use strict' }";
	ExpressionStatement expected = ExpressionStatement
		.create(Literal.createString("use strict", "\"use strict\""), "use strict");
	test(json, NodeType.EXPRESSION_STATEMENT, expected);
    }

    @Test
    void testBlockStatement() {
	String json = "{ 'type': 'BlockStatement', 'body': [ { 'type': 'EmptyStatement' } ] }";
	BlockStatement expected = BlockStatement
		.create(Collections.singletonList(EmptyStatement.create()));
	test(json, NodeType.BLOCK_STATEMENT, expected);
    }

    @Test
    void testProgram() {
	String json = "{ 'type': 'Program', 'sourceType': 'script', 'body': [ { 'type': 'EmptyStatement' } ] }";
	Program expected = Program.create(SourceType.SCRIPT,
		Collections.singletonList(EmptyStatement.create()));
	test(json, NodeType.PROGRAM, expected);
    }

    @Test
    void testWithStatement() {
	String json = "{ 'type': 'WithStatement', 'object': { 'type': 'Identifier', 'name': 'x' }, 'body': { 'type': 'EmptyStatement' } }";
	WithStatement expected = WithStatement.create(Identifier.create("x"),
		EmptyStatement.create());
	test(json, NodeType.WITH_STATEMENT, expected);
    }

    @Test
    void testReturnStatement() {
	String json = "{ 'type': 'ReturnStatement' }";
	ReturnStatement expected = ReturnStatement.create();
	test(json, NodeType.RETURN_STATEMENT, expected);
    }

    @Test
    void testReturnStatementWithArgument() {
	String json = "{ 'type': 'ReturnStatement', 'argument': { 'type': 'Identifier', 'name': 'a' } }";
	ReturnStatement expected = ReturnStatement.create(Identifier.create("a"));
	test(json, NodeType.RETURN_STATEMENT, expected);
    }

    @Test
    void testLabeledStatement() {
	String json = "{ 'type': 'LabeledStatement', 'label': { 'type': 'Identifier', 'name': 'a' }, 'body': { 'type': 'EmptyStatement' } }";
	LabeledStatement expected = LabeledStatement.create(Identifier.create("a"),
		EmptyStatement.create());
	test(json, NodeType.LABELED_STATEMENT, expected);
    }

    @Test
    void testBreakStatement() {
	String json = "{ 'type': 'BreakStatement' }";
	BreakStatement expected = BreakStatement.create(null);
	test(json, NodeType.BREAK_STATEMENT, expected);
    }

    @Test
    void testBreakStatementWithLabel() {
	String json = "{ 'type': 'BreakStatement', 'label': { 'type': 'Identifier', 'name': 'a' } }";
	BreakStatement expected = BreakStatement.create(Identifier.create("a"));
	test(json, NodeType.BREAK_STATEMENT, expected);
    }

    @Test
    void testContinueStatement() {
	String json = "{ 'type': 'ContinueStatement' }";
	ContinueStatement expected = ContinueStatement.create(null);
	test(json, NodeType.CONTINUE_STATEMENT, expected);
    }

    @Test
    void testContinueStatementWithLabel() {
	String json = "{ 'type': 'ContinueStatement', 'label': { 'type': 'Identifier', 'name': 'a' } }";
	ContinueStatement expected = ContinueStatement.create(Identifier.create("a"));
	test(json, NodeType.CONTINUE_STATEMENT, expected);
    }

    @Test
    void testIfStatement() {
	String json = "{ 'type': 'IfStatement', 'test': { 'type': 'Identifier', 'name': 'x' }, 'consequent': { 'type': 'EmptyStatement' }, 'alternate': null }";
	IfStatement expected = IfStatement.create(Identifier.create("x"), EmptyStatement.create(),
		null);
	test(json, NodeType.IF_STATEMENT, expected);
    }

    @Test
    void testIfStatementWithAlternate() {
	String json = "{ 'type': 'IfStatement', 'test': { 'type': 'Identifier', 'name': 'x' }, 'consequent': { 'type': 'EmptyStatement' }, 'alternate': { 'type': 'EmptyStatement' } }";
	IfStatement expected = IfStatement.create(Identifier.create("x"), EmptyStatement.create(),
		EmptyStatement.create());
	test(json, NodeType.IF_STATEMENT, expected);
    }

    @Test
    void testSwitchCase() {
	String json = "{ 'type': 'SwitchCase', 'test': { 'type': 'Identifier', 'name': 'x' }, 'consequent': [ { 'type': 'EmptyStatement' } ] }";
	SwitchCase expected = SwitchCase.create(Identifier.create("x"),
		Collections.singletonList(EmptyStatement.create()));
	test(json, NodeType.SWITCH_CASE, expected);
    }

    @Test
    void testDefaultSwitchCase() {
	String json = "{ 'type': 'SwitchCase', 'consequent': [ { 'type': 'EmptyStatement' } ] }";
	SwitchCase expected = SwitchCase.create(Collections.singletonList(EmptyStatement.create()));
	test(json, NodeType.SWITCH_CASE, expected);
    }

    @Test
    void testSwitchStatement() {
	String json = "{ 'type': 'SwitchStatement', 'discriminant': { 'type': 'Identifier', 'name': 'x' }, 'cases': [ { 'type': 'SwitchCase', 'consequent': [ { 'type': 'EmptyStatement' } ] } ] }";
	SwitchStatement expected = SwitchStatement.create(Identifier.create("x"),
		Collections.singletonList(
			SwitchCase.create(Collections.singletonList(EmptyStatement.create()))));
	test(json, NodeType.SWITCH_STATEMENT, expected);
    }

    @Test
    void testThrowStatement() {
	String json = "{ 'type': 'ThrowStatement', 'expression': { 'type': 'Identifier', 'name': 'a' } }";
	ThrowStatement expected = ThrowStatement.create(Identifier.create("a"));
	test(json, NodeType.THROW_STATEMENT, expected);
    }

    @Test
    void testCatchClause() {
	String json = "{ 'type': 'CatchClause', 'param': { 'type': 'Identifier', 'name': 'a' }, 'body': { 'type': 'BlockStatement', 'body': [ { 'type': 'EmptyStatement' } ] } }";
	CatchClause expected = CatchClause.create(Identifier.create("a"),
		BlockStatement.create(Collections.singletonList(EmptyStatement.create())));
	test(json, NodeType.CATCH_CLAUSE, expected);
    }

    @Test
    void testCatchClauseWithoutParam() {
	String json = "{ 'type': 'CatchClause', 'param': null, 'body': { 'type': 'BlockStatement', 'body': [ { 'type': 'EmptyStatement' } ] } }";
	CatchClause expected = CatchClause.create(null,
		BlockStatement.create(Collections.singletonList(EmptyStatement.create())));
	test(json, NodeType.CATCH_CLAUSE, expected);
    }

    @Test
    void testTryStatement() {
	String json = " { 'type': 'TryStatement', 'block': { 'type': 'BlockStatement', 'body': [ { 'type': 'EmptyStatement' } ] }, 'handler': null, 'finalizer': null } ";
	TryStatement expected = TryStatement.create(
		BlockStatement.create(Collections.singletonList(EmptyStatement.create())), null,
		null);
	test(json, NodeType.TRY_STATEMENT, expected);
    }

    @Test
    void testTryStatementWithHandlerAndFinalizer() {
	String json = " { 'type': 'TryStatement', 'block': { 'type': 'BlockStatement', 'body': [ ] }, 'handler': { 'type': 'CatchClause', 'param': { 'type': 'Identifier', 'name': 'a' }, 'body': { 'type': 'BlockStatement', 'body': [ ] } }, 'finalizer': { 'type': 'BlockStatement', 'body': [ ] } } ";
	TryStatement expected = TryStatement.create(BlockStatement.create(Collections.emptyList()),
		CatchClause.create(Identifier.create("a"),
			BlockStatement.create(Collections.emptyList())),
		BlockStatement.create(Collections.emptyList()));
	test(json, NodeType.TRY_STATEMENT, expected);
    }

    @Test
    void testWhileStatement() {
	String json = " { 'type': 'WhileStatement', 'test': { 'type': 'Identifier', 'name': 'x' }, 'body': { 'type': 'EmptyStatement' } }";
	WhileStatement expected = WhileStatement.create(Identifier.create("x"),
		EmptyStatement.create());
	test(json, NodeType.WHILE_STATEMENT, expected);
    }

    @Test
    void testDoWhileStatement() {
	String json = " { 'type': 'DoWhileStatement', 'test': { 'type': 'Identifier', 'name': 'x' }, 'body': { 'type': 'EmptyStatement' } }";
	DoWhileStatement expected = DoWhileStatement.create(Identifier.create("x"),
		EmptyStatement.create());
	test(json, NodeType.DO_WHILE_STATEMENT, expected);
    }

    @Test
    void testForStatement() {
	String json = " { 'type': 'ForStatement', 'init': { 'type': 'Identifier', 'name': 'i' }, 'test': { 'type': 'Identifier', 'name': 'i' }, 'update': { 'type': 'Identifier', 'name': 'i'}, 'body': { 'type': 'EmptyStatement' } }";
	ForStatement expected = ForStatement.create(Identifier.create("i"), Identifier.create("i"),
		Identifier.create("i"), EmptyStatement.create());
	test(json, NodeType.FOR_STATEMENT, expected);
    }

    @Test
    void testEmptyForStatement() {
	String json = " { 'type': 'ForStatement', 'init': null, 'test': null, 'update': null, 'body': { 'type': 'EmptyStatement' } }";
	ForStatement expected = ForStatement.create(null, null, null, EmptyStatement.create());
	test(json, NodeType.FOR_STATEMENT, expected);
    }

    @Test
    void testForInStatement() {
	String json = " { 'type': 'ForInStatement', 'left': { 'type': 'Identifier', 'name': 'val' }, 'right': { 'type': 'Identifier', 'name': 'vals' }, 'body': { 'type': 'EmptyStatement' } }";
	ForInStatement expected = ForInStatement.create(Identifier.create("val"),
		Identifier.create("vals"), EmptyStatement.create());
	test(json, NodeType.FOR_IN_STATEMENT, expected);
    }

    @Test
    void testForOfStatement() {
	String json = " { 'type': 'ForOfStatement', 'left': { 'type': 'Identifier', 'name': 'val' }, 'right': { 'type': 'Identifier', 'name': 'vals' }, 'body': { 'type': 'EmptyStatement' }, 'await': false }";
	ForOfStatement expected = ForOfStatement.create(Identifier.create("val"),
		Identifier.create("vals"), EmptyStatement.create(), false);
	test(json, NodeType.FOR_OF_STATEMENT, expected);
    }

    @Test
    void testVariableDeclarator() {
	String json = " { 'type': 'VariableDeclarator', 'id': { 'type': 'Identifier', 'name': 'a' }, 'init': null }";
	VariableDeclarator expected = VariableDeclarator.create(Identifier.create("a"), null);
	test(json, NodeType.VARIABLE_DECLARATOR, expected);
    }

    @Test
    void testVariableDeclaratorWithInit() {
	String json = " { 'type': 'VariableDeclarator', 'id': { 'type': 'Identifier', 'name': 'a' }, 'init': { 'type': 'Identifier', 'name': 'x' } }";
	VariableDeclarator expected = VariableDeclarator.create(Identifier.create("a"),
		Identifier.create("x"));
	test(json, NodeType.VARIABLE_DECLARATOR, expected);
    }

    @Test
    void testVariableDeclaration() {
	String json = " { 'type': 'VariableDeclaration', 'kind': 'var', 'declarations': [ { 'type': 'VariableDeclarator', 'id': { 'type': 'Identifier', 'name': 'a' }, 'init': null } ] }";
	VariableDeclaration expected = VariableDeclaration.create(
		Collections.singletonList(VariableDeclarator.create(Identifier.create("a"), null)),
		VariableDeclaration.Kind.VAR);
	test(json, NodeType.VARIABLE_DECLARATION, expected);
    }

    @Test
    void testSuper() {
	String json = " { 'type': 'Super' }";
	Super expected = Super.create();
	test(json, NodeType.SUPER, expected);
    }

    @Test
    void testSpreadElement() {
	String json = "{ 'type': 'SpreadElement', 'argument': { 'type': 'Identifier', 'name': 'a' } }";
	SpreadElement expected = SpreadElement.create(Identifier.create("a"));
	test(json, NodeType.SPREAD_ELEMENT, expected);
    }

    @Test
    void testThisExpression() {
	String json = " { 'type': 'ThisExpression' }";
	ThisExpression expected = ThisExpression.create();
	test(json, NodeType.THIS_EXPRESSION, expected);
    }

    @Test
    void testArrayExpression() {
	String json = " { 'type': 'ArrayExpression', 'elements': [ { 'type': 'Identifier', 'name': 'a' } ] }";
	ArrayExpression expected = ArrayExpression
		.create(Collections.singletonList(Identifier.create("a")));
	test(json, NodeType.ARRAY_EXPRESSION, expected);
    }

    @Test
    void testObjectExpression() {
	String json = " { 'type': 'ObjectExpression', 'properties': [ { 'type': 'Property', key: { 'type': 'Identifier', 'name': 'a' }, 'value': { 'type': 'Identifier', 'name': 'b' }, 'kind': 'init', 'method': false, 'shorthand': false, 'computed': false } ] }";
	ObjectExpression expected = ObjectExpression
		.create(Collections.singletonList(Property.create(Identifier.create("a"),
			Identifier.create("b"), Kind.INIT, false, false, false)));
	test(json, NodeType.OBJECT_EXPRESSION, expected);
    }

    @Test
    void testObjectExpressionWithSpreadElement() {
	String json = " { 'type': 'ObjectExpression', 'properties': [ { 'type': 'SpreadElement', 'argument': { 'type': 'Identifier', 'name': 'a' } } ] }";
	ObjectExpression expected = ObjectExpression
		.create(Collections.singletonList(SpreadElement.create(Identifier.create("a"))));
	test(json, NodeType.OBJECT_EXPRESSION, expected);
    }

    @Test
    void testProperty() {
	String json = " { 'type': 'Property', key: { 'type': 'Identifier', 'name': 'a' }, 'value': { 'type': 'Identifier', 'name': 'b' }, 'kind': 'init', 'method': false, 'shorthand': false, 'computed': false }";
	Property expected = Property.create(Identifier.create("a"), Identifier.create("b"),
		Kind.INIT, false, false, false);
	test(json, NodeType.PROPERTY, expected);
    }

    @Test
    void testFunctionExpression() {
	String json = "{ 'type': 'FunctionExpression', 'generator': false, 'async': false, 'id': { 'type': 'Identifier', 'name': 'foo' }, params: [ { 'type': 'Identifier', 'name': 'a' } ], body: { 'type': 'BlockStatement', 'body': [ ] } }";
	FunctionExpression expected = FunctionExpression.create(false, false,
		Identifier.create("foo"), Collections.singletonList(Identifier.create("a")),
		BlockStatement.create(Collections.emptyList()));
	test(json, NodeType.FUNCTION_EXPRESSION, expected);
    }

    @Test
    void testAnonFunctionExpression() {
	String json = "{ 'type': 'FunctionExpression', 'generator': false, 'async': false, 'id': null, params: [ { 'type': 'Identifier', 'name': 'a' } ], body: { 'type': 'BlockStatement', 'body': [ ] } }";
	FunctionExpression expected = FunctionExpression.create(false, false, null,
		Collections.singletonList(Identifier.create("a")),
		BlockStatement.create(Collections.emptyList()));
	test(json, NodeType.FUNCTION_EXPRESSION, expected);
    }

    @Test
    void testArrowFunctionExpression() {
	String json = "{ 'type': 'ArrowFunctionExpression', 'generator': false, 'async': false, params: [ { 'type': 'Identifier', 'name': 'a' } ], body: { 'type': 'BlockStatement', 'body': [ ] }, 'expression': false }";
	ArrowFunctionExpression expected = ArrowFunctionExpression.create(false, false,
		Collections.singletonList(Identifier.create("a")),
		BlockStatement.create(Collections.emptyList()), false);
	test(json, NodeType.ARROW_FUNCTION_EXPRESSION, expected);
    }

    @Test
    void testExpressionArrowFunctionExpression() {
	String json = "{ 'type': 'ArrowFunctionExpression', 'generator': false, 'async': false, params: [ { 'type': 'Identifier', 'name': 'a' } ], body: { 'type': 'BlockStatement', 'body': [ ] }, 'expression': true }";
	ArrowFunctionExpression expected = ArrowFunctionExpression.create(false, false,
		Collections.singletonList(Identifier.create("a")),
		BlockStatement.create(Collections.emptyList()), true);
	test(json, NodeType.ARROW_FUNCTION_EXPRESSION, expected);
    }

    @Test
    void testUnaryExpression() {
	String json = "{ 'type': 'UnaryExpression', 'operator': '+', 'prefix': true, 'argument': { 'type': 'Identifier', 'name': 'a' } }";
	UnaryExpression expected = UnaryExpression.create(UnaryOperator.ADD, true,
		Identifier.create("a"));
	test(json, NodeType.UNARY_EXPRESSION, expected);
    }

    @Test
    void testUpdateExpression() {
	String json = "{ 'type': 'UpdateExpression', 'operator': '++', 'prefix': true, 'argument': { 'type': 'Identifier', 'name': 'a' } }";
	UpdateExpression expected = UpdateExpression.create(UpdateOperator.INCREMENT, true,
		Identifier.create("a"));
	test(json, NodeType.UPDATE_EXPRESSION, expected);
    }

    @Test
    void testBinaryExpression() {
	String json = "{ 'type': 'BinaryExpression', 'operator': '**', 'left': { 'type': 'Identifier', 'name': 'a' }, 'right': { 'type': 'Identifier', 'name': 'b' } }";
	BinaryExpression expected = BinaryExpression.create(BinaryOperator.EXP,
		Identifier.create("a"), Identifier.create("b"));
	test(json, NodeType.BINARY_EXPRESSION, expected);
    }

    @Test
    void testAssignmentExpression() {
	String json = "{ 'type': 'AssignmentExpression', 'operator': '**=', 'left': { 'type': 'Identifier', 'name': 'a' }, 'right': { 'type': 'Identifier', 'name': 'b' } }";
	AssignmentExpression expected = AssignmentExpression.create(AssignmentOperator.ASSIGN_EXP,
		Identifier.create("a"), Identifier.create("b"));
	test(json, NodeType.ASSIGNMENT_EXPRESSION, expected);
    }

    @Test
    void testLogicalExpression() {
	String json = "{ 'type': 'LogicalExpression', 'operator': '||', 'left': { 'type': 'Identifier', 'name': 'a' }, 'right': { 'type': 'Identifier', 'name': 'b' } }";
	LogicalExpression expected = LogicalExpression.create(LogicalOperator.OR,
		Identifier.create("a"), Identifier.create("b"));
	test(json, NodeType.LOGICAL_EXPRESSION, expected);
    }

    @Test
    void testMemberExpression() {
	String json = "{ 'type': 'MemberExpression', 'object': { 'type': 'Identifier', 'name': 'a' }, 'property': { 'type': 'Identifier', 'name': 'b' }, 'computed': false }";
	MemberExpression expected = MemberExpression.create(Identifier.create("a"),
		Identifier.create("b"), false);
	test(json, NodeType.MEMBER_EXPRESSION, expected);
    }

    @Test
    void testConditionalExpression() {
	String json = "{ 'type': 'ConditionalExpression', 'test': { 'type': 'Identifier', 'name': 'a' }, 'alternate': { 'type': 'Identifier', 'name': 'b' }, 'consequent': { 'type': 'Identifier', 'name': 'c' } }";
	ConditionalExpression expected = ConditionalExpression.create(Identifier.create("a"),
		Identifier.create("b"), Identifier.create("c"));
	test(json, NodeType.CONDITIONAL_EXPRESSION, expected);
    }

    @Test
    void testCallExpression() {
	String json = "{ 'type': 'CallExpression', 'callee': { 'type': 'Identifier', 'name': 'foo' }, 'arguments': [ { 'type': 'Identifier', 'name': 'a' } ] }";
	CallExpression expected = CallExpression.create(Identifier.create("foo"),
		Collections.singletonList(Identifier.create("a")));
	test(json, NodeType.CALL_EXPRESSION, expected);
    }

    @Test
    void testNewExpression() {
	String json = "{ 'type': 'NewExpression', 'callee': { 'type': 'Identifier', 'name': 'foo' }, 'arguments': [ { 'type': 'Identifier', 'name': 'a' } ] }";
	NewExpression expected = NewExpression.create(Identifier.create("foo"),
		Collections.singletonList(Identifier.create("a")));
	test(json, NodeType.NEW_EXPRESSION, expected);
    }

    @Test
    void testSequenceExpression() {
	String json = "{ 'type': 'SequenceExpression', 'expressions': [ { 'type': 'Identifier', 'name': 'a' } ] }";
	SequenceExpression expected = SequenceExpression
		.create(Collections.singletonList(Identifier.create("a")));
	test(json, NodeType.SEQUENCE_EXPRESSION, expected);
    }

    @Test
    void testYieldExpression() {
	String json = "{ 'type': 'YieldExpression', 'argument': null, 'delegate': false }";
	YieldExpression expected = YieldExpression.create(null, false);
	test(json, NodeType.YIELD_EXPRESSION, expected);
    }

    @Test
    void testYieldExpressionWithArgument() {
	String json = "{ 'type': 'YieldExpression', 'argument': { 'type': 'Identifier', 'name': 'a' }, 'delegate': false }";
	YieldExpression expected = YieldExpression.create(Identifier.create("a"), false);
	test(json, NodeType.YIELD_EXPRESSION, expected);
    }

    @Test
    void testTemplateElement() {
	String json = "{ 'type': 'TemplateElement', 'tail': false, 'value': { 'cooked': 'x', 'raw': 'x' } }";
	TemplateElement expected = TemplateElement.create(false,
		TemplateValue.create("\"x\"", "\"x\""));
	test(json, NodeType.TEMPLATE_ELEMENT, expected);
    }

    @Test
    void testTemplateElementWithNullCooked() {
	String json = "{ 'type': 'TemplateElement', 'tail': false, 'value': { 'cooked': null, 'raw': 'x' } }";
	TemplateElement expected = TemplateElement.create(false,
		TemplateValue.create(null, "\"x\""));
	test(json, NodeType.TEMPLATE_ELEMENT, expected);
    }

    @Test
    void testTemplateLiteral() {
	String json = "{ 'type': 'TemplateLiteral', quasis: [ { 'type': 'TemplateElement', 'tail': false, 'value': { 'cooked': 'x', 'raw': 'x' } } ], expressions: [ { 'type': 'Identifier', 'name': 'a' } ] }";
	TemplateLiteral expected = TemplateLiteral.create(
		Collections.singletonList(
			TemplateElement.create(false, TemplateValue.create("\"x\"", "\"x\""))),
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
    void testObjectPatternWithRestElement() {
	String json = "{ 'type': 'ObjectPattern', 'properties': [ { 'type': 'RestElement', 'argument': { 'type': 'Identifier', 'name': 'a' } } ] }";
	ObjectPattern expected = ObjectPattern
		.create(Collections.singletonList(RestElement.create(Identifier.create("a"))));
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
	String json = "{ 'type': 'MethodDefinition', 'key': { 'type': 'Identifier', 'name': 'foo' }, 'value': { 'type': 'FunctionExpression', 'generator': false, 'async': false, 'id': null, params: [ ], body: { 'type': 'BlockStatement', 'body': [ ] } }, 'kind': 'method', 'computed': false, 'static': false }";
	MethodDefinition expected = MethodDefinition.create(Identifier.create("foo"),
		FunctionExpression.create(false, false, null, Collections.emptyList(),
			BlockStatement.create(Collections.emptyList())),
		MethodDefinition.Kind.METHOD, false, false);
	test(json, NodeType.METHOD_DEFINITION, expected);
    }

    @Test
    void testClassBody() {
	String json = "{ 'type': 'ClassBody', 'body': [ { 'type': 'MethodDefinition', 'key': { 'type': 'Identifier', 'name': 'foo' }, 'value': { 'type': 'FunctionExpression', 'generator': false, 'async': false, 'id': null, params: [ ], body: { 'type': 'BlockStatement', 'body': [ ] } }, 'kind': 'method', 'computed': false, 'static': false } ] }";
	ClassBody expected = ClassBody
		.create(Collections.singletonList(MethodDefinition.create(Identifier.create("foo"),
			FunctionExpression.create(false, false, null, Collections.emptyList(),
				BlockStatement.create(Collections.emptyList())),
			MethodDefinition.Kind.METHOD, false, false)));
	test(json, NodeType.CLASS_BODY, expected);
    }

    @Test
    void testAnonClassExpression() {
	String json = "{ 'type': 'ClassExpression', 'id': null, 'superClass': null, 'body': { 'type': 'ClassBody', 'body': [ ] } }";
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

    @Test
    void testMetaProperty() {
	String json = "{ 'type': 'MetaProperty', 'meta': { 'type': 'Identifier', 'name': 'new' }, 'property': { 'type': 'Identifier', 'name': 'target' } }";
	MetaProperty expected = MetaProperty.create(Identifier.create("new"),
		Identifier.create("target"));
	test(json, NodeType.META_PROPERTY, expected);
    }

    @Test
    void testAwaitExpression() {
	String json = "{ 'type': 'AwaitExpression', 'argument': { 'type': 'Identifier', 'name': 'a' } }";
	AwaitExpression expected = AwaitExpression.create(Identifier.create("a"));
	test(json, NodeType.AWAIT_EXPRESSION, expected);
    }

}

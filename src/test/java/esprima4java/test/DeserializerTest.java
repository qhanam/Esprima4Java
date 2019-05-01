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
import esprima4java.ast.EmptyStatement;
import esprima4java.ast.ExpressionStatement;
import esprima4java.ast.Identifier;
import esprima4java.ast.Literal;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;
import esprima4java.ast.Program;
import esprima4java.ast.RegExpLiteral;
import esprima4java.ast.deserialize.DeserializationException;

class DeserializerTest {

    Node test(String json, NodeType type, Node expected) {
	try (Reader reader = new StringReader(json)) {
	    Node actual = Esprima2Java.deserialize(reader);
	    assertEquals(actual.type(), type);
	    assertEquals(actual, expected);
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
}

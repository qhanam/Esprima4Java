package esprima4java.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

import esprima4java.DeserializationException;
import esprima4java.Esprima2Java;
import esprima4java.ast.AstNode;
import esprima4java.ast.Identifier;
import esprima4java.ast.Literal;
import esprima4java.ast.NodeType;
import esprima4java.ast.RegEx;

class JsonToObjectTest {

    AstNode test(String json, NodeType type, AstNode expected) {
	try (Reader reader = new StringReader(json)) {
	    AstNode actual = Esprima2Java.deserialize(reader);
	    assertEquals(actual.getType(), type);
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
	Identifier expected = Identifier.builder().name("foo").build();
	test(json, NodeType.IDENTIFIER, expected);
    }

    @Test
    void testNumberLiteralParsed() {
	String json = "{ 'type': 'Literal', 'value': 0, 'raw': '0' }";
	Literal expected = Literal.builder().number(0).raw("0").build();
	test(json, NodeType.LITERAL, expected);
    }

    @Test
    void testStringLiteralParsed() {
	String json = "{ 'type': 'Literal', 'value': '0', 'raw': '\"0\"' }";
	Literal expected = Literal.builder().string("0").raw("\"0\"").build();
	test(json, NodeType.LITERAL, expected);
    }

    @Test
    void testBoolLiteralParsed() {
	String json = "{ 'type': 'Literal', 'value': true, 'raw': 'true' }";
	Literal expected = Literal.builder().bool(true).raw("true").build();
	test(json, NodeType.LITERAL, expected);
    }

    @Test
    void testRegExLiteralParsed() {
	String json = "{ 'type': 'Literal', 'value': '/.*/g', 'raw': '/.*/g', 'regex': { 'pattern': '.*', 'flags': 'g' } }";
	Literal expected = Literal.builder().regex(RegEx.builder().pattern(".*").flags("g").build())
		.raw("/.*/g").build();
	test(json, NodeType.LITERAL, expected);
    }

    @Test
    void testThisLiteralParsed() {
	String json = "{ 'type': 'Literal', 'value': null, 'raw': 'null' }";
	Literal expected = Literal.builder().keyThis().raw("null").build();
	test(json, NodeType.LITERAL, expected);
    }

}

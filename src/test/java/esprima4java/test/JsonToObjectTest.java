package esprima4java.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

import esprima4java.Esprima2Java;
import esprima4java.ast.AstNode;
import esprima4java.ast.Identifier;
import esprima4java.ast.NodeType;

class JsonToObjectTest {

    AstNode test(String json, NodeType type, AstNode expected) {
	try (Reader reader = new StringReader(json)) {
	    AstNode actual = Esprima2Java.deserialize(reader);
	    assertEquals(actual.getType(), type);
	    assertEquals(actual, expected);
	    return actual;
	} catch (IOException e) {
	    fail("Parsing should succeed.");
	}
	return null;
    }

    @Test
    void testIdentifierParsed() {
	String json = "{ 'type': 'Identifier', 'name': 'foo' }";
	Identifier expected = Identifier.builder().name("foo").build();
	test(json, NodeType.IDENTIFIER, expected);
    }

}

package esprima4java;

import java.io.Reader;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import esprima4java.ast.AstNode;
import esprima4java.ast.Identifier;

/**
 * A utility for converting serialized Esprima (Json) to Java objects.
 */
public class Esprima2Java {

    public static AstNode deserialize(Reader reader) {
	JsonParser parser = new JsonParser();
	JsonElement tree = parser.parse(reader);

	System.out.println(tree);

	return Identifier.builder().name("foo").build();
    }

}

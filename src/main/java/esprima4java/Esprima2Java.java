package esprima4java;

import java.io.Reader;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import esprima4java.ast.AstNode;
import esprima4java.ast.EmptyStatement;
import esprima4java.ast.ExpressionStatement;
import esprima4java.ast.Identifier;
import esprima4java.ast.Literal;

/**
 * A utility for converting serialized Esprima (Json) to Java objects.
 */
public class Esprima2Java {

    public static AstNode deserialize(Reader reader) throws DeserializationException {
	JsonParser parser = new JsonParser();
	JsonElement je = parser.parse(reader);

	if (!je.isJsonObject()) {
	    throw new DeserializationException("JSON is not an object type.");
	}

	return deserialize((JsonObject) je);
    }

    public static AstNode deserialize(JsonObject jo) throws DeserializationException {
	if (!jo.has("type") || !jo.get("type").isJsonPrimitive())
	    throw new DeserializationException("JSON node has no 'type' property.");

	String type = jo.get("type").getAsString();
	switch (type) {
	case "EmptyStatement":
	    return EmptyStatement.deserialize(jo);
	case "ExpressionStatement":
	    return ExpressionStatement.deserialize(jo);
	case "Identifier":
	    return Identifier.deserialize(jo);
	case "Literal":
	    return Literal.deserialize(jo);
	default:
	    throw new DeserializationException("Unrecognized node type " + type);
	}
    }

}

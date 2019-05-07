package esprima4java.ast.deserialize;

import com.google.gson.JsonObject;

import esprima4java.Esprima2Java;
import esprima4java.ast.ForStatement;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;

public class ForStatementDeserializer implements NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.FOR_STATEMENT;
    }

    @Override
    public Node deserialize(JsonObject json) throws DeserializationException {
	Node init = json.has("init") && !json.get("init").isJsonNull()
		? Esprima2Java.deserialize(json.get("init"))
		: null;
	Node test = json.has("test") && !json.get("test").isJsonNull()
		? Esprima2Java.deserialize(json.get("test"))
		: null;
	Node update = json.has("update") && !json.get("update").isJsonNull()
		? Esprima2Java.deserialize(json.get("update"))
		: null;
	Node body = Esprima2Java.deserialize(json.get("body"));
	return ForStatement.create(init, test, update, body);
    }

}

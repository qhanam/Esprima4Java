package esprima4java.ast.deserialize;

import com.google.gson.JsonObject;

import esprima4java.Esprima2Java;
import esprima4java.ast.BreakStatement;
import esprima4java.ast.Identifier;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;

public class BreakStatementDeserializer implements NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.BREAK_STATEMENT;
    }

    @Override
    public Node deserialize(JsonObject json) throws DeserializationException {
	Identifier label = json.has("label") && !json.get("label").isJsonNull()
		? (Identifier) Esprima2Java.deserialize(json.get("label"))
		: null;
	return BreakStatement.create(label);
    }

}

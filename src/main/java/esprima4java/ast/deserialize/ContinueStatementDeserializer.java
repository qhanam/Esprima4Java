package esprima4java.ast.deserialize;

import com.google.gson.JsonObject;

import esprima4java.Esprima2Java;
import esprima4java.ast.ContinueStatement;
import esprima4java.ast.Identifier;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;

public class ContinueStatementDeserializer extends NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.CONTINUE_STATEMENT;
    }

    @Override
    public Node deserializePartial(JsonObject json) throws DeserializationException {
	Identifier label = json.has("label") && !json.get("label").isJsonNull()
		? (Identifier) Esprima2Java.deserialize(json.get("label"))
		: null;
	return ContinueStatement.create(label);
    }

}

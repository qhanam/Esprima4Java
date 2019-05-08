package esprima4java.ast.deserialize;

import com.google.gson.JsonObject;

import esprima4java.Esprima2Java;
import esprima4java.ast.IfStatement;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;

public class IfStatementDeserializer extends NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.IF_STATEMENT;
    }

    @Override
    public Node deserializePartial(JsonObject json) throws DeserializationException {
	Node test = Esprima2Java.deserialize(json.get("test"));
	Node consequent = Esprima2Java.deserialize(json.get("consequent"));
	Node alternate = json.has("alternate") && !json.get("alternate").isJsonNull()
		? Esprima2Java.deserialize(json.get("alternate"))
		: null;
	return IfStatement.create(test, consequent, alternate);
    }

}

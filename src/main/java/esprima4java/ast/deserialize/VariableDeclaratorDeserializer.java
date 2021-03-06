package esprima4java.ast.deserialize;

import com.google.gson.JsonObject;

import esprima4java.Esprima2Java;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;
import esprima4java.ast.VariableDeclarator;

public class VariableDeclaratorDeserializer extends NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.VARIABLE_DECLARATOR;
    }

    @Override
    public Node deserializePartial(JsonObject json) throws DeserializationException {
	Node id = Esprima2Java.deserialize(json.get("id"));
	Node init = json.has("init") && !json.get("init").isJsonNull()
		? Esprima2Java.deserialize(json.get("init"))
		: null;
	return VariableDeclarator.create(id, init);
    }

}

package esprima4java.ast.deserialize;

import com.google.gson.JsonObject;

import esprima4java.ast.Identifier;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;

public class IdentifierDeserializer extends NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.IDENTIFIER;
    }

    @Override
    public Node deserializePartial(JsonObject json) throws DeserializationException {
	return Identifier.create(json.get("name").getAsString());
    }

}

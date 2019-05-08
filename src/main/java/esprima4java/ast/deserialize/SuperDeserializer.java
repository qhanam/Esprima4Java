package esprima4java.ast.deserialize;

import com.google.gson.JsonObject;

import esprima4java.ast.Node;
import esprima4java.ast.NodeType;
import esprima4java.ast.Super;

public class SuperDeserializer extends NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.SUPER;
    }

    @Override
    public Node deserializePartial(JsonObject json) throws DeserializationException {
	return Super.create();
    }

}

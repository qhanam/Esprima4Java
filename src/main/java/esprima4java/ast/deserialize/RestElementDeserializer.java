package esprima4java.ast.deserialize;

import com.google.gson.JsonObject;

import esprima4java.Esprima2Java;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;
import esprima4java.ast.RestElement;

public class RestElementDeserializer extends NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.REST_ELEMENT;
    }

    @Override
    public Node deserializePartial(JsonObject json) throws DeserializationException {
	Node argument = Esprima2Java.deserialize(json.get("argument"));
	return RestElement.create(argument);
    }

}

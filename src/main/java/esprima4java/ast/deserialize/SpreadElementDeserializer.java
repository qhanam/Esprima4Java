package esprima4java.ast.deserialize;

import com.google.gson.JsonObject;

import esprima4java.Esprima2Java;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;
import esprima4java.ast.SpreadElement;

public class SpreadElementDeserializer implements NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.SPREAD_ELEMENT;
    }

    @Override
    public Node deserialize(JsonObject json) throws DeserializationException {
	Node argument = Esprima2Java.deserialize(json.get("argument"));
	return SpreadElement.create(argument);
    }

}

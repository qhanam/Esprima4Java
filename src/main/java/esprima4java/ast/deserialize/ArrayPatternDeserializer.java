package esprima4java.ast.deserialize;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import esprima4java.Esprima2Java;
import esprima4java.ast.ArrayPattern;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;

public class ArrayPatternDeserializer implements NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.ARRAY_PATTERN;
    }

    @Override
    public Node deserialize(JsonObject json) throws DeserializationException {
	List<Node> elements = new ArrayList<>();
	for (JsonElement je : json.get("elements").getAsJsonArray()) {
	    elements.add(Esprima2Java.deserialize(je));
	}
	return ArrayPattern.create(elements);
    }

}

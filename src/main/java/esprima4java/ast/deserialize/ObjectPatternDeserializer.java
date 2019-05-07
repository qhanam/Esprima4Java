package esprima4java.ast.deserialize;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import esprima4java.Esprima2Java;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;
import esprima4java.ast.ObjectPattern;
import esprima4java.ast.Property;

public class ObjectPatternDeserializer implements NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.OBJECT_PATTERN;
    }

    @Override
    public Node deserialize(JsonObject json) throws DeserializationException {
	List<Property> properties = new ArrayList<>();
	for (JsonElement je : json.get("properties").getAsJsonArray()) {
	    properties.add((Property) Esprima2Java.deserialize(je));
	}
	return ObjectPattern.create(properties);
    }

}

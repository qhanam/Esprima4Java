package esprima4java.ast.deserialize;

import com.google.gson.JsonObject;

import esprima4java.Esprima2Java;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;
import esprima4java.ast.Property;
import esprima4java.ast.Property.Kind;

public class PropertyDeserializer implements NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.PROPERTY;
    }

    @Override
    public Node deserialize(JsonObject json) throws DeserializationException {
	Node key = Esprima2Java.deserialize(json.get("key"));
	Node value = Esprima2Java.deserialize(json.get("value"));
	Kind kind;
	switch (json.get("kind").getAsString()) {
	case "init":
	    kind = Kind.INIT;
	    break;
	case "get":
	    kind = Kind.GET;
	    break;
	case "set":
	    kind = Kind.SET;
	    break;
	default:
	    throw new DeserializationException(
		    "Unrecognized property kind " + json.get("kind").getAsString());
	}
	return Property.create(key, value, kind);
    }

}

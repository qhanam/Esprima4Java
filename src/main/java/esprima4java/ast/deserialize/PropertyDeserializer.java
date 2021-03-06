package esprima4java.ast.deserialize;

import com.google.gson.JsonObject;

import esprima4java.Esprima2Java;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;
import esprima4java.ast.Property;
import esprima4java.ast.Property.Kind;

public class PropertyDeserializer extends NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.PROPERTY;
    }

    @Override
    public Node deserializePartial(JsonObject json) throws DeserializationException {
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
	boolean method = json.get("method").getAsBoolean();
	boolean shorthand = json.get("shorthand").getAsBoolean();
	boolean computed = json.get("computed").getAsBoolean();
	return Property.create(key, value, kind, method, shorthand, computed);
    }

}

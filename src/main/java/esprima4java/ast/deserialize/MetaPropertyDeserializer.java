package esprima4java.ast.deserialize;

import com.google.gson.JsonObject;

import esprima4java.Esprima2Java;
import esprima4java.ast.Identifier;
import esprima4java.ast.MetaProperty;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;

public class MetaPropertyDeserializer implements NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.META_PROPERTY;
    }

    @Override
    public Node deserialize(JsonObject json) throws DeserializationException {
	Identifier meta = (Identifier) Esprima2Java.deserialize(json.get("meta"));
	Identifier property = (Identifier) Esprima2Java.deserialize(json.get("property"));
	return MetaProperty.create(meta, property);
    }

}

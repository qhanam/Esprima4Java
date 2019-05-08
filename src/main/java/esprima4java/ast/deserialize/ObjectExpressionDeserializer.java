package esprima4java.ast.deserialize;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import esprima4java.Esprima2Java;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;
import esprima4java.ast.ObjectExpression;

public class ObjectExpressionDeserializer extends NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.OBJECT_EXPRESSION;
    }

    @Override
    public Node deserializePartial(JsonObject json) throws DeserializationException {
	List<Node> properties = new ArrayList<>();
	for (JsonElement je : json.get("properties").getAsJsonArray()) {
	    properties.add(Esprima2Java.deserialize(je));
	}
	return ObjectExpression.create(properties);
    }

}

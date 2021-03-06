package esprima4java.ast.deserialize;

import com.google.gson.JsonObject;

import esprima4java.Esprima2Java;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;
import esprima4java.ast.YieldExpression;

public class YieldExpressionDeserializer extends NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.YIELD_EXPRESSION;
    }

    @Override
    public Node deserializePartial(JsonObject json) throws DeserializationException {
	Node argument = json.has("argument") && !json.get("argument").isJsonNull()
		? Esprima2Java.deserialize(json.get("argument"))
		: null;
	boolean delegate = json.get("delegate").getAsBoolean();
	return YieldExpression.create(argument, delegate);
    }

}

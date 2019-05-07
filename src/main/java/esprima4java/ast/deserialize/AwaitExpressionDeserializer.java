package esprima4java.ast.deserialize;

import com.google.gson.JsonObject;

import esprima4java.Esprima2Java;
import esprima4java.ast.AwaitExpression;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;

public class AwaitExpressionDeserializer implements NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.AWAIT_EXPRESSION;
    }

    @Override
    public Node deserialize(JsonObject json) throws DeserializationException {
	Node argument = Esprima2Java.deserialize(json.get("argument"));
	return AwaitExpression.create(argument);
    }

}

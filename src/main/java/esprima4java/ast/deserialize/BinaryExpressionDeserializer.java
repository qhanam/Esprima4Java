package esprima4java.ast.deserialize;

import com.google.gson.JsonObject;

import esprima4java.Esprima2Java;
import esprima4java.ast.BinaryExpression;
import esprima4java.ast.BinaryExpression.BinaryOperator;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;

public class BinaryExpressionDeserializer extends NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.BINARY_EXPRESSION;
    }

    @Override
    public Node deserializePartial(JsonObject json) throws DeserializationException {
	BinaryOperator operator = BinaryOperator.deserialize(json.get("operator").getAsString());
	if (operator == null)
	    throw new DeserializationException("Unknown update operator.");
	Node left = Esprima2Java.deserialize(json.get("left"));
	Node right = Esprima2Java.deserialize(json.get("right"));
	return BinaryExpression.create(operator, left, right);
    }

}

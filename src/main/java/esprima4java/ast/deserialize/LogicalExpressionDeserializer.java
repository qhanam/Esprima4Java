package esprima4java.ast.deserialize;

import com.google.gson.JsonObject;

import esprima4java.Esprima2Java;
import esprima4java.ast.LogicalExpression;
import esprima4java.ast.LogicalExpression.LogicalOperator;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;

public class LogicalExpressionDeserializer implements NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.LOGICAL_EXPRESSION;
    }

    @Override
    public Node deserialize(JsonObject json) throws DeserializationException {
	LogicalOperator operator = LogicalOperator.deserialize(json.get("operator").getAsString());
	if (operator == null)
	    throw new DeserializationException("Unknown update operator.");
	Node left = Esprima2Java.deserialize(json.get("left"));
	Node right = Esprima2Java.deserialize(json.get("right"));
	return LogicalExpression.create(operator, left, right);
    }

}

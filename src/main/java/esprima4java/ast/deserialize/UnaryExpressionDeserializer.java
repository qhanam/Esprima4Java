package esprima4java.ast.deserialize;

import com.google.gson.JsonObject;

import esprima4java.Esprima2Java;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;
import esprima4java.ast.UnaryExpression;
import esprima4java.ast.UnaryExpression.UnaryOperator;

public class UnaryExpressionDeserializer implements NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.UNARY_EXPRESSION;
    }

    @Override
    public Node deserialize(JsonObject json) throws DeserializationException {
	UnaryOperator operator = UnaryOperator.deserialize(json.get("operator").getAsString());
	if (operator == null)
	    throw new DeserializationException("Unknown unary operator.");
	boolean prefix = json.get("prefix").getAsBoolean();
	Node argument = Esprima2Java.deserialize(json.get("argument"));
	return UnaryExpression.create(operator, prefix, argument);
    }

}

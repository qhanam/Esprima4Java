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
	UnaryOperator operator;
	switch (json.get("operator").getAsString()) {
	case "+":
	    operator = UnaryOperator.ADD;
	    break;
	case "-":
	    operator = UnaryOperator.SUB;
	    break;
	case "!":
	    operator = UnaryOperator.NOT;
	    break;
	case "~":
	    operator = UnaryOperator.BIT_NOT;
	    break;
	case "typeof":
	    operator = UnaryOperator.TYPEOF;
	    break;
	case "void":
	    operator = UnaryOperator.VOID;
	    break;
	case "delete":
	    operator = UnaryOperator.DELETE;
	    break;
	default:
	    throw new DeserializationException("Unknown unary operator.");
	}
	boolean prefix = json.get("prefix").getAsBoolean();
	Node argument = Esprima2Java.deserialize(json.get("argument"));
	return UnaryExpression.create(operator, prefix, argument);
    }

}

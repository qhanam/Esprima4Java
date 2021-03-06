package esprima4java.ast.deserialize;

import com.google.gson.JsonObject;

import esprima4java.Esprima2Java;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;
import esprima4java.ast.UpdateExpression;
import esprima4java.ast.UpdateExpression.UpdateOperator;

public class UpdateExpressionDeserializer extends NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.UPDATE_EXPRESSION;
    }

    @Override
    public Node deserializePartial(JsonObject json) throws DeserializationException {
	UpdateOperator operator = UpdateOperator.deserialize(json.get("operator").getAsString());
	if (operator == null)
	    throw new DeserializationException("Unknown update operator.");
	boolean prefix = json.get("prefix").getAsBoolean();
	Node argument = Esprima2Java.deserialize(json.get("argument"));
	return UpdateExpression.create(operator, prefix, argument);
    }

}

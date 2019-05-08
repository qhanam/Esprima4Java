package esprima4java.ast.deserialize;

import com.google.gson.JsonObject;

import esprima4java.Esprima2Java;
import esprima4java.ast.AssignmentExpression;
import esprima4java.ast.AssignmentExpression.AssignmentOperator;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;

public class AssignmentExpressionDeserializer extends NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.ASSIGNMENT_EXPRESSION;
    }

    @Override
    public Node deserializePartial(JsonObject json) throws DeserializationException {
	AssignmentOperator operator = AssignmentOperator
		.deserialize(json.get("operator").getAsString());
	if (operator == null)
	    throw new DeserializationException("Unknown update operator.");
	Node left = Esprima2Java.deserialize(json.get("left"));
	Node right = Esprima2Java.deserialize(json.get("right"));
	return AssignmentExpression.create(operator, left, right);
    }

}

package esprima4java.ast.deserialize;

import com.google.gson.JsonObject;

import esprima4java.Esprima2Java;
import esprima4java.ast.ConditionalExpression;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;

public class ConditionalExpressionDeserializer implements NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.CONDITIONAL_EXPRESSION;
    }

    @Override
    public Node deserialize(JsonObject json) throws DeserializationException {
	Node test = Esprima2Java.deserialize(json.get("test"));
	Node alternate = Esprima2Java.deserialize(json.get("alternate"));
	Node consequent = Esprima2Java.deserialize(json.get("consequent"));
	return ConditionalExpression.create(test, alternate, consequent);
    }

}

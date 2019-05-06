package esprima4java.ast.deserialize;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import esprima4java.Esprima2Java;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;
import esprima4java.ast.SequenceExpression;

public class SequenceExpressionDeserializer implements NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.SEQUENCE_EXPRESSION;
    }

    @Override
    public Node deserialize(JsonObject json) throws DeserializationException {
	List<Node> expressions = new ArrayList<>();
	for (JsonElement je : json.get("expressions").getAsJsonArray()) {
	    expressions.add(Esprima2Java.deserialize(je));
	}
	return SequenceExpression.create(expressions);
    }

}

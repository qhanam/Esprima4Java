package esprima4java.ast.deserialize;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import esprima4java.Esprima2Java;
import esprima4java.ast.CallExpression;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;

public class CallExpressionDeserializer extends NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.CALL_EXPRESSION;
    }

    @Override
    public Node deserializePartial(JsonObject json) throws DeserializationException {
	Node callee = Esprima2Java.deserialize(json.get("callee"));
	List<Node> arguments = new ArrayList<Node>();
	for (JsonElement je : json.get("arguments").getAsJsonArray()) {
	    arguments.add(Esprima2Java.deserialize(je));
	}
	return CallExpression.create(callee, arguments);
    }

}

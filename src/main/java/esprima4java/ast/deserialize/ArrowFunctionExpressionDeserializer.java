package esprima4java.ast.deserialize;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import esprima4java.Esprima2Java;
import esprima4java.ast.ArrowFunctionExpression;
import esprima4java.ast.Identifier;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;

public class ArrowFunctionExpressionDeserializer implements NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.ARROW_FUNCTION_EXPRESSION;
    }

    @Override
    public Node deserialize(JsonObject json) throws DeserializationException {
	boolean generator = json.get("generator").getAsBoolean();
	List<Identifier> params = new ArrayList<>();
	for (JsonElement param : json.get("params").getAsJsonArray()) {
	    params.add((Identifier) Esprima2Java.deserialize(param));
	}
	Node body = Esprima2Java.deserialize(json.get("body"));
	boolean expression = json.get("expression").getAsBoolean();
	return ArrowFunctionExpression.create(generator, params, body, expression);
    }

}

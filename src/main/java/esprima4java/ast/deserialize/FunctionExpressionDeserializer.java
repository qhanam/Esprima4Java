package esprima4java.ast.deserialize;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import esprima4java.Esprima2Java;
import esprima4java.ast.BlockStatement;
import esprima4java.ast.FunctionExpression;
import esprima4java.ast.Identifier;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;

public class FunctionExpressionDeserializer implements NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.FUNCTION_EXPRESSION;
    }

    @Override
    public Node deserialize(JsonObject json) throws DeserializationException {
	boolean generator = json.get("generator").getAsBoolean();
	boolean async = json.get("async").getAsBoolean();
	Identifier id = json.has("id") ? (Identifier) Esprima2Java.deserialize(json.get("id"))
		: null;
	List<Identifier> params = new ArrayList<>();
	for (JsonElement param : json.get("params").getAsJsonArray()) {
	    params.add((Identifier) Esprima2Java.deserialize(param));
	}
	BlockStatement body = (BlockStatement) Esprima2Java.deserialize(json.get("body"));
	return FunctionExpression.create(generator, async, id, params, body);
    }

}

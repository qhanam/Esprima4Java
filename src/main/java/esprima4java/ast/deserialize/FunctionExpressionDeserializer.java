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
	List<Identifier> params = new ArrayList<>();
	for (JsonElement param : json.get("params").getAsJsonArray()) {
	    params.add((Identifier) Esprima2Java.deserialize((JsonObject) param));
	}
	BlockStatement body = (BlockStatement) Esprima2Java
		.deserialize((JsonObject) json.get("body"));

	if (json.has("id")) {
	    Identifier id = (Identifier) Esprima2Java.deserialize((JsonObject) json.get("id"));
	    return FunctionExpression.create(id, params, body);
	} else {
	    return FunctionExpression.create(params, body);
	}
    }

}

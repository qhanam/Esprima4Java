package esprima4java.ast.deserialize;

import com.google.gson.JsonObject;

import esprima4java.Esprima2Java;
import esprima4java.ast.ExpressionStatement;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;

public class ExpressionStatementDeserializer implements NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.EXPRESSION_STATEMENT;
    }

    @Override
    public Node deserialize(JsonObject json) throws DeserializationException {
	String directive = json.has("directive") && !json.get("directive").isJsonNull()
		? json.get("directive").getAsString()
		: null;
	Node expression = Esprima2Java.deserialize(json.get("expression"));
	return ExpressionStatement.create(expression, directive);
    }

}

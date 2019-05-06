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
	if (json.has("directive")) {
	    return ExpressionStatement.create(Esprima2Java.deserialize(json.get("expression")),
		    json.get("directive").getAsString());
	}
	return ExpressionStatement.create(Esprima2Java.deserialize(json.get("expression")));
    }

}

package esprima4java.ast.deserialize;

import com.google.gson.JsonObject;

import esprima4java.Esprima2Java;
import esprima4java.ast.ContinueStatement;
import esprima4java.ast.Identifier;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;

public class ContinueStatementDeserializer implements NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.CONTINUE_STATEMENT;
    }

    @Override
    public Node deserialize(JsonObject json) throws DeserializationException {
	if (json.has("label")) {
	    return ContinueStatement
		    .create((Identifier) Esprima2Java.deserialize((JsonObject) json.get("label")));
	}
	return ContinueStatement.create();
    }

}

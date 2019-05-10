package esprima4java.ast.deserialize;

import com.google.gson.JsonObject;

import esprima4java.Esprima2Java;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;
import esprima4java.ast.ReturnStatement;

public class ReturnStatementDeserializer extends NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.RETURN_STATEMENT;
    }

    @Override
    public Node deserializePartial(JsonObject json) throws DeserializationException {
	if (json.has("argument")) {
	    return ReturnStatement.create(Esprima2Java.deserialize(json.get("argument")));
	} else {
	    return ReturnStatement.create(null);
	}
    }

}

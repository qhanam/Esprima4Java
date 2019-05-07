package esprima4java.ast.deserialize;

import com.google.gson.JsonObject;

import esprima4java.Esprima2Java;
import esprima4java.ast.ForOfStatement;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;

public class ForOfStatementDeserializer implements NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.FOR_OF_STATEMENT;
    }

    @Override
    public Node deserialize(JsonObject json) throws DeserializationException {
	Node left = Esprima2Java.deserialize(json.get("left"));
	Node right = Esprima2Java.deserialize(json.get("right"));
	Node body = Esprima2Java.deserialize(json.get("body"));
	boolean await = json.get("await").getAsBoolean();
	return ForOfStatement.create(left, right, body, await);
    }

}

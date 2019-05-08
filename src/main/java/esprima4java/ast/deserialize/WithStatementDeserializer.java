package esprima4java.ast.deserialize;

import com.google.gson.JsonObject;

import esprima4java.Esprima2Java;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;
import esprima4java.ast.WithStatement;

public class WithStatementDeserializer extends NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.WITH_STATEMENT;
    }

    @Override
    public Node deserializePartial(JsonObject json) throws DeserializationException {
	Node object = Esprima2Java.deserialize(json.get("object"));
	Node body = Esprima2Java.deserialize(json.get("body"));
	return WithStatement.create(object, body);
    }

}

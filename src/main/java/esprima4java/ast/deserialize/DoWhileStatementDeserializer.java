package esprima4java.ast.deserialize;

import com.google.gson.JsonObject;

import esprima4java.Esprima2Java;
import esprima4java.ast.DoWhileStatement;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;

public class DoWhileStatementDeserializer extends NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.DO_WHILE_STATEMENT;
    }

    @Override
    public Node deserializePartial(JsonObject json) throws DeserializationException {
	Node test = Esprima2Java.deserialize(json.get("test"));
	Node body = Esprima2Java.deserialize(json.get("body"));
	return DoWhileStatement.create(test, body);
    }

}

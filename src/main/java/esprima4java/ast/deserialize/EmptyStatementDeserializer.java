package esprima4java.ast.deserialize;

import com.google.gson.JsonObject;

import esprima4java.ast.EmptyStatement;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;

public class EmptyStatementDeserializer extends NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.EMPTY_STATEMENT;
    }

    @Override
    public Node deserializePartial(JsonObject json) throws DeserializationException {
	return EmptyStatement.create();
    }

}

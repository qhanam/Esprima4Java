package esprima4java.ast.deserialize;

import com.google.gson.JsonObject;

import esprima4java.Esprima2Java;
import esprima4java.ast.BlockStatement;
import esprima4java.ast.CatchClause;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;
import esprima4java.ast.TryStatement;

public class TryStatementDeserializer implements NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.TRY_STATEMENT;
    }

    @Override
    public Node deserialize(JsonObject json) throws DeserializationException {
	BlockStatement block = (BlockStatement) Esprima2Java
		.deserialize((JsonObject) json.get("block"));
	CatchClause handler = json.has("handler")
		? (CatchClause) Esprima2Java.deserialize(json.get("handler"))
		: null;
	BlockStatement finalizer = json.has("finalizer")
		? (BlockStatement) Esprima2Java.deserialize(json.get("finalizer"))
		: null;
	return TryStatement.create(block, handler, finalizer);
    }

}
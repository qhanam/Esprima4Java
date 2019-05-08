package esprima4java.ast.deserialize;

import com.google.gson.JsonObject;

import esprima4java.Esprima2Java;
import esprima4java.ast.BlockStatement;
import esprima4java.ast.CatchClause;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;

public class CatchClauseDeserializer extends NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.CATCH_CLAUSE;
    }

    @Override
    public Node deserializePartial(JsonObject json) throws DeserializationException {
	Node param = json.has("param") && !json.get("param").isJsonNull()
		? Esprima2Java.deserialize(json.get("param"))
		: null;
	BlockStatement body = (BlockStatement) Esprima2Java.deserialize(json.get("body"));
	return CatchClause.create(param, body);
    }

}

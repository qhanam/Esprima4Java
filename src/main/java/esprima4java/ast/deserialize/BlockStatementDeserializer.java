package esprima4java.ast.deserialize;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import esprima4java.Esprima2Java;
import esprima4java.ast.BlockStatement;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;

public class BlockStatementDeserializer implements NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.BLOCK_STATEMENT;
    }

    @Override
    public Node deserialize(JsonObject json) throws DeserializationException {
	List<Node> statements = new ArrayList<Node>();
	JsonArray jsonStatements = (JsonArray) json.get("body");
	for (JsonElement jsonStatement : jsonStatements) {
	    statements.add(Esprima2Java.deserialize((JsonObject) jsonStatement));
	}
	return BlockStatement.create(statements);
    }

}

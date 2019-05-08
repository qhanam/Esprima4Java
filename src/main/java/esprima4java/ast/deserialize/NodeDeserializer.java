package esprima4java.ast.deserialize;

import com.google.gson.JsonObject;

import esprima4java.addons.SourceLocation;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;

public abstract class NodeDeserializer {

    /**
     * Deserializes a JSON object onto an abstract syntax sub-tree.
     * 
     * @return The root node of the sub-tree.
     */
    public Node deserialize(JsonObject json) throws DeserializationException {
	Node node = deserializePartial(json);
	Integer startLine = null;
	Integer endLine = null;
	Integer startColumn = null;
	Integer endColumn = null;
	Integer startChar = null;
	Integer endChar = null;

	if (json.has("range")) {
	    startChar = json.get("range").getAsJsonArray().get(0).getAsInt();
	    endChar = json.get("range").getAsJsonArray().get(1).getAsInt();
	}

	if (json.has("loc")) {
	    startLine = json.get("loc").getAsJsonObject().get("start").getAsJsonObject().get("line")
		    .getAsInt();
	    endLine = json.get("loc").getAsJsonObject().get("end").getAsJsonObject().get("line")
		    .getAsInt();
	    startColumn = json.get("loc").getAsJsonObject().get("start").getAsJsonObject()
		    .get("column").getAsInt();
	    endColumn = json.get("loc").getAsJsonObject().get("end").getAsJsonObject().get("column")
		    .getAsInt();
	}

	node.setSourceLocation(SourceLocation.create(startLine, endLine, startColumn, endColumn,
		startChar, endChar));

	return node;
    }

    /**
     * Returns the NodeType supported by the de-serializer.
     */
    abstract NodeType getSupportedType();

    /**
     * Deserializes a JON object into a partial abstract syntax sub-tree, which
     * includes type-specific information.
     */
    abstract Node deserializePartial(JsonObject json) throws DeserializationException;

}

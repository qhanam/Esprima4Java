package esprima4java.ast.deserialize;

import com.google.gson.JsonObject;

import esprima4java.ast.Node;
import esprima4java.ast.NodeType;

public interface NodeDeserializer {

    /**
     * Returns the NodeType supported by the de-serializer.
     */
    NodeType getSupportedType();

    /**
     * Deserializes a JSON object onto an abstract syntax sub-tree.
     * 
     * @return The root node of the sub-tree.
     */
    Node deserialize(JsonObject json) throws DeserializationException;

}

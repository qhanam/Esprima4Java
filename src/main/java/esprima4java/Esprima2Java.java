package esprima4java;

import java.io.Reader;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import esprima4java.ast.Node;
import esprima4java.ast.deserialize.DeserializationException;
import esprima4java.ast.deserialize.NodeDeserializers;

/**
 * A utility for converting serialized Esprima (Json) to Java objects.
 */
public class Esprima2Java {

    public static Node deserialize(Reader reader) throws DeserializationException {
	JsonParser parser = new JsonParser();
	JsonElement je = parser.parse(reader);

	if (!je.isJsonObject()) {
	    throw new DeserializationException("JSON is not an object type.");
	}

	return deserialize((JsonObject) je);
    }

    public static Node deserialize(JsonElement je) throws DeserializationException {
	JsonObject jo = (JsonObject) je;
	if (!jo.has("type") || !jo.get("type").isJsonPrimitive())
	    throw new DeserializationException("JSON node has no 'type' property.");
	return NodeDeserializers.instance().getDeserializer(jo.get("type").getAsString())
		.deserialize(jo);
    }

}

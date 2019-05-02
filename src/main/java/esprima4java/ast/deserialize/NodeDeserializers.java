package esprima4java.ast.deserialize;

import java.util.HashMap;
import java.util.Map;

public class NodeDeserializers {

    /**
     * ADD NEW DESERIALIZERS HERE
     */
    private static final NodeDeserializer[] deserializers = { //
	    new ProgramDeserializer(), //
	    new FunctionDeclarationDeserializer(), //
	    new BlockStatementDeserializer(), //
	    new EmptyStatementDeserializer(), //
	    new ExpressionStatementDeserializer(), //
	    new WithStatementDeserializer(), //
	    new FunctionExpressionDeserializer(), //
	    new IdentifierDeserializer(), //
	    new LiteralDeserializer() //
    };

    private static NodeDeserializers instance = null;

    private Map<String, NodeDeserializer> dszMap;

    private NodeDeserializers() {
	dszMap = new HashMap<>();
	for (NodeDeserializer deserializer : deserializers) {
	    dszMap.put(deserializer.getSupportedType().toString(), deserializer);
	}
    }

    public static NodeDeserializers instance() {
	if (instance == null) {
	    instance = new NodeDeserializers();
	}
	return instance;
    }

    /**
     * Returns the appropriate de-serializer for the given type.
     * 
     * @throws DeserializationException
     *             when the type is not recognized.
     */
    public NodeDeserializer getDeserializer(String type) throws DeserializationException {
	if (!dszMap.containsKey(type)) {
	    throw new DeserializationException("Unrecognized node type " + type);
	}
	return dszMap.get(type);
    }

}

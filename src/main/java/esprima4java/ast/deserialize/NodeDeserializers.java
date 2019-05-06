package esprima4java.ast.deserialize;

import java.util.HashMap;
import java.util.Map;

public class NodeDeserializers {

    /**
     * ADD NEW DESERIALIZERS HERE
     */
    private static final NodeDeserializer[] deserializers = { //
	    // Program
	    new ProgramDeserializer(), //
	    // Statements
	    new BlockStatementDeserializer(), //
	    new EmptyStatementDeserializer(), //
	    new ExpressionStatementDeserializer(), //
	    new WithStatementDeserializer(), //
	    // Control Flow
	    new LabeledStatementDeserializer(), //
	    new ReturnStatementDeserializer(), //
	    new BreakStatementDeserializer(), //
	    new ContinueStatementDeserializer(), //
	    // Choice
	    new IfStatementDeserializer(), //
	    new SwitchStatementDeserializer(), //
	    new SwitchCaseDeserializer(), //
	    // Exceptions
	    new ThrowStatementDeserializer(), //
	    new TryStatementDeserializer(), //
	    new CatchClauseDeserializer(), //
	    // Loops
	    new WhileStatementDeserializer(), //
	    new DoWhileStatementDeserializer(), //
	    new ForStatementDeserializer(), //
	    new ForInStatementDeserializer(), //
	    // Declarations
	    new FunctionDeclarationDeserializer(), //
	    new VariableDeclarationDeserializer(), //
	    new VariableDeclaratorDeserializer(), //
	    // Expressions
	    new ThisExpressionDeserializer(), //
	    new ArrayExpressionDeserializer(), //
	    new ObjectExpressionDeserializer(), //
	    new PropertyDeserializer(), //
	    new FunctionExpressionDeserializer(), //
	    // Unary Operators
	    new UnaryExpressionDeserializer(), //
	    new UpdateExpressionDeserializer(), //
	    // Binary Operators
	    new BinaryExpressionDeserializer(), //
	    new AssignmentExpressionDeserializer(), //
	    new LogicalExpressionDeserializer(), //
	    new MemberExpressionDeserializer(), //
	    new ConditionalExpressionDeserializer(), //
	    new CallExpressionDeserializer(), //
	    new NewExpressionDeserializer(), //
	    new SequenceExpressionDeserializer(), //
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

package esprima4java.ast;

public enum NodeType {
    // Identifier
    IDENTIFIER("Identifier"), //
    // Literal
    LITERAL("Literal"), //
    // Program
    PROGRAM("Program"), //
    // Statements
    BLOCK_STATEMENT("BlockStatement"), //
    EMPTY_STATEMENT("EmptyStatement"), //
    EXPRESSION_STATEMENT("ExpressionStatement"), //
    WITH_STATEMENT("WithStatement"), //
    // Control Flow
    LABELED_STATEMENT("LabeledStatement"), //
    RETURN_STATEMENT("ReturnStatement"), //
    BREAK_STATEMENT("BreakStatement"), //
    CONTINUE_STATEMENT("ContinueStatement"), //
    // Choice
    IF_STATEMENT("IfStatement"), //
    SWITCH_STATEMENT("SwitchStatement"), //
    SWITCH_CASE("SwitchCase"), //
    // Exceptions
    THROW_STATEMENT("ThrowStatement"), //
    TRY_STATEMENT("TryStatement"), //
    CATCH_CLAUSE("CatchClause"), //
    // Loops
    WHILE_STATEMENT("WhileStatement"), //
    DO_WHILE_STATEMENT("DoWhileStatement"), //
    FOR_STATEMENT("ForStatement"), //
    FOR_IN_STATEMENT("ForInStatement"), //
    FOR_OF_STATEMENT("ForOfStatement"), //
    // Declarations
    FUNCTION_DECLARATION("FunctionDeclaration"), //
    VARIABLE_DECLARATION("VariableDeclaration"), //
    VARIABLE_DECLARATOR("VariableDeclarator"), //
    // Expressions
    SUPER("Super"), //
    YIELD_EXPRESSION("YieldExpression"), //
    THIS_EXPRESSION("ThisExpression"), //
    ARRAY_EXPRESSION("ArrayExpression"), //
    SPREAD_ELEMENT("SpreadElement"), //
    OBJECT_EXPRESSION("ObjectExpression"), //
    PROPERTY("Property"), //
    FUNCTION_EXPRESSION("FunctionExpression"), //
    ARROW_FUNCTION_EXPRESSION("ArrowFunctionExpression"), //
    // Unary Operators
    UNARY_EXPRESSION("UnaryExpression"), //
    UPDATE_EXPRESSION("UpdateExpression"), //
    // Binary Operators
    BINARY_EXPRESSION("BinaryExpression"), //
    ASSIGNMENT_EXPRESSION("AssignmentExpression"), //
    LOGICAL_EXPRESSION("LogicalExpression"), //
    MEMBER_EXPRESSION("MemberExpression"), //
    CONDITIONAL_EXPRESSION("ConditionalExpression"), //
    // Function Calls
    CALL_EXPRESSION("CallExpression"), //
    NEW_EXPRESSION("NewExpression"), //
    SEQUENCE_EXPRESSION("SequenceExpression"), //
    // Templates
    TAGGED_TEMPLATE_EXPRESSION("TaggedTemplateExpression"), //
    TEMPLATE_LITERAL("TemplateLiteral"), //
    TEMPLATE_ELEMENT("TemplateElement"), //
    // Patterns
    OBJECT_PATTERN("ObjectPattern"), //
    ARRAY_PATTERN("ArrayPattern"), //
    REST_ELEMENT("RestElement"), //
    ASSIGNMENT_PATTERN("AssignmentPattern"), //
    // Classes
    CLASS_DECLARATION("ClassDeclaration"), //
    CLASS_EXPRESSION("ClassExpression"), //
    CLASS_BODY("ClassBody"), //
    METHOD_DEFINITION("MethodDefinition"), //
    META_PROPERTY("MetaProperty");

    private String esprimaName;

    private NodeType(String esprimaName) {
	this.esprimaName = esprimaName;
    }

    @Override
    public String toString() {
	return esprimaName;
    }

}

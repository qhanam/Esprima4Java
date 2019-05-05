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
    // Declarations
    FUNCTION_DECLARATION("FunctionDeclaration"), //
    VARIABLE_DECLARATION("VariableDeclaration"), //
    VARIABLE_DECLARATOR("VariableDeclarator"), //
    // Expressions
    THIS_EXPRESSION("ThisExpression"), //
    ARRAY_EXPRESSION("ArrayExpression"), //
    OBJECT_EXPRESSION("ObjectExpression"), //
    PROPERTY("Property"), //
    FUNCTION_EXPRESSION("FunctionExpression"), //
    // Unary Operators
    UNARY_EXPRESSION("UnaryExpression"), //
    UPDATE_EXPRESSION("UpdateExpression"), //
    // Binary Operators
    BINARY_EXPRESSION("BinaryExpression"), //
    ASSIGNMENT_EXPRESSION("AssignmentExpression");

    private String esprimaName;

    private NodeType(String esprimaName) {
	this.esprimaName = esprimaName;
    }

    @Override
    public String toString() {
	return esprimaName;
    }

}

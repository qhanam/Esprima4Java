package esprima4java.ast;

public enum NodeType {
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
    FUNCTION_EXPRESSION("FunctionExpression"), //
    LITERAL("Literal"), //
    IDENTIFIER("Identifier");

    private String esprimaName;

    private NodeType(String esprimaName) {
	this.esprimaName = esprimaName;
    }

    @Override
    public String toString() {
	return esprimaName;
    }

}

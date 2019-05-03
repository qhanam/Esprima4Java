package esprima4java.ast;

public enum NodeType {
    // Program
    PROGRAM("Program"), //
    // Declarations
    FUNCTION_DECLARATION("FunctionDeclaration"), //
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
    SWITCH_CASE("SwitchCase"), //
    // Choice
    IF_STATEMENT("IfStatement"), //
    // Expressions
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

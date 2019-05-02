package esprima4java.ast;

public enum NodeType {

    PROGRAM("Program"), //

    // Statements
    BLOCK_STATEMENT("BlockStatement"), //
    EMPTY_STATEMENT("EmptyStatement"), //
    EXPRESSION_STATEMENT("ExpressionStatement"), //
    FUNCTION_DECLARATION("FunctionDeclaration"), //
    WITH_STATEMENT("WithStatement"), //

    // Expressions
    FUNCTION_EXPRESSION("FunctionExpression"), //

    // Other
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

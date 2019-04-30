package esprima4java.ast;

public enum NodeType {

    PROGRAM("Program"), //

    // Statements
    FUNCTION_DECLARATION("FunctionDeclaration"), //
    BLOCK_STATEMENT("BlockStatement"), //

    // Expressions
    FUNCTION_EXPRESSION("FunctionExpression"), //

    // Other
    LITERAL("Literal"), //
    IDENTIFIER("Identifier");

    private String esprimaName;

    private NodeType(String esprimaName) {
	this.esprimaName = esprimaName;
    }

}

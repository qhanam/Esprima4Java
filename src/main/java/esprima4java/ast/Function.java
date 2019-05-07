package esprima4java.ast;

public abstract class Function extends Node {
    public abstract boolean generator();

    public abstract boolean async();
}

package esprima4java;

/**
 * An exception thrown when JSON deserialization fails.
 */
public class DeserializationException extends Exception {
    private static final long serialVersionUID = 1L;

    public DeserializationException(String message) {
	super(message);
    }
}

package esprima4java.cfg;

/**
 * For creating unique IDs for CFG nodes and edges.
 */
public class CfgIdGenerator {

    private static int uniqueID = 0;

    public static int getUniqueID() {
	int id = uniqueID;
	uniqueID++;
	return id;
    }

}

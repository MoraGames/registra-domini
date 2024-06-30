package it.unimib.sd2024.types;

public enum DocumentOperation {
    INSERT, //INSERT <data-structure>
    SEARCH, //SEARCH <data-field> <operand> <data-value>[ AND <data-field> <operand> <data-value>]*
    REMOVE; //REMOVE <data-field>

    public static boolean contains(String operation) {
        for (CollectionOperation op : CollectionOperation.values()) {
            if (op.name().equals(operation)) {
                return true;
            }
        }
        return false;
    }
}

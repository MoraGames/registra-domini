package it.unimib.sd2024.types;

public class SearchCondition {
    private String field;
    private String operand;
    private String value;

    public SearchCondition(String field, String operand, String value) {
        this.field = field;
        this.operand = operand;
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public String getOperand() {
        return operand;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return field + " " + operand + " " + value;
    }
}

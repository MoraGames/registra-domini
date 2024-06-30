package it.unimib.sd2024.types;

public class SearchCondition {
    private String field;
    private String operand;
    private String value;

    public SearchCondition(String field, String operand, String value) {
        if (!operand.equals("=") && !operand.equals("!=") && !operand.equals(">") && !operand.equals("<") && !operand.equals(">=") && !operand.equals("<=")){
            throw new IllegalArgumentException("Invalid operand: " + operand);
        }
        this.field = field;
        this.operand = operand;
        this.value = value;
    }

    public String getField() {
        return this.field;
    }

    public String getOperand() {
        return this.operand;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.field + " " + this.operand + " " + this.value;
    }

    public boolean isSatisfiedBy(String fieldValue) {
        switch (this.operand) {
            case "=":
                return fieldValue.equals(this.value);
            case "!=":
                return !fieldValue.equals(this.value);
            case ">":
                return fieldValue.compareTo(this.value) > 0;
            case "<":
                return fieldValue.compareTo(this.value) < 0;
            case ">=":
                return fieldValue.compareTo(this.value) >= 0;
            case "<=":
                return fieldValue.compareTo(this.value) <= 0;
            default:
                return false;
        }
    }
}

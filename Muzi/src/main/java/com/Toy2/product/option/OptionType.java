package com.Toy2.product.option;

public enum OptionType {
    DEFAULT("DEFAULT"), SELECT("SELECT");

    private final String type;
    OptionType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static String validate(String type) {
        for (OptionType optionType : OptionType.values()) {
            if (optionType.getType().equalsIgnoreCase(type)) {
                return type.toUpperCase();
            }
        }
        throw new IllegalArgumentException("Unknown OptionType: " + type);
    }
}

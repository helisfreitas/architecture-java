package br.helis.architecture.products.entity;

import lombok.Getter;

@Getter
public enum Category {
    
    MOBILE(1, "Mobile"),
    ELECTRONIC(2, "Electronic"),
    FURNITURE(3, "Furniture"),
    TOOL(4, "Tool"),
    OTHER(5, "Other");
    
    private int code;
    private String name;

    Category(int code, String name) {
        this.code = code;
        this.name = name;
    }
}

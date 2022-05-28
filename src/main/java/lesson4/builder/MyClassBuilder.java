package lesson4.builder;

public class MyClassBuilder {
    private String attr1;
    private Boolean attr2;
    private Long attr3 = 1l;

    public MyClassBuilder setAttr1(String attr1) {
        this.attr1 = attr1;
        return this;
    }

    public MyClassBuilder setAttr2(Boolean attr2) {
        this.attr2 = attr2;
        return this;
    }

    public MyClassBuilder setAttr3(Long attr3) {
        this.attr3 = attr3;
        return this;
    }

    public MyClass createMyClass() {
        return new MyClass(attr1, attr2, attr3);
    }
}
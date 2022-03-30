package lesson4;

public class MyClass {

    private String attr1;
    private Boolean attr2;
    private Long attr3;

    public String getAttr1() {
        return attr1;
    }

    public void setAttr1(String attr1) {
        this.attr1 = attr1;
    }

    public Boolean getAttr2() {
        return attr2;
    }

    public void setAttr2(Boolean attr2) {
        this.attr2 = attr2;
    }

    public Long getAttr3() {
        return attr3;
    }

    public void setAttr3(Long attr3) {
        this.attr3 = attr3;
    }

    public MyClass(String attr1, Boolean attr2, Long attr3) {
        this.attr1 = attr1;
        this.attr2 = attr2;
        this.attr3 = attr3;
    }

    public MyClass() {
    }
}

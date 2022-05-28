package lesson4.builder;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyClass myClass = (MyClass) o;
        return Objects.equals(attr1, myClass.attr1) && Objects.equals(attr2, myClass.attr2) && Objects.equals(attr3, myClass.attr3);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attr1, attr2, attr3);
    }
}

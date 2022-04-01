package lesson4;

import org.junit.jupiter.api.Test;

public class MyClassTest {

    @Test
    void createObject(){

        MyClass myClass1 = new MyClass();
        myClass1.setAttr1("");
        myClass1.setAttr2(true);
        myClass1.setAttr3(1l);


        MyClass myClass2 = new MyClass("",true,1l);

        MyClass myClass3 = new MyClassBuilder()
                .setAttr1("")
                .setAttr2(true)
                .setAttr3(1l)
                .createMyClass();
    }
}

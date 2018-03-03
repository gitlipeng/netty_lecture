package com.shengsiyuan.decorator;

public class ConcreateDecorator2 extends Decorator {

    public ConcreateDecorator2(Component component) {
        super(component);
    }

    @Override
    public void doSomething() {
        super.doSomething();
        this.doAnotherThing();
    }

    private void doAnotherThing() {
        System.out.println("ConcreateDecorator2 功能C");
    }
}

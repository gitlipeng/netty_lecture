package com.shengsiyuan.decorator;

public class ConcreateDecorator1 extends Decorator {

    public ConcreateDecorator1(Component component) {
        super(component);
    }

    @Override
    public void doSomething() {
        super.doSomething();
        this.doAnotherThing();
    }

    private void doAnotherThing() {
        System.out.println("ConcreateDecorator1 功能B");
    }
}

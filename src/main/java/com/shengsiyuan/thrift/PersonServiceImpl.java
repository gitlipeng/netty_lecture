package com.shengsiyuan.thrift;

import org.apache.thrift.TException;
import thrift.generated.DataException;
import thrift.generated.Person;
import thrift.generated.PersonService;

public class PersonServiceImpl implements PersonService.Iface {

    @Override
    public Person getPersonByUsername(String username) throws DataException, TException {
        System.out.println("Get client param:"+username);

        Person person = new Person();
        person.setUsername(username);
        person.setAge(12);
        person.setMarried(false);

        return person;
    }

    @Override
    public void savePerson(Person person) throws DataException, TException {
        System.out.println("Got Client Param:");

        System.out.println(person.getUsername() + "," + person.getAge() + "," + person.isMarried());
    }
}

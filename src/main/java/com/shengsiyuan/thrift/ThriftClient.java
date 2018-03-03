package com.shengsiyuan.thrift;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFastFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import thrift.generated.Person;
import thrift.generated.PersonService;

public class ThriftClient {
    public static void main(String[] args) {
        TTransport transport = new TFastFramedTransport(new TSocket("localhost", 8899), 600);

        TProtocol protocol = new TCompactProtocol(transport);
        PersonService.Client client = new PersonService.Client(protocol);

        try {

            transport.open();
            Person person = client.getPersonByUsername("lipeng");
            System.out.println(person.getUsername() + "," + person.getAge() + "," + person.isMarried());

            System.out.println("--------");


            Person person2 = new Person();
            person2.setUsername("张丽");
            person2.setAge(12);
            person2.setMarried(false);
            client.savePerson(person2);



        } catch (Exception e) {
            throw new RuntimeException();
        }finally {
            transport.close();
        }
    }
}

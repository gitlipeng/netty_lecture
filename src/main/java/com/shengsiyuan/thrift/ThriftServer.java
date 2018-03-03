package com.shengsiyuan.thrift;

import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFastFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;
import thrift.generated.PersonService;

public class ThriftServer {
    public static void main(String[] args) throws Exception {
        TNonblockingServerSocket socket = new TNonblockingServerSocket(8899);//Socket对象 客户端和服务端的连接
        THsHaServer.Args arg = new THsHaServer.Args(socket).minWorkerThreads(2).maxWorkerThreads(4);

        PersonService.Processor<PersonServiceImpl> processor = new PersonService.Processor<>(new PersonServiceImpl());

        arg.protocolFactory(new TCompactProtocol.Factory());//协议层 压缩协议
        arg.transportFactory(new TFastFramedTransport.Factory());//传输层 更底层
        arg.processorFactory(new TProcessorFactory(processor));

        TServer server = new THsHaServer(arg);//半同步半异步
        System.out.println("Thrift Server Started");

        server.serve();//死循环

    }
}

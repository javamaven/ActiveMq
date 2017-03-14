package com.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by chenhaijun on 2017/3/14.
 */
public class Revicer {

    public static void main(String args[]) throws JMSException {

        //1.建立connectionFactory工厂对象 需要用户名密码地址 可以用默认
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,ActiveMQConnectionFactory.DEFAULT_PASSWORD,"tcp://localhost:61616");

        //2.建立连接 启动
        Connection connection = connectionFactory.createConnection();
        connection.start();

        //3.创建session对象
        Session session = connection.createSession(true,Session.CLIENT_ACKNOWLEDGE);

        //4 创建一个destination 类似于queue
        Destination destination = session.createQueue("testQueue");

        //5.创建一个消息的接收或者发送对象
        MessageConsumer consumer = session.createConsumer(destination);

        //7使用消息规范发送消息
        while(true){
            TextMessage msg = (TextMessage)consumer.receive();
            if (msg == null ){
                System.out.println("--------------------+当前类=Revicer.main()");
                break;
            }
            System.out.println("--------------------msg.getText()="+msg.getText()+","+"当前类=Revicer.main()");
            msg.acknowledge();
        }

        if(connection!=null){
            connection.close();
        }

    }

}

package com.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by chenhaijun on 2017/3/14.
 */
public class Sender {

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
        MessageProducer producer = session.createProducer(null);

        //6设置持久化特性和非持久化
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        //7使用消息规范发送消息
        for(int i =1; i<9; i++){
            TextMessage msg = session.createTextMessage();
            msg.setText("你好"+i);
            producer.send(destination,msg,DeliveryMode.NON_PERSISTENT,i,100000);
            System.out.println("--------------------i="+5+","+"当前类=Sender.main()");
        }

        session.commit();

        if(connection!=null){
         connection.close();
        }
    }

}

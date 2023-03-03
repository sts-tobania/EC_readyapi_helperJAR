package helper;

import com.ibm.mq.jms.MQQueue;
import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;

import javax.jms.*;


public class MQConnector {
    private MQQueueConnectionFactory qcf;
    private QueueConnection qc;
    private Queue q;
    private QueueSession qs;
    public MQConnector(String hostName, int port, String queueManager, String channel) throws JMSException {
        this.qcf = new MQQueueConnectionFactory();
        // Host and port settings
        qcf.setHostName (hostName);
        qcf.setPort (port);
        qcf.setQueueManager (queueManager);
        qcf.setChannel (channel);
        qcf.setTransportType (WMQConstants.WMQ_CM_CLIENT);
    }

    public void connectToQueue(String asUser, String withPassword, String queueName) throws JMSException, JMSException {

        this.qc = qcf.createQueueConnection (asUser ,withPassword);  //set credentials
        qc.start();

        // Create a queue and a session
        this.q = new MQQueue(queueName);
        this.qs = qc.createQueueSession (false, Session.AUTO_ACKNOWLEDGE);
    }

    public void disconnectFromQueue() throws JMSException {
        qc.close();
    }

    public void putMessage(Message message) throws JMSException {
        QueueSender queueSender = qs.createSender(q);
        queueSender.send(message);
    }

    public Message getMessage() throws JMSException {
        QueueReceiver queueReceiver = qs.createReceiver(q);
        return queueReceiver.receive();
    }

    public Message createTextMessage(String text) throws JMSException {
        return qs.createTextMessage(text);
    }
}

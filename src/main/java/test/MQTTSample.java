package test;


import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MQTTSample {
  public static void main(String[] args) {
    String topic        = "my/mqtt/topic";
    String content      = "Hello CloudMQTT";
    int qos             = 1;
    String broker       = "tcp://m12.cloudmqtt.com:10341";

    //MQTT client id to use for the device. "" will generate a client id automatically
    String clientId     = "";

    MemoryPersistence persistence = new MemoryPersistence();
    try {
      MqttClient mqttClient = new MqttClient(broker, clientId, persistence);
      mqttClient.setCallback(new MqttCallback() {
        public void messageArrived(String topic, MqttMessage msg)
                  throws Exception {
                      System.out.println("Recived:" + topic);
                      System.out.println("Recived:" + new String(msg.getPayload()));
                }

        public void deliveryComplete(IMqttDeliveryToken arg0) {
                    System.out.println("Delivary complete");
                }

        public void connectionLost(Throwable arg0) {
                    // TODO Auto-generated method stub
                }
      });

      MqttConnectOptions connOpts = new MqttConnectOptions();
      connOpts.setCleanSession(true);
     // connOpts.setUserName("szbauxis");
      //connOpts.setPassword(new char[]{'d', 'f', 'q', 'o', 'H', 'x', 'P', 'D', '3', 'S', 'z', 'U'});
      connOpts.setUserName("meindert");
      connOpts.setPassword(new char[]{'j', 'a', 't', 'k', 'l', 'e', 'd', '1', '4', '7'});
      mqttClient.connect(connOpts);
      MqttMessage message = new MqttMessage(content.getBytes());
      message.setQos(qos); 
      System.out.println("Publish message: " + message);
      mqttClient.subscribe(topic, qos);
      mqttClient.publish(topic, message);
      mqttClient.disconnect();
      System.exit(0);
    } catch(MqttException me) {
      System.out.println("reason "+me.getReasonCode());
      System.out.println("msg "+me.getMessage());
      System.out.println("loc "+me.getLocalizedMessage());
      System.out.println("cause "+me.getCause());
      System.out.println("excep "+me);

    }
  }
}
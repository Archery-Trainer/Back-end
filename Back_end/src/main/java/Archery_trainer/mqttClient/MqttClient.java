package Archery_trainer.mqttClient;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import com.amazonaws.services.iot.client.*;


/**
 * Class for listening the MQTT topic where messages from the embedded device are published.
 * 
 * 
 * To test this you will need to have a certificate and a private key from Amazon IoT.
 * Also a key store file needs to be generated for these certificates.
 * Need to think about how to handle this so that everyone has access.
 * 
 * This is tested in a maven project where the key store file is stored in 
 * src/main/resources and the pom file includes:
 * 
   <dependency>
      <groupId>com.amazonaws</groupId>
      <artifactId>aws-iot-device-sdk-java</artifactId>
      <version>1.1.1</version>
   </dependency>

   <dependency>
      <groupId>com.amazonaws</groupId>
      <artifactId>aws-iot-device-sdk-java-samples</artifactId>
      <version>1.1.1</version>
   </dependency>
 */
public class MqttClient {

	private static AWSIotMqttClient awsIotClient;
	
	//@TODO: How to pass this address to the MQTT server? 
	private static final String CLIENT_ENDPOINT = "a20pmpdacgwj4.iot.us-east-1.amazonaws.com";
	//CLIENT_ID needs to be unique for every client and during testing the connection gets messed up if I 
	//make multiple subsequent connections with the same id. So here's a hacky one-liner to generate a random string
	private static final String CLIENT_ID = Long.toHexString(Double.doubleToLongBits(Math.random()));
	private static final String TEST_TOPIC = "#";					// # is wildcard
	private static final AWSIotQos TestTopicQos = AWSIotQos.QOS0; 	//Don't know what this is	
	
	/**
	 * Initialize the MQTT-client
	 * 
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static void init() 
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException {
	
		if (awsIotClient == null) {
			
			/*
			 * @TODO: This key store thing is pretty awkward
			 * 
			 *  Basically I ran
			 *  	openssl pkcs12 -export -in <certificate-file> -inkey <private-key-file> -out p12.keystore -name alias
			 *  
			 *  where <certificate-file> was the path to the certificate and 
			 *  <private-key-file> to the key I got from Amazon IoT.
			 *  This created a keystore file whose path is below.
			 *  
			 *  How to handle the certificates and these passwords?
			 */
			String keyStoreFile = "src/main/resources/p12.keystore";
			String keyStorePassword = "salasana";
			String keyPassword = "salasana";
	
			KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());

			keyStore.load(new FileInputStream(keyStoreFile), keyStorePassword.toCharArray());

			//Initialize the client with our credentials
			awsIotClient = new AWSIotMqttClient(CLIENT_ENDPOINT, CLIENT_ID, keyStore, keyPassword);
			
			System.out.println("Connected to " + CLIENT_ENDPOINT + " as " + CLIENT_ID);
			
		
       }
		
       if (awsIotClient == null) {
            throw new IllegalArgumentException("Failed to construct client due to missing certificate or credentials.");
       }

	}
	
	/**
	 * 
	 * @param c the client to set
	 */
	public static void setClient(AWSIotMqttClient c) {
		awsIotClient = c;
	}
	
	
	/**
	 * MQTT-message listener
	 */
	private class TopicListener extends AWSIotTopic {
		
		OnMessageCallback onMsg;

		
		/**
		 * Construct a TopicListener
		 * 
		 * @param topic	Topic to subscribe to
		 * @param qos	?
		 */
		public TopicListener(String topic, AWSIotQos qos, OnMessageCallback onMsgCallback) {
		    super(topic, qos);
		    onMsg = onMsgCallback;
		}

		/**
		 * This function gets called when a message is published to the topic
		 * 
		 * @param message	The message read from the topic
		 */
		@Override
		public void onMessage(AWSIotMessage message) {
			
			onMsg.call(message.getStringPayload());
			
		}

	}

	public void disconnect() throws AWSIotException {
                System.out.println("Disconnecting AWS IOT client");
		awsIotClient.unsubscribe(TEST_TOPIC);
                awsIotClient.disconnect();
                awsIotClient = null;
	}
		
	
	/**
	 * Construct client and subscribe to topic #. 
	 * 
	 */
	public MqttClient(OnMessageCallback onMsgCallback) {
		try {

		    init();

		    awsIotClient.connect();

		    AWSIotTopic topic = new TopicListener(TEST_TOPIC, TestTopicQos, onMsgCallback);
		    awsIotClient.subscribe(topic, true);

    		System.out.println("Subscribed to topic " + TEST_TOPIC);
    		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}



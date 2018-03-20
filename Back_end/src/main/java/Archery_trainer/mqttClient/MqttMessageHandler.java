package Archery_trainer.mqttClient;

import com.amazonaws.services.iot.client.AWSIotException;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Handler class for the MQTT-messages
 */
public class MqttMessageHandler {

	private List<String> messages;
	private final int MAX_NUM_MESSAGES = 2000;
	private MqttClient client;

	/**
	 * Initialize handler and connect to MQTT-server
	 */
	public MqttMessageHandler() {
		messages = new LinkedList<>();

		//Callback that will be called when a message arrives
		OnMessageCallback cb = new AddToCollectionCallback(messages);

		client = new MqttClient(cb);
	}

	/**
	 * Disconnect from the MQTT-server
	 */
	public void disconnect() {
		try {
			client.disconnect();
		} catch (AWSIotException e) {
			System.out.println("Unable to disconnect from AWS IOT");
			e.printStackTrace();
		}
	}

	/**
	 * Get the messages that have been received
	 * @return The received messages
	 */
	public List<String> getMessages() {
		return messages;
	}

	/**
	 * Get the newest received message
	 *
	 * @return The newest message from the MQTT-server
	 */
	public String getNewestMessage() {

		int sz = messages.size();

		if(sz != 0) {

			String res = messages.get(sz - 1);

			//List should be cleared sometimes.
			//Maybe after saving or discarding sessions
			if(sz > MAX_NUM_MESSAGES) {
				//Removing with iterator is O(1)
				Iterator<String> it = messages.listIterator(0);
				it.remove();
			}

			return res;
		}
		else
			return "";
	}

	/**
	 * Clear the messages container
	 */
	public void cleanUp() {
		messages.clear();
	}
}

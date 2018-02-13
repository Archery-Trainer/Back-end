package Archery_trainer.mqttClient;

import com.amazonaws.services.iot.client.AWSIotException;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MqttMessageHandler {

	private List<String> messages;
	private final int MAX_NUM_MESSAGES = 2000;
	private MqttClient client;

	public MqttMessageHandler() {
		messages = new LinkedList<>();

		//Callback that will be called when a message arrives
		OnMessageCallback cb = new AddToCollectionCallback(messages);

		client = new MqttClient(cb);
	}

	public void disconnect() {
		try {
			client.disconnect();
		} catch (AWSIotException e) {
			System.out.println("Unable to disconnect from AWS IOT");
			e.printStackTrace();
		}
	}

	public List<String> getMessages() {
		return messages;
	}

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

	public void cleanUp() {
		messages.clear();
	}
}

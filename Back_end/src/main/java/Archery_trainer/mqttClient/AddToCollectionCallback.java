package Archery_trainer.mqttClient;

import java.util.Collection;

/**
 * Utility to add messages to a list when they are received. The list is provided by the caller. 
 *
 */
public class AddToCollectionCallback implements OnMessageCallback{
	private final int MAX_MESSAGES = 10000;

	private Collection<String> storage;
	
	/**
	 * Set up the utility
	 * 
	 * @param collection The storage where messages are stored
	 */
	public AddToCollectionCallback(Collection<String> collection) {
		storage = collection;
	}
	
	/**
	 * Callback that will be called by the TopicListener
	 * @param message Message received from the MQTT-topic
	 */
	public void call(String message) {
		if(storage.size() < MAX_MESSAGES)
			storage.add(message);
	}

}

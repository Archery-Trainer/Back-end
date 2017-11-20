

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		//A list where messages will be stored
		ArrayList<String> list = new ArrayList<String>();
		
		//Callback that will be called when a message arrives
		OnMessageCallback cb = new AddToCollectionCallback(list);
		
		MQTT_client c = new MQTT_client(cb);
		//The client is not needed anymore
		
		
		int i = 0;
		while(true) {

			if(list.size() > i) {
				//Print out a message
				System.out.println(list.get(i));
				i++;
			}

		}
	}

}

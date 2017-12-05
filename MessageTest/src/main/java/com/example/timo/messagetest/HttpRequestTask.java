package com.example.timo.messagetest;

import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;



public class HttpRequestTask extends AsyncTask<Void, Void, Message> {

    /**
     * Send a http request to the server and create a Message object
     *
     * @param params
     * @return
     */
    @Override
    protected Message doInBackground(Void... params) {
        try {
            System.out.println("Sending HTTP request");
            final String url = "http://ec2-54-208-42-165.compute-1.amazonaws.com:80/message";
            RestTemplate restTemplate = new RestTemplate();
            System.out.println("");
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            Message message = restTemplate.getForObject(url, Message.class);

            System.out.println("Recieved: \n{\n" +
                    message.getId() + "\n" + message.getType() + "\n" + message.getPayload() + "\n}");
            return message;
        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Message msg) {
        if(msg == null) {
            System.out.println("Message was null");
            return;
        }

        //Do something with the newly created Message object
    }

}

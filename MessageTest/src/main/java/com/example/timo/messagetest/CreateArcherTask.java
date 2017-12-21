package com.example.timo.messagetest;

import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;


public class CreateArcherTask extends AsyncTask<Void, Void, Archer> {

    //@TODO: how to handle urls?
    final String URL = "http://ec2-54-208-42-165.compute-1.amazonaws.com:80/CreateArcher";

    /**
     * Send a http request to the server to create a new archer
     *
     * @param params
     * @return
     */
    @Override
    protected Archer doInBackground(Void... params) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            Archer a = new Archer("Testiheppu", false);

            System.out.println("Sending POST request with " + a.toString());

            Archer res = restTemplate.postForObject(URL, a, Archer.class);


            return res;
        } catch (Exception e) {
            Log.e("createArcher", e.getMessage(), e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Archer a) {
        if(a == null) {
            System.out.println("Response was null");
            return;
        }

        System.out.println("onPostExecute called with " + a.toString());
    }

}
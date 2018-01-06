package com.example.timo.messagetest;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;


public class CreateArcherTask extends AsyncTask<Void, Void, Archer> {

    //@TODO: how to handle urls?
    final String URL = "http://ec2-54-208-42-165.compute-1.amazonaws.com:80/createArcher";

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

            //Create test archer and convert to json
            Gson gson = new Gson();
            Archer a = new Archer("Testiheppu", false);
            String requestJson = gson.toJson(a);

            System.out.println("req: " + requestJson);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);

            //Send POST
            Archer answer = restTemplate.postForObject(URL, requestJson, Archer.class);
            System.out.println("Answer: " + answer.toString());

            return answer;
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
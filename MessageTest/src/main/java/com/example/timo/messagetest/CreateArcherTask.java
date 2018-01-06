package com.example.timo.messagetest;

import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
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
        Archer a = null;
        try {
            //Create rest template and json converter
            RestTemplate restTemplate = new RestTemplate();
            MappingJackson2HttpMessageConverter msgConverter = new MappingJackson2HttpMessageConverter();
            restTemplate.getMessageConverters().add(msgConverter);

            //Create test archer and a HTTP entity from it
            Gson gson = new Gson();
            a = new Archer("Testiheppu2", false);
            HttpEntity<Archer> entity = new HttpEntity<Archer>(a);

            //Send POST
            ResponseEntity<ObjectNode> res = restTemplate.postForEntity(URL, entity, ObjectNode.class);

            //@TODO: Maybe we should return a boolean that tells whether creation was successfull
            Archer responseObject = gson.fromJson(res.getBody().toString(), Archer.class);
            return responseObject;

        } catch (Exception e) {
            Log.e("createArcher", e.getMessage(), e);
            return null;
        }

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
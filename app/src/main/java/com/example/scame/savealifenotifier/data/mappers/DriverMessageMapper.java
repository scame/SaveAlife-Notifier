package com.example.scame.savealifenotifier.data.mappers;


import com.example.scame.savealifenotifier.presentation.models.DriversMessageModel;
import com.mapbox.mapboxsdk.geometry.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DriverMessageMapper {

    private static final String MESSAGE_BODY = "messageBody";
    private static final String PATH = "path";

    public DriversMessageModel convert(Map<String, String> remoteMessage) {
        DriversMessageModel messageModel = new DriversMessageModel();
        Set<String> keySet = remoteMessage.keySet();

        String messageBody;

        for (String key : keySet) {

            if (key.equals(MESSAGE_BODY)) {
                messageBody = remoteMessage.get(key);
                messageModel.setMessageBody(messageBody);
            } else if (key.equals(PATH)) {
                try {
                    JSONArray jsonArray = new JSONArray(remoteMessage.get(key));
                    List<LatLng> latLngList = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        Double latitude = jsonObject.getDouble("latitude");
                        Double longitude = jsonObject.getDouble("longitude");
                        LatLng latLng = new LatLng(latitude, longitude);
                        latLngList.add(latLng);
                    }

                    messageModel.setPath(latLngList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return messageModel;
    }
}

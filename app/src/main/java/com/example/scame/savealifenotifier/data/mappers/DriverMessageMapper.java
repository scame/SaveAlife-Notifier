package com.example.scame.savealifenotifier.data.mappers;


import com.example.scame.savealifenotifier.data.entities.LatLongPair;
import com.example.scame.savealifenotifier.presentation.models.DriversMessageModel;

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
                    List<LatLongPair> latLongPairList = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String latitude = jsonObject.getString("latitude");
                        String longitude = jsonObject.getString("longitude");
                        LatLongPair latLongPair = new LatLongPair(Double.valueOf(latitude), Double.valueOf(longitude));
                        latLongPairList.add(latLongPair);
                    }

                    messageModel.setPath(latLongPairList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return messageModel;
    }
}

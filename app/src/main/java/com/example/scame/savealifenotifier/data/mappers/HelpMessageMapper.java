package com.example.scame.savealifenotifier.data.mappers;


import com.example.scame.savealifenotifier.presentation.models.HelpMessageModel;

import java.util.Map;
import java.util.Set;

public class HelpMessageMapper {

    private static final String MESSAGE_KEY = "messageBody";
    private static final String LAT_KEY = "latitude";
    private static final String LONG_KEY = "longitude";

    public HelpMessageModel convert(Map<String, String> helpMessage) {
        HelpMessageModel helpMessageModel = new HelpMessageModel();
        Set<String> keySet = helpMessage.keySet();

        for (String key : keySet) {
            switch (key) {
                case MESSAGE_KEY:
                    String message = helpMessage.get(MESSAGE_KEY);
                    helpMessageModel.setMessage(message);
                    break;
                case LAT_KEY:
                    String latitude = helpMessage.get(LAT_KEY);
                    helpMessageModel.setLatitude(Double.valueOf(latitude));
                    break;
                case LONG_KEY:
                    String longitude = helpMessage.get(LONG_KEY);
                    helpMessageModel.setLongitude(Double.valueOf(longitude));
                    break;
            }
        }

        return helpMessageModel;
    }
}

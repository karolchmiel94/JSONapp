package com.example.karol.aplikacjanapraktyki;

import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by Karol on 11.01.2017.
 */


public class JSONparser {

    public List<HashMap<String, Object>> parse(JSONObject jObject) {
        JSONArray jData = null;
        try {
            jData = jObject.getJSONArray("data");
        } catch (JSONException var4) {
            var4.printStackTrace();
        }
        return this.getData(jData);
    }

    private List<HashMap<String, Object>> getData(JSONArray jCountries) {
        ArrayList dataList = new ArrayList();
        HashMap data;
        for(int i = 0; i < jCountries.length(); ++i) {
            try {
                data = this.getData((JSONObject)jCountries.get(i));
                dataList.add(data);
            } catch (JSONException var7) {
                var7.printStackTrace();
            }
        }
        return dataList;
    }

    private HashMap<String, Object> getData(JSONObject jCountry) {
        HashMap data = new HashMap();
        try {
            String name = jCountry.getString("name");
            String lastName = jCountry.getString("last_name");
            String country = jCountry.getString("country");
            data.put("name", name);
            data.put("last_name", lastName);
            data.put("country", country);
        } catch (JSONException var10) {
            var10.printStackTrace();
        }
        return data;
    }
}
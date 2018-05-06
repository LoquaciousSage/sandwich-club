package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        // variables to hold sandwich details
        String sandwichName,  sandwichOrigin, sandwichDescription, imageURL;
        JSONArray jsonAKA, jsonIngredients;
        List<String> sandwichAKA = new ArrayList<String>();
        List<String> sandwichIngredients = new ArrayList<String>();

        // JSON object variables
        JSONObject sandwichJSON, nameJSON;

        //get JSON objects
        try {
            sandwichJSON = new JSONObject(json);
            nameJSON = sandwichJSON.getJSONObject("name");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


        // assign sandwich variables
        try {
            sandwichName = nameJSON.getString("mainName");
            jsonAKA = nameJSON.getJSONArray("alsoKnownAs");
            if (jsonAKA.length() != 0) {
                for (int index=0; index < jsonAKA.length(); index++) {
                    sandwichAKA.add(jsonAKA.getString(index));
                }
            }
            sandwichOrigin = sandwichJSON.getString("placeOfOrigin");
            sandwichDescription = sandwichJSON.getString("description");
            imageURL = sandwichJSON.getString("image");
            jsonIngredients = sandwichJSON.getJSONArray("ingredients");
            if (jsonIngredients.length() != 0) {
                for (int index=0; index < jsonIngredients.length(); index++ ) {
                    sandwichIngredients.add(jsonIngredients.getString(index));
                }
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        Sandwich sandwich = new Sandwich
                (sandwichName, sandwichAKA, sandwichOrigin, sandwichDescription, imageURL, sandwichIngredients);

        return sandwich;
    }
}

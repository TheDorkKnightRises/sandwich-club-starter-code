package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            JSONObject names = obj.getJSONObject("name");
            String mainName = names.getString("mainName");
            JSONArray aka = names.getJSONArray("alsoKnownAs");
            ArrayList<String> alsoKnownAs = new ArrayList<>();
            for (int i = 0; i < aka.length(); i++) {
                alsoKnownAs.add(aka.getString(i));
            }
            String placeOfOrigin = obj.getString("placeOfOrigin");
            String description = obj.getString("description");
            String image = obj.getString("image");
            JSONArray ingredientArray = obj.getJSONArray("ingredients");
            ArrayList<String> ingredients = new ArrayList<>();
            for (int i = 0; i < ingredientArray.length(); i++) {
                ingredients.add(ingredientArray.getString(i));
            }
            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}

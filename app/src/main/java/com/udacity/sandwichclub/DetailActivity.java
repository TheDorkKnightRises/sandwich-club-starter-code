package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        getSupportActionBar().setTitle(sandwich.getMainName());
        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        TextView akaTv = findViewById(R.id.also_known_tv);
        TextView ingredientsTv = findViewById(R.id.ingredients_tv);
        TextView originTv = findViewById(R.id.origin_tv);
        TextView descriptionTv = findViewById(R.id.description_tv);

        List aka = sandwich.getAlsoKnownAs();
        if (aka.isEmpty()) {
            akaTv.setText(" - ");
        } else {
            String akaStr = aka.toString();
            akaTv.setText(akaStr.substring(1, akaStr.length() - 1));
        }
        List ing = sandwich.getIngredients();
        if (ing.isEmpty()) {
            ingredientsTv.setText(" - ");
        } else {
            String ingStr = ing.toString();
            ingredientsTv.setText(ingStr.substring(1, ingStr.length() - 1));
        }
        if (sandwich.getPlaceOfOrigin().equals("")) {
            originTv.setText(" - ");
        } else {
            originTv.setText(sandwich.getPlaceOfOrigin());
        }
        descriptionTv.setText(sandwich.getDescription());

    }
}

package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;
import android.databinding.DataBindingUtil;

import com.udacity.sandwichclub.databinding.ActivityDetailBinding;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    ActivityDetailBinding mBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

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
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        // Populate sandwich origin
        mBinding.originTv.setText(sandwich.getPlaceOfOrigin());

        // Populate sandwich AKA
        List<String> alsoKnownAs = sandwich.getAlsoKnownAs();
        if (alsoKnownAs.isEmpty()) {
            mBinding.alsoKnownTv.setText("");
        } else {
            String stringAKA = alsoKnownAs.get(0);
            for (int i = 1; i < alsoKnownAs.size(); i++) {
                stringAKA = stringAKA + ", " + alsoKnownAs.get(i);
            }
            stringAKA = stringAKA + ".";
            mBinding.alsoKnownTv.setText(stringAKA);
        }

        // Populate sandwich ingredients
        List<String> sandwichIngredients = sandwich.getIngredients();
        if (sandwichIngredients.isEmpty()) {
            mBinding.ingredientsTv.setText("");
        } else {
            String ingredients = sandwichIngredients.get(0);
            for (int i = 1; i < sandwichIngredients.size(); i++) {
                ingredients = ingredients + ", " + sandwichIngredients.get(i);
            }
            ingredients = ingredients + ".";
            mBinding.ingredientsTv.setText(ingredients);
        }

        // Populate description
        mBinding.descriptionTv.setText(sandwich.getDescription());

    }
}

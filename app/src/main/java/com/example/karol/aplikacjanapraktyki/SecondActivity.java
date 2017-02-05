package com.example.karol.aplikacjanapraktyki;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

TextView nameTextView, lastNameTextView, countryTextView;
Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        String name = getIntent().getStringExtra("name");
        String last_name = getIntent().getStringExtra("last_name");
        String country = getIntent().getStringExtra("country");

        nameTextView = (TextView) findViewById(R.id.display_name);
        nameTextView.setText(name);
        lastNameTextView = (TextView) findViewById(R.id.display_last_name);
        lastNameTextView.setText(last_name);
        countryTextView = (TextView) findViewById(R.id.display_country);
        countryTextView.setText(country);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}

package com.example.turfbooking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Homepage extends AppCompatActivity {

    Button b1, b2, b3, btcomplaint,btgallery,btmymatches,btmatchrequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        b1 = (Button) findViewById(R.id.btlogout);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

        b2 = (Button) findViewById(R.id.btturf);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(getApplicationContext(), ViewTurf.class));
            }
        });
        b3 = (Button) findViewById(R.id.btbookings);

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(getApplicationContext(), Bookings.class));
            }
        });

        btcomplaint = (Button) findViewById(R.id.btcomplaint);

        btcomplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(getApplicationContext(), SendComplaint.class));
            }
        });
        btgallery = (Button) findViewById(R.id.btgallery);

        btgallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(getApplicationContext(), TurfGallery.class));
            }
        });
        btmymatches = (Button) findViewById(R.id.btmymatches);

        btmymatches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(getApplicationContext(), Matches.class));
            }
        });
        btmatchrequests = (Button) findViewById(R.id.btmatcherequests);

        btmatchrequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(getApplicationContext(), MatchRequest.class));
            }
        });
    }
}
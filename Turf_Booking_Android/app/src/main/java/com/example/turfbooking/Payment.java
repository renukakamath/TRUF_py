package com.example.turfbooking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

public class Payment extends AppCompatActivity implements JsonResponse{
    EditText ed_card_num, ed_cvv, ed_exp_date, ed_amount;
    Button bt_pay;
    String amount, card_no, cvv, exp_date;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ed_card_num = findViewById(R.id.ed_card_num);
        ed_cvv = findViewById(R.id.ed_cvv);
        ed_exp_date = findViewById(R.id.ed_exp_date);
        ed_amount = findViewById(R.id.ed_amount);
        ed_amount.setText(Bookings.amounts);
        bt_pay = findViewById(R.id.bt_pay);
        ed_amount.setText(Bookings.amounts);
        ed_amount.setEnabled(false);



        bt_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount = ed_amount.getText().toString();
                card_no = ed_card_num.getText().toString();
                cvv = ed_cvv.getText().toString();
                exp_date = ed_exp_date.getText().toString();

                if (card_no.length() != 16) {
                    ed_card_num.setError("Valid card number");
                    ed_card_num.setFocusable(true);
                } else if (cvv.length() != 3) {
                    ed_cvv.setError("Valid cvv");
                    ed_cvv.setFocusable(true);
                } else if (exp_date.equalsIgnoreCase("")) {
                    ed_exp_date.setError("Expiry date");
                    ed_exp_date.setFocusable(true);
                } else {


                        JsonReq JR=new JsonReq();
                        JR.json_response=(JsonResponse) Payment.this;
                        String q = "/payments?book_id="+Bookings.book_ids+"&amount="+Bookings.amounts;
                        q=q.replace(" ","%20");
                        JR.execute(q);


                }
            }

        });
        ed_exp_date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                exp_date = ed_exp_date.getText().toString();
                if (exp_date.length() == 2 && !exp_date.contains("/")) {
                    ed_exp_date.setText(exp_date + "/");
                    ed_exp_date.setSelection(ed_exp_date.getText().length());
                }
            }
        });

    }

    public void response(JSONObject jo) {
        try {
            String status=jo.getString("status");

            if(status.equalsIgnoreCase("success")) {
                Toast.makeText(getApplicationContext(), "payment success ", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),Homepage.class));

            } else {
                Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_LONG).show();

            }
        } catch(Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

}
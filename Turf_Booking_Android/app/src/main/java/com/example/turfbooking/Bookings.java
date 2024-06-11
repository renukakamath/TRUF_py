package com.example.turfbooking;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class Bookings extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView lv1;
    String[] book_id, slot_id, turf_id, owner_name, day, from_time, to_time, amount, booking_status, val;
    public static String book_ids, booking_statuss,amounts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings);
        lv1 = (ListView) findViewById(R.id.lv1);
        lv1.setOnItemClickListener(this);

        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Bookings.this;
        String q = "/Bookings?login_id=" + Login.logid;
        q = q.replace(" ", "%20");
        JR.execute(q);
    }

    public void response(JSONObject jo) {
        // TODO Auto-generated method stub
        try {

            String method = jo.getString("method");
            if (method.equalsIgnoreCase("Bookings")) {
                String status = jo.getString("status");
                Log.d("pearl", status);
                Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
                if (status.equalsIgnoreCase("success")) {

                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                    book_id = new String[ja1.length()];
                    slot_id = new String[ja1.length()];
                    turf_id = new String[ja1.length()];

                    owner_name = new String[ja1.length()];
                    day = new String[ja1.length()];
                    from_time = new String[ja1.length()];
                    to_time = new String[ja1.length()];
                    booking_status = new String[ja1.length()];
                    amount = new String[ja1.length()];


                    val = new String[ja1.length()];


                    for (int i = 0; i < ja1.length(); i++) {

                        book_id[i] = ja1.getJSONObject(i).getString("book_id");
                        slot_id[i] = ja1.getJSONObject(i).getString("slot_id");
                        turf_id[i] = ja1.getJSONObject(i).getString("turf_id");
                        owner_name[i] = ja1.getJSONObject(i).getString("owner_name");
                        day[i] = ja1.getJSONObject(i).getString("day");
                        from_time[i] = ja1.getJSONObject(i).getString("from_time");
                        to_time[i] = ja1.getJSONObject(i).getString("to_time");
                        booking_status[i] = ja1.getJSONObject(i).getString("status");
                        amount[i] = ja1.getJSONObject(i).getString("amount");


//                        Toast.makeText(getApplicationContext(), val[i], Toast.LENGTH_SHORT).show();
                        val[i] = "Turf Owner : " + owner_name[i] + "\nDay : " + day[i] + "\nFrom Time : " + from_time[i] + "\nTo Time : " + to_time[i] + "\nAmount : " + amount[i];


                    }
                    ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), R.layout.cust_list, val);
                    lv1.setAdapter(ar);


                } else {
                    Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();

                }
            }
        } catch (Exception e) {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        book_ids = book_id[arg2];
        booking_statuss = booking_status[arg2];
        amounts=amount[arg2];

        if (booking_statuss.equalsIgnoreCase("pending")) {

            final CharSequence[] items = {"Payment", "Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(Bookings.this);
            // builder.setTitle("Add Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (items[item].equals("Payment")) {
//                    JsonReq JR=new JsonReq();
//                    JR.json_response=(JsonResponse) Bookings.this;
//                    String q = "/User_Send_feedback?book_ids="+Bookings.book_ids+"&loginid="+Login.logid;
//                    q=q.replace(" ","%20");
//                    JR.execute(q);
                        startActivity(new Intent(getApplicationContext(), Payment.class));


                    }else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }

            });
            builder.show();
//	Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            //startActivityForResult(i, GALLERY_CODE);
        }

    else

    {

        final CharSequence[] items = {"Schedule Matches", "Send Feedback", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Bookings.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Send Feedback")) {
//                    JsonReq JR=new JsonReq();
//                    JR.json_response=(JsonResponse) Bookings.this;
//                    String q = "/User_Send_feedback?book_ids="+Bookings.book_ids+"&loginid="+Login.logid;
//                    q=q.replace(" ","%20");
//                    JR.execute(q);
                    startActivity(new Intent(getApplicationContext(), User_Send_feedback.class));
                } else if (items[item].equals("Schedule Matches")) {
                    startActivity(new Intent(getApplicationContext(), ScheduleMatches.class));
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }

        });
        builder.show();
//	Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //startActivityForResult(i, GALLERY_CODE);
    }

}
}
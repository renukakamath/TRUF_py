package com.example.turfbooking;

import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class TurfGallery extends AppCompatActivity implements JsonResponse {
    GridView gv_gallery;
    String[] wpic, name, date;
    ListView l1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turf_gallery);
        l1 =  findViewById(R.id.lv1);

        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) TurfGallery.this;
        String q = "/turfgallery";
        q = q.replace(" ", "%20");
        JR.execute(q);
    }


    public void response(JSONObject jo) {
        // TODO Auto-generated method stub
        try {
            String method = jo.getString("method");
            if (method.equalsIgnoreCase("turfgallery")) {
                String status = jo.getString("status");
                Log.d("pearl", status);
                Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
                if (status.equalsIgnoreCase("success")) {
                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");
//                    aid=new String[ja1.length()];

                    wpic=new String[ja1.length()];
                    name=new String[ja1.length()];
                    date=new String[ja1.length()];

                    for(int i = 0;i<ja1.length();i++)
                    {
                        wpic[i] = ja1.getJSONObject(i).getString("image");
                        name[i] = ja1.getJSONObject(i).getString("name");
                        date[i] = ja1.getJSONObject(i).getString("date_time");

                    }

                    Custtimage cc=new Custtimage(this,wpic,name,date);
                    l1.setAdapter(cc);
//                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");





                } else {
                    Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();

                }
            }


        } catch (Exception e) {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

    }
}
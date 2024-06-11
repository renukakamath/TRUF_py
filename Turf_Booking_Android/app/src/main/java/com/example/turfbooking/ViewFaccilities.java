package com.example.turfbooking;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class ViewFaccilities extends AppCompatActivity implements JsonResponse {
    ListView lv1;
    String[] facility_id,facility,description,image,val;
    public static String facility_ids;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_faccilities);
        lv1=(ListView)findViewById(R.id.lv1);

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) ViewFaccilities.this;
        String q = "/ViewFacilities?turf_ids="+ViewTurf.turf_ids;
        q=q.replace(" ","%20");
        JR.execute(q);
    }

    public void response(JSONObject jo) {
        // TODO Auto-generated method stub
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("ViewFacilities")){
                String status=jo.getString("status");
                Log.d("pearl",status);
                Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
                if(status.equalsIgnoreCase("success")){

                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");
                    facility_id=new String[ja1.length()];
                    facility=new String[ja1.length()];
                    description=new String[ja1.length()];
                    image=new String[ja1.length()];



                    val=new String[ja1.length()];



                    for(int i = 0;i<ja1.length();i++)
                    {

                        facility_id[i]=ja1.getJSONObject(i).getString("facility_id");
                        facility[i]=ja1.getJSONObject(i).getString("facility");
                        description[i]=ja1.getJSONObject(i).getString("description");



//                        Toast.makeText(getApplicationContext(),val[i], Toast.LENGTH_SHORT).show();
                        val[i]="Facility : "+facility[i]+"\nDescription : "+description[i];


                    }
                    ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),R.layout.cust_list,val);
                    lv1.setAdapter(ar);



                }

                else {
                    Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();

                }
            }
        }catch (Exception e)
        {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
        }}
}
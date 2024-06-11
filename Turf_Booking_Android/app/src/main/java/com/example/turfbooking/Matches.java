package com.example.turfbooking;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class Matches extends AppCompatActivity implements JsonResponse {
    ListView lv1;
    String [] turf,owner_name,turf_place,from_time,to_time,date_time,user_name,phone,email,val,day;
    public static String turf_ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);
        lv1=(ListView)findViewById(R.id.lv1);



        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) Matches.this;
        String q = "/MyMatches?login_id="+ Login.logid;
        q=q.replace(" ","%20");
        JR.execute(q);
    }
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("MyMatches")){
                String status=jo.getString("status");
                Log.d("pearl",status);
                Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
                if(status.equalsIgnoreCase("success")){

                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");
                    turf_place=new String[ja1.length()];
                    owner_name=new String[ja1.length()];
                    day=new String[ja1.length()];
                    date_time=new String[ja1.length()];


                    user_name=new String[ja1.length()];
                    from_time=new String[ja1.length()];
                    to_time=new String[ja1.length()];
                    email=new String[ja1.length()];
                    phone=new String[ja1.length()];




                    val=new String[ja1.length()];



                    for(int i = 0;i<ja1.length();i++)
                    {

                        turf_place[i]=ja1.getJSONObject(i).getString("turf_place");
                        owner_name[i]=ja1.getJSONObject(i).getString("owner_name");
                        date_time[i]=ja1.getJSONObject(i).getString("date_time");
                        user_name[i]=ja1.getJSONObject(i).getString("user_name");

                        day[i]=ja1.getJSONObject(i).getString("day");
                        from_time[i]=ja1.getJSONObject(i).getString("from_time");
                        to_time[i]=ja1.getJSONObject(i).getString("to_time");
                        email[i]=ja1.getJSONObject(i).getString("email");
                        phone[i]=ja1.getJSONObject(i).getString("phone");



//                        Toast.makeText(getApplicationContext(),val[i], Toast.LENGTH_SHORT).show();
                        val[i]="Turf Owner : "+owner_name[i]+"\nDay : "+day[i]+"\nFrom Time : "+from_time[i]+"\nTo Time : "+to_time[i]+"\nDate : "+date_time[i]+"\n Against Player : "+user_name[i]+"\nEmail : "+email[i];


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
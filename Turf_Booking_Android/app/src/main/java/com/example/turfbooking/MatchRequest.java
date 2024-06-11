package com.example.turfbooking;

import android.content.DialogInterface;
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

public class MatchRequest extends AppCompatActivity implements JsonResponse,AdapterView.OnItemClickListener {
    ListView lv1;
    String [] match_id,turf,owner_name,turf_place,from_time,to_time,date_time,user_name,phone,email,val,day;
    public static String match_ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_request);

        lv1=(ListView)findViewById(R.id.lv1);

        lv1.setOnItemClickListener(this);



        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) MatchRequest.this;
        String q = "/MatchRequests?login_id="+ Login.logid;
        q=q.replace(" ","%20");
        JR.execute(q);
    }
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("MatchRequests")){
                String status=jo.getString("status");
                Log.d("pearl",status);
                Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
                if(status.equalsIgnoreCase("success")){

                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");
                    match_id=new String[ja1.length()];
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
                        match_id[i]=ja1.getJSONObject(i).getString("match_id");
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
                        val[i]="Turf Owner : "+owner_name[i]+"\nDay : "+day[i]+"\nFrom Time : "+from_time[i]+"\nTo Time : "+to_time[i]+"\nDate : "+date_time[i]+"\nAgainst Player : "+user_name[i]+"\nEmail : "+email[i];


                    }
                    ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),R.layout.cust_list,val);
                    lv1.setAdapter(ar);



                }

                else {
                    Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();

                }
            }
            if(method.equalsIgnoreCase("MatchAccept")){
                String status=jo.getString("status");
                Log.d("pearl",status);
                Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
                if(status.equalsIgnoreCase("success")){
                    Toast.makeText(getApplicationContext(), "Request Accepted ", Toast.LENGTH_LONG).show();



                    }
                    ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,val);
                    lv1.setAdapter(ar);



                }

                else {
                    Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();

                }
            if(method.equalsIgnoreCase("MatchReject")){
                String status=jo.getString("status");
                Log.d("pearl",status);
                Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
                if(status.equalsIgnoreCase("success")){
                    Toast.makeText(getApplicationContext(), "Request Rejected ", Toast.LENGTH_LONG).show();



                }
                ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,val);
                lv1.setAdapter(ar);



            }

            else {
                Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();

            }
            }

        catch (Exception e)
        {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
        }}



    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        match_ids=match_id[arg2];

        final CharSequence[] items = {"Accept","Reject","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(MatchRequest.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Accept"))
                {
                    JsonReq JR=new JsonReq();
                    JR.json_response=(JsonResponse) MatchRequest.this;
                    String q = "/MatchAccept?match_id="+MatchRequest.match_ids;
                    q=q.replace(" ","%20");
                    JR.execute(q);
                }
                else if (items[item].equals("Reject"))
                {
                    JsonReq JR=new JsonReq();
                    JR.json_response=(JsonResponse) MatchRequest.this;
                    String q = "/MatchReject?match_id="+MatchRequest.match_ids;
                    q=q.replace(" ","%20");
                    JR.execute(q);
                }

                else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }

        });
        builder.show();
//	Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //startActivityForResult(i, GALLERY_CODE);
    }
}
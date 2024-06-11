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

public class ScheduleMatches extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView lv1;
    String [] book_id,user_id,name,phone,email,val;
    public static String opp_user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_matches);
        lv1=(ListView)findViewById(R.id.lv1);
        lv1.setOnItemClickListener(this);


        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) ScheduleMatches.this;
        String q = "/viewusers?loginid="+Login.logid;
        q=q.replace(" ","%20");
        JR.execute(q);

    }
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("viewusers")){
                String status=jo.getString("status");
                Log.d("pearl",status);
                Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
                if(status.equalsIgnoreCase("success")){

                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");

                    user_id=new String[ja1.length()];
                    name=new String[ja1.length()];

                    phone=new String[ja1.length()];
                    email=new String[ja1.length()];




                    val=new String[ja1.length()];



                    for(int i = 0;i<ja1.length();i++)
                    {


                        user_id[i]=ja1.getJSONObject(i).getString("user_id");
                        name[i]=ja1.getJSONObject(i).getString("name");

                        phone[i]=ja1.getJSONObject(i).getString("phone");
                        email[i]=ja1.getJSONObject(i).getString("email");
//                        photo[i]=ja1.getJSONObject(i).getString("photo");
//                        place[i]=ja1.getJSONObject(i).getString("place");


//                        Toast.makeText(getApplicationContext(),val[i], Toast.LENGTH_SHORT).show();
                        val[i]="Name : "+name[i]+"\nPhone : "+phone[i]+"\nEmail : "+email[i];


                    }
                    ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,val);
                    lv1.setAdapter(ar);



                }


                else {
                    Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();

                }
            }
			if(method.equalsIgnoreCase("ScheduleMatches"))
			{
				String status=jo.getString("status");
				Toast.makeText(getApplicationContext(),status, Toast.LENGTH_LONG).show();
				if(status.equalsIgnoreCase("success"))
				{
					Toast.makeText(getApplicationContext(),"Match Sheduled!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),Matches.class));
				}
				else{
					Toast.makeText(getApplicationContext(),"Alredy Scheduled", Toast.LENGTH_LONG).show();
				}
			}
        }catch (Exception e)
        {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
        }



    }
    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        opp_user_id=user_id[arg2];

        final CharSequence[] items = {"Set Match","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ScheduleMatches.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Set Match"))
                {
                    JsonReq JR=new JsonReq();
                    JR.json_response=(JsonResponse) ScheduleMatches.this;
                    String q = "/ScheduleMatch?book_id="+Bookings.book_ids+"&loginid="+Login.logid+"&opp_user_id="+opp_user_id;
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
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

public class ViewSlots extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView lv1;
    String[] slot_id,day,from_time,to_time,amount,val;
    public static String slot_ids;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_slots);
        lv1=(ListView)findViewById(R.id.lv1);
        lv1.setOnItemClickListener(this);

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) ViewSlots.this;
        String q = "/ViewSlots?turf_ids="+ ViewTurf.turf_ids;
        q=q.replace(" ","%20");
        JR.execute(q);
    }




    @Override
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub
        try{
            String method=jo.getString("method");
            if(method.equalsIgnoreCase("BookSlot")){
                String status=jo.getString("status");
                Log.d("pearl",status);
                //Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
                if(status.equalsIgnoreCase("success")){

                    Toast.makeText(getApplicationContext(), " SENT", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Bookings.class));
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Something went wrong!Try Again.", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),ViewSlots.class));
                }
            }
            if(method.equalsIgnoreCase("ViewSlots")){
                String status=jo.getString("status");
                Log.d("pearl",status);


                if(status.equalsIgnoreCase("success")){

                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");
                    slot_id=new String[ja1.length()];
                    day=new String[ja1.length()];
                    from_time=new String[ja1.length()];
                    to_time=new String[ja1.length()];
                    amount=new String[ja1.length()];


                    val=new String[ja1.length()];



                    for(int i = 0;i<ja1.length();i++)
                    {

                        slot_id[i]=ja1.getJSONObject(i).getString("slot_id");
                        day[i]=ja1.getJSONObject(i).getString("day");
                        from_time[i]=ja1.getJSONObject(i).getString("from_time");
                        to_time[i]=ja1.getJSONObject(i).getString("to_time");
                        amount[i]=ja1.getJSONObject(i).getString("amount");


//                        Toast.makeText(getApplicationContext(),val[i], Toast.LENGTH_SHORT).show();
                        val[i]="Day : "+day[i]+"\nFrom Time : "+from_time[i]+"\nTo Time : "+to_time[i]+"\nAmount : "+amount[i];


                    }
                    ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),R.layout.cust_list,val);
                    lv1.setAdapter(ar);



                }


                else

                {
                    Toast.makeText(getApplicationContext(), "No Data!!", Toast.LENGTH_LONG).show();

                }
            }

        }catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }


    }




    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        slot_ids=slot_id[arg2];

        final CharSequence[] items = {"Book Slot","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ViewSlots.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Book Slot"))
                {
                    JsonReq JR=new JsonReq();
                    JR.json_response=(JsonResponse) ViewSlots.this;
                    String q = "/BookSlot?slot_ids="+ViewSlots.slot_ids+"&loginid="+ Login.logid;
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
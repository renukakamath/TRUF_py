package com.example.turfbooking;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

public class ViewTurf extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView lv1;
    String [] turf_id,owner_name,turf_place,latitude,longitude,phone,email,val,loginid;
    public static String turf_ids,receiver_id,lati,longi;
    SharedPreferences sh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_turf);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        lv1=(ListView)findViewById(R.id.lv1);
        lv1.setOnItemClickListener(this);


        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) ViewTurf.this;
        String q = "/ViewTurf?latitude="+ LocationService.lati+"&logitude="+ LocationService.logi;
        q=q.replace(" ","%20");
        JR.execute(q);
    }


    public void response(JSONObject jo) {
        // TODO Auto-generated method stub
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("ViewTurf")){
                String status=jo.getString("status");
                Log.d("pearl",status);
                Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
                if(status.equalsIgnoreCase("success")){

                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");

                    turf_id=new String[ja1.length()];
                    loginid=new String[ja1.length()];
                    owner_name=new String[ja1.length()];
                    turf_place=new String[ja1.length()];
                    latitude=new String[ja1.length()];
                    longitude=new String[ja1.length()];
                    phone=new String[ja1.length()];
                    email=new String[ja1.length()];
                    phone=new String[ja1.length()];



                    val=new String[ja1.length()];



                    for(int i = 0;i<ja1.length();i++)
                    {


                        turf_id[i]=ja1.getJSONObject(i).getString("turf_id");
                        loginid[i]=ja1.getJSONObject(i).getString("login_id");
                        owner_name[i]=ja1.getJSONObject(i).getString("owner_name");
                        turf_place[i]=ja1.getJSONObject(i).getString("turf_place");
                        latitude[i]=ja1.getJSONObject(i).getString("latitude");
                        longitude[i]=ja1.getJSONObject(i).getString("longitude");
                        phone[i]=ja1.getJSONObject(i).getString("phone");
                        email[i]=ja1.getJSONObject(i).getString("email");
//                        photo[i]=ja1.getJSONObject(i).getString("photo");
//                        place[i]=ja1.getJSONObject(i).getString("place");


//                        Toast.makeText(getApplicationContext(),val[i], Toast.LENGTH_SHORT).show();
                        val[i]="OwnerName : "+owner_name[i]+"\nPlace : "+turf_place[i]+"\nLatitude : "+latitude[i]+"\nLongitude : "+longitude[i]+"\nPhone : "+phone[i]+"\nEmail : "+email[i];


                    }
                    ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),R.layout.cust_list,val);
                    lv1.setAdapter(ar);



                }

                else {
                    Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();

                }
            }
//			if(method.equalsIgnoreCase("buyprod"))
//			{
//				String status=jo.getString("status");
//				Toast.makeText(getApplicationContext(),status, Toast.LENGTH_LONG).show();
//				if(status.equalsIgnoreCase("success"))
//				{
//					Toast.makeText(getApplicationContext(),"Your order is submitted!", Toast.LENGTH_LONG).show();
//				}
//				else{
//					Toast.makeText(getApplicationContext(),"Your order is not submitted", Toast.LENGTH_LONG).show();
//				}
//			}
        }catch (Exception e)
        {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
        }



    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        turf_ids=turf_id[arg2];
        receiver_id=loginid[arg2];
        lati=latitude[arg2];
        longi=longitude[arg2];
        SharedPreferences.Editor e=sh.edit();
        e.putString("receiver_id",receiver_id);
        e.putString("sender_type","user");
        e.putString("receiver_type","truf");
        e.commit();


        final CharSequence[] items = {"View Location","View slots and amounts","View Faccilities","Rate & Review","Chat","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ViewTurf.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("View Location"))
                {
                    String url = "http://www.google.com/maps?saddr="+LocationService.lati+""+","+LocationService.logi+""+"&&daddr="+lati+","+longi;
                    Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(in);
                }
                else if (items[item].equals("View slots and amounts"))
                {
                    startActivity(new Intent(getApplicationContext(),ViewSlots.class));
                }
                else if (items[item].equals("View Faccilities"))
                {
                    startActivity(new Intent(getApplicationContext(), ViewFaccilities.class));
                }
                else if (items[item].equals("Rate & Review"))
                {
                    startActivity(new Intent(getApplicationContext(), Review.class));
                }
                else if (items[item].equals("Chat"))
                {
                    startActivity(new Intent(getApplicationContext(), ChatHere.class));
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
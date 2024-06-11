package com.example.turfbooking;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class Userviewcoaches extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    String[] loginid,cid,name,place,email,phone,feeamount,sporttype,value;
    SharedPreferences sh;
    public static String cids,receiver_id,amounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userviewcoaches);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        l1=(ListView)findViewById(R.id.lvview);
        l1.setOnItemClickListener(this);
        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) Userviewcoaches.this;
        String q = "/userviewcoaches?tid="+ViewTurf.turf_ids;
        q=q.replace(" ","%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {

        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("usersendrequesttocoach")) {

                String status = jo.getString("status");
                Log.d("pearl", status);


                if (status.equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(), "SENDED SUCCESSFULLY", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Userviewcoaches.class));

                } else {

                    Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
                }
            }
            else if(method.equalsIgnoreCase("userviewcoaches"))
            {
                String status=jo.getString("status");
                Log.d("pearl",status);


                if(status.equalsIgnoreCase("success")){
                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");
                    cid=new String[ja1.length()];
                    loginid=new String[ja1.length()];
                    name=new String[ja1.length()];
                    place=new String[ja1.length()];
                    phone=new String[ja1.length()];
                    email=new String[ja1.length()];
                    feeamount=new String[ja1.length()];
                    sporttype=new String[ja1.length()];
                    value=new String[ja1.length()];

                    for(int i = 0;i<ja1.length();i++)
                    {
                        cid[i]=ja1.getJSONObject(i).getString("coach_id");
                        loginid[i]=ja1.getJSONObject(i).getString("login_id");
                        name[i]=ja1.getJSONObject(i).getString("fname")+" "+ja1.getJSONObject(i).getString("lname");
                        place[i]=ja1.getJSONObject(i).getString("place");
                        phone[i]=ja1.getJSONObject(i).getString("phone");
                        email[i]=ja1.getJSONObject(i).getString("email");
                        feeamount[i]=ja1.getJSONObject(i).getString("fee_amount");
                        sporttype[i]=ja1.getJSONObject(i).getString("sports_type");
                      //  name[i]=ja1.getJSONObject(i).getString("date");
                        value[i]="Coach Name : "+name[i]+"\nPlace : "+place[i]+"\nPhone : "+phone[i]+"\nEmail : "+email[i]+"\nSports Type : "+sporttype[i]+"\nFee Amount : "+feeamount[i];

                    }
                    ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,value);
                    l1.setAdapter(ar);
                }
            }

        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        cids=cid[position];
        receiver_id=loginid[position];
        SharedPreferences.Editor e=sh.edit();
        e.putString("receiver_id",receiver_id);
        e.putString("sender_type","user");
        e.putString("receiver_type","coach");
        e.commit();

        final CharSequence[] items = {"Send Request","View Request","Chat","View Trainings","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Userviewcoaches.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Send Request"))
                {
                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) Userviewcoaches.this;
                    String q = "/usersendrequesttocoach?lid=" + sh.getString("logid", "")+"&cid="+Userviewcoaches.cids;
                    q = q.replace(" ", "%20");
                    JR.execute(q);
                }
                else  if (items[item].equals("View Request"))
                {
                    startActivity(new Intent(getApplicationContext(), Userviewcoachrequest.class));
                }
                else if (items[item].equals("Chat"))
                {
                    startActivity(new Intent(getApplicationContext(), ChatHere.class));
                }
                else if (items[item].equals("View Trainings"))
                {
                    startActivity(new Intent(getApplicationContext(),Userviewtrainings.class));
                }
//                else if (items[item].equals("View Videos"))
//                {
//                    startActivity(new Intent(getApplicationContext(),Review.class));
//                }


                else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }

        });
        builder.show();
    }
//    public void onBackPressed()
//    {
//        // TODO Auto-generated method stub
//        super.onBackPressed();
//        Intent b=new Intent(getApplicationContext(),Userhome.class);
//        startActivity(b);
//    }
}
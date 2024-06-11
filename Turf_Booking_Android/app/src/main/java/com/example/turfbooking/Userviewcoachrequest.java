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

public class Userviewcoachrequest extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    SharedPreferences sh;
    ListView l1;
    String[] feeamount,statuss,name,value,sporttype,rid;
    public static String amounts,rids,stat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userviewcoachrequest);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        l1=(ListView)findViewById(R.id.lvview);
        l1.setOnItemClickListener(this);
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Userviewcoachrequest.this;
        String q = "/userviewrequesttocoach?lid=" + sh.getString("logid", "")+"&cid="+Userviewcoaches.cids;
        q = q.replace(" ", "%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {
        try {


                String status=jo.getString("status");
                Log.d("pearl",status);


                if(status.equalsIgnoreCase("success")){
                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");
                    rid=new String[ja1.length()];
                    statuss=new String[ja1.length()];
                    name=new String[ja1.length()];
//                    place=new String[ja1.length()];
//                    phone=new String[ja1.length()];
//                    email=new String[ja1.length()];
                    feeamount=new String[ja1.length()];
                    sporttype=new String[ja1.length()];
                    value=new String[ja1.length()];

                    for(int i = 0;i<ja1.length();i++)
                    {
                        rid[i]=ja1.getJSONObject(i).getString("request_id");
                       // loginid[i]=ja1.getJSONObject(i).getString("login_id");
                        name[i]=ja1.getJSONObject(i).getString("fname")+" "+ja1.getJSONObject(i).getString("lname");
//                        place[i]=ja1.getJSONObject(i).getString("place");
//                        phone[i]=ja1.getJSONObject(i).getString("phone");
                        statuss[i]=ja1.getJSONObject(i).getString("status");
                        feeamount[i]=ja1.getJSONObject(i).getString("fee_amount");
                        sporttype[i]=ja1.getJSONObject(i).getString("sports_type");
                        //  name[i]=ja1.getJSONObject(i).getString("date");
                        value[i]="Coach Name : "+name[i]+"\nSports Type : "+sporttype[i]+"\nFee Amount : "+feeamount[i]+"\nStatuss : "+statuss[i];

                    }
                    ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,value);
                    l1.setAdapter(ar);
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
        amounts=feeamount[position];
        rids=rid[position];
        stat=statuss[position];
        if(stat.equalsIgnoreCase("accepted"))
        {
            final CharSequence[] items = {"Payment","Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(Userviewcoachrequest.this);
            // builder.setTitle("Add Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int item) {

                    if (items[item].equals("Payment"))
                    {
                        startActivity(new Intent(getApplicationContext(),Usermakepayment.class));
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
    }
}
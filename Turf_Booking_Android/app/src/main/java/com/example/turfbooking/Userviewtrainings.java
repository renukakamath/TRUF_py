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

public class Userviewtrainings extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    String[] tid,description,datetime,value;
    public static String tids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userviewtrainings);
        l1=(ListView)findViewById(R.id.lvview);
        l1.setOnItemClickListener(this);
        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) Userviewtrainings.this;
        String q = "/userviewtrainings?cid="+ Userviewcoaches.cids;
        q=q.replace(" ","%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {
        try {


                String status=jo.getString("status");
                Log.d("pearl",status);


                if(status.equalsIgnoreCase("success")){
                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");
                    tid=new String[ja1.length()];
                    description=new String[ja1.length()];
                    datetime=new String[ja1.length()];
//                    place=new String[ja1.length()];
//                    phone=new String[ja1.length()];
//                    email=new String[ja1.length()];
//                    feeamount=new String[ja1.length()];
//                    sporttype=new String[ja1.length()];
                    value=new String[ja1.length()];

                    for(int i = 0;i<ja1.length();i++)
                    {

                        //  tid[i]=ja1.getJSONObject(i).getString("training_id");
//                        loginid[i]=ja1.getJSONObject(i).getString("login_id");
//                        name[i]=ja1.getJSONObject(i).getString("fname")+" "+ja1.getJSONObject(i).getString("lname");
//                        place[i]=ja1.getJSONObject(i).getString("place");
//                        phone[i]=ja1.getJSONObject(i).getString("phone");
                        description[i]=ja1.getJSONObject(i).getString("description");
                        datetime[i]=ja1.getJSONObject(i).getString("datetime");
                        tid[i]=ja1.getJSONObject(i).getString("training_id");
                        //  name[i]=ja1.getJSONObject(i).getString("date");
                        value[i]="Description : "+description[i]+"\nDatetime : "+datetime[i];

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
        tids=tid[position];
        final CharSequence[] items = {"View Videos","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Userviewtrainings.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int item) {

              if (items[item].equals("View Videos"))
                {
                    startActivity(new Intent(getApplicationContext(), Videos.class));
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
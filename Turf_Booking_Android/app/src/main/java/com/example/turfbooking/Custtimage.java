package com.example.turfbooking;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Custtimage extends ArrayAdapter<String>  {

	 private Activity context;       //for to get current activity context
	    SharedPreferences sh;
	private String[] file;


	private String[] name;
	private String[] date;

	
	 public Custtimage(Activity context, String[] file,String[] name,String[] date){
	        //constructor of this class to get the values from main_activity_class

	        super(context, R.layout.custt_images, file);
	        this.context = context;
	        this.file = file;
		 this.name = name;
		 this.date = date;

//		    this.bikenum = bikenum;
//		    this.phone = phone;
//		    this.email = email;


	    }

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	                 //override getView() method

	        LayoutInflater inflater = context.getLayoutInflater();
	        View listViewItem = inflater.inflate(R.layout.custt_images, null, true);
			//cust_list_view is xml file of layout created in step no.2

	        ImageView im = (ImageView) listViewItem.findViewById(R.id.imageView1);


			TextView t1=(TextView)listViewItem.findViewById(R.id.textView1);
			TextView t10=(TextView)listViewItem.findViewById(R.id.textView2);
			t1.setText(name[position]);
//			t2.setText(date[position]);
			t10.setText(date[position]);


	        sh=PreferenceManager.getDefaultSharedPreferences(getContext());
	        
	       String pth = "http://"+sh.getString("ip", "")+"/"+file[position];
	       pth = pth.replace("~", "");
//	       Toast.makeText(context, pth, Toast.LENGTH_LONG).show();
	        
	        Log.d("-------------", pth);
	        Picasso.with(context)
	                .load(pth)
	                .placeholder(R.drawable.ic_launcher_background)
	                .error(R.drawable.ic_launcher_background).into(im);
	        
	        return  listViewItem;
	    }

		private TextView setText(String string) {
			// TODO Auto-generated method stub
			return null;
		}
}
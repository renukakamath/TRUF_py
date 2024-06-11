package com.example.turfbooking;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class Videos extends Activity implements OnItemClickListener, JsonResponse {

	ListView lv1;
	String [] photo;
	public static String hot_id,qpath,typeid,types,fids,files;
	String[] videoid,childnamee,videonamee,detail,places,fid,type,date;
	String dwld_path,path1;

	ProgressDialog mProgressDialog;


	SharedPreferences sh;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_videos);
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

	
		
		lv1=(ListView)findViewById(R.id.ltvideo);
		lv1.setOnItemClickListener(this);
		
//		if(sh.getString("val", "").equalsIgnoreCase("public"))
//		{
//		 //TODO Auto-generated method stub
//			JsonReq JR=new JsonReq();
//	        JR.json_response=(JsonResponse) Videos.this;
//	        String q = "/view_videos?";
//	        q=q.replace(" ","%20");
//	        JR.execute(q);
//		}
//		else if(sh.getString("val", "").equalsIgnoreCase("user"))
//		{
			JsonReq JR=new JsonReq();
	        JR.json_response=(JsonResponse) Videos.this;
	        String q = "/userview_videos?tid="+Userviewtrainings.tids;
	        q=q.replace(" ","%20");
	        JR.execute(q);
//		}
		
		
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

		fids=fid[arg2];
		files=videonamee[arg2];
//		types=type[arg2];
		qpath=videonamee[arg2];

			final CharSequence[] items = {"Play Video", "Cancel"};

			AlertDialog.Builder builder = new AlertDialog.Builder(Videos.this);
			builder.setTitle("Select Any!");
			builder.setItems(items, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int item) {
					if (items[item].equals("Play Video")) {
						// TODO Auto-generated method stub
						Intent in = new Intent(getApplicationContext(), VideoPlay.class);
						in.putExtra("vdo", videonamee[arg2]);
						startActivity(in);
						qpath = videonamee[arg2];

					}
//					if (items[item].equals("View Images")) {
//						startActivity(new Intent(getApplicationContext(), customerviewimages.class));
//
//					}
					else if (items[item].equals("Cancel")) {
						dialog.dismiss();
					}
				}
			});
			builder.show();
		}
//		else if(files.equalsIgnoreCase(".jpg"))
//		{
//			final CharSequence[] items = {"View Images","Cancel"};
//
//			AlertDialog.Builder builder = new AlertDialog.Builder(Videos.this);
//			builder.setTitle("Select Any!");
//			builder.setItems(items, new DialogInterface.OnClickListener() {
//				@Override
//				public void onClick(DialogInterface dialog, int item) {
////					if (items[item].equals("Play Video")) {
////						// TODO Auto-generated method stub
////						Intent in = new Intent(getApplicationContext(), VideoPlay.class);
////						in.putExtra("vdo", videonamee[arg2]);
////						startActivity(in);
////						qpath = videonamee[arg2];
////
////					}
////					if (items[item].equals("View Images")) {
////						startActivity(new Intent(getApplicationContext(), customerviewimages.class));
////
////					}
//					else if (items[item].equals("Cancel")) {
//						dialog.dismiss();
//					}
//				}
//			});
//			builder.show();
//		}


	    void downloadFile() {
		// declare the dialog as a member field of your activity
//        ProgressDialog mProgressDialog;

		// instantiate it within the onCreate method
		mProgressDialog = new ProgressDialog(Videos.this);
		mProgressDialog.setMessage("A message");
		mProgressDialog.setIndeterminate(true);
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		mProgressDialog.setCancelable(true);

		// execute this when the downloader must be fired
		final DownloadTask downloadTask = new DownloadTask(getApplicationContext()); //YourActivity.this
		downloadTask.execute(dwld_path);

		mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				downloadTask.cancel(true);
			}
		});
	}

	// usually, subclasses of AsyncTask are declared inside the activity class.
	// that way, you can easily modify the UI thread from here
	private class DownloadTask extends AsyncTask<String, Integer, String> {

		private Context context;
		private PowerManager.WakeLock mWakeLock;

		public DownloadTask(Context context) {
			this.context = context;
		}

		@Override
		protected String doInBackground(String... sUrl) {
			InputStream input = null;
			OutputStream output = null;
			HttpURLConnection connection = null;
			try {
				URL url = new URL(sUrl[0]);
				connection = (HttpURLConnection) url.openConnection();
				connection.connect();

				// expect HTTP 200 OK, so we don't mistakenly save error report
				// instead of the file
				if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
					return "Server returned HTTP " + connection.getResponseCode()
							+ " " + connection.getResponseMessage();
				}

				// this will be useful to display download percentage
				// might be -1: server did not report the length
				int fileLength = connection.getContentLength();

				// download the file
				input = connection.getInputStream();
				output = new FileOutputStream("/sdcard/"+ qpath);

				byte data[] = new byte[4096];
				long total = 0;
				int count;
				while ((count = input.read(data)) != -1) {
					// allow canceling with back button
					if (isCancelled()) {
						input.close();
						return null;
					}
					total += count;
					// publishing the progress....
					if (fileLength > 0) // only if total length is known
						publishProgress((int) (total * 100 / fileLength));
					output.write(data, 0, count);
				}
			} catch (Exception e) {
				return e.toString();
			} finally {
				try {
					if (output != null)
						output.close();
					if (input != null)
						input.close();
				} catch (IOException ignored) {
				}

				if (connection != null)
					connection.disconnect();
			}
			return null;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			// take CPU lock to prevent CPU from going off if the user
			// presses the power button during download
			PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
			mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
					getClass().getName());
			mWakeLock.acquire();
			mProgressDialog.show();
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);

			// if we get here, length is known, now set indeterminate to false
			mProgressDialog.setIndeterminate(false);
			mProgressDialog.setMax(100);
			mProgressDialog.setProgress(values[0]);
		}

		@Override
		protected void onPostExecute(String s) {

			mWakeLock.release();
			mProgressDialog.dismiss();
			if (s != null)
				Toast.makeText(context,"Download error : "+s, Toast.LENGTH_LONG).show();
			else
				Toast.makeText(context,"File downloaded", Toast.LENGTH_SHORT).show();
		}

	}


	@Override
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
try {
			
//			String method=jo.getString("method");
//			if(method.equalsIgnoreCase("public_view_videos")){
			String status=jo.getString("status");
			Log.d("pearl",status);
			Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
			if(status.equalsIgnoreCase("success")){
			
				JSONArray ja1=(JSONArray)jo.getJSONArray("data");
//
				fid=new String[ja1.length()];
				type=new String[ja1.length()];
//				date=new String[ja1.length()];
				videonamee=new String[ja1.length()];
				detail=new String[ja1.length()];

				
			     
				for(int i = 0;i<ja1.length();i++)
				{


					fid[i]=ja1.getJSONObject(i).getString("video_id");
                    type[i]=ja1.getJSONObject(i).getString("title");
//					date[i]=ja1.getJSONObject(i).getString("date");
					videonamee[i]=ja1.getJSONObject(i).getString("file_path");
					detail[i]="File: "+videonamee[i]+"\nTitle: "+type[i];
					
				
				}
//				Custimage clist=new Custimage(this,photo);
//				 lv1.setAdapter(clist);
				
				ArrayAdapter<String> ar= new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,detail);
				lv1.setAdapter(ar);
		      
		       
			}
			
			else {
				Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();
	
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

//	@Override

//	public void onBackPressed()
//	{
//
//        // TODO Auto-generated method stub
//        super.onBackPressed();
//        Intent b=new Intent(getApplicationContext(),Userhome.class);
//        startActivity(b);
//
//    }

}

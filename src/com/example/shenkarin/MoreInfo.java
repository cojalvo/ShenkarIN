//package com.example.shenkarin;
//
//
//import com.parse.ParseException;
//import com.parse.ParseObject;
//import com.parse.ParseUser;
//import com.parse.SaveCallback;
//
//import android.app.Activity;
//import android.app.ProgressDialog;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.RadioButton;
//import android.widget.Spinner;
//
//public class MoreInfo
//{
//	private Spinner trendSpinner=null;
//	private Spinner yearSpinner=null;
//	private RadioButton displayRB=null;
//	private Button submit=null;
//	private ProgressDialog pd=null;
//	
//	 
//	   
//	   /**
//	    * show the progress dialog with a specific message 
//	    * @param message
//	    */
//		private void  showProgressDialog(String message)
//		{
//			pd = new ProgressDialog(MoreInfo.this);
//			pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//			pd.setMessage(message);
//			pd.show();
//		}
//		
//		/**
//		 * closing the progress dialog
//		 */
//		private void closeProgressDialog()
//		{
//			pd.cancel();
//		}
//		
//		/**
//		 * initialize  the UI controllers
//		 */
//	   private void initialize()
//	   {
//		   submit=(Button) findViewById(R.id.btnSubmit);
//		   trendSpinner=(Spinner) findViewById(R.id.spinner1);
//		   yearSpinner=(Spinner) findViewById(R.id.spinner2);
//		   displayRB=(RadioButton) findViewById(R.id.displayMyFriendRB);
//		   
//	   }
//	   /***
//	    * create the user settings object and save it to the cloud
//	    * when saving the data a progress dialog will appear, this pd will close
//	    * only after the completed data will be save
//	    */
//	   private void createUserSettingParseObject()
//	   {
//		   ParseObject po=new ParseObject("UserSettings");
//		   po.add("trend",String.valueOf(trendSpinner.getSelectedItem()));
//		   po.add("year",String.valueOf(yearSpinner.getSelectedItem()));
//		   po.add("displayMyFriendOnly",displayRB.isChecked());
//		   po.add("UserId",ParseUser.getCurrentUser().getObjectId());
//		   showProgressDialog("Saving...");
//		   po.saveInBackground(new SaveCallback() {
//			@Override
//			public void done(ParseException arg0) {
//				closeProgressDialog();
//				
//			}
//		});
//	   }
//	
//}

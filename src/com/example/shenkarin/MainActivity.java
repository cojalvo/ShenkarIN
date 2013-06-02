package com.example.shenkarin;




import com.facebook.Session;
import com.facebook.android.Facebook;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.auth.FacebookAuthenticationProvider;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {

	
	 private ProgressDialog pb=null;
	 
	 //the settings dialogs 
	 private Dialog stepOneDialog=null;
	 private Dialog stepTwoDialog=null;
	 private Dialog stepThreeDialog=null;
	 
	 //Controllers inside the settings dialog
	 private Spinner trendSpinner=null;
	 private Spinner yearSpinner=null;
	 private RadioButton displayFriendOnlyRB=null;
	 private RadioButton displayAllRB=null;
	 private Button next1=null;
	 private Button next2=null;
	 private Button finish=null;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    Parse.initialize(this, "3kRz2kNhNu5XxVs3mI4o3LfT1ySuQDhKM4I6EblE", "UmGc3flrvIervInFbzoqGxVKapErnd9PKnXy4uMC"); 
	    ParseFacebookUtils.initialize("635010643194002");
		setContentView(R.layout.activity_main);
		 setContentView(R.layout.activity_main);
	      //if the user is already log in than go the main activity
	        final ParseUser currentUser = ParseUser.getCurrentUser();
	        currentUser.logOut();
			if (currentUser != null)
			{
			}
			//get the login button 
			ImageButton loginButton=(ImageButton) findViewById(R.id.facebookLoginButton);
			if(loginButton!=null)
			{
				//set the on click listener 
				loginButton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) 
					{
						//after login the facebook activity will call and the use will have to login
						//after the login we return to the onActivityResult event and when the finishAuthentication will invoke 
						//we will return here
						showProgressDialog("Login...");
				        ParseFacebookUtils.logIn(MainActivity.this, new LogInCallback() {
				            @Override
				            public void done(ParseUser user, ParseException err) {
				                if (user == null) {
				                	closeProgressDialog();
				                    Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
				                } else if (user.isNew()) {
				                	closeProgressDialog();
				                	startSettingsProcess();
				                    Log.d("MyApp", "User signed up and logged in through Facebook!");
				                } else {
				                    Log.d("MyApp", "User logged in through Facebook!");
				                    closeProgressDialog();
				                    startSettingsProcess();
				                    //TODO only for testing remove it 
				                    Toast t= Toast.makeText(MainActivity.this,"User  was logged in through Facebook!",Toast.LENGTH_SHORT);
				                    t.show();
				                }
				            }
				        });
					}
					
				});

			}
	    }
		private PersonalSettings getPersonlaSettingsFromCloud()
		{
			PersonalSettings ps=null;
			return ps;
		}
		private void  initSettingDialogs()
		{
			stepOneDialog = new Dialog(this,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
			stepOneDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			stepOneDialog.setContentView(R.layout.settings_dialog_step1);
			stepTwoDialog = new Dialog(this,android.R.style.Theme_Holo_Light_NoActionBar_Fullscreen);
			stepTwoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			stepTwoDialog.setContentView(R.layout.settings_dialog_step2);
			stepThreeDialog = new Dialog(this,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
			stepThreeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			stepThreeDialog.setContentView(R.layout.settings_dialog_step3);
		}
		private void initSettingDialogsButtons()
		{
				next1 = (Button) stepOneDialog.findViewById(R.id.btnNext1);
				next2=(Button)stepTwoDialog.findViewById(R.id.btnNext2);
				finish = (Button) stepThreeDialog.findViewById(R.id.btnFinish);
		}
		private void setOnclickListenersForSettingsDialogButtons()
		{
			next1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					stepOneDialog.hide();
					stepTwoDialog.show();
					
				}
			});
			next2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					stepTwoDialog.hide();
					stepThreeDialog.show();
				}
			});
			finish.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					stepThreeDialog.hide();
					createUserSettingParseObject();
					
				}
			});
			
		}
		private void initSettingsContentController()
		{
			trendSpinner=(Spinner) stepOneDialog.findViewById(R.id.spinner1);
			yearSpinner=(Spinner) stepTwoDialog.findViewById(R.id.spinner2);
			displayFriendOnlyRB=(RadioButton) stepThreeDialog.findViewById(R.id.displayMyFriendRB);
			displayAllRB=(RadioButton) stepThreeDialog.findViewById(R.id.displayAllRB);
			
		}
		private void startSettingsProcess()
		{
			initSettingDialogs();
			initSettingDialogsButtons();
			setOnclickListenersForSettingsDialogButtons();
			initSettingsContentController();
			stepOneDialog.show();
		}
		private void startNewActivity(Context context,Class activityClass)
		{
			Intent intent = new Intent(context, activityClass);
			startActivity(intent);
		}
		
		private void  showProgressDialog(String message)
		{
			pb = new ProgressDialog(MainActivity.this);
			pb.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pb.setMessage(message);
			pb.show();
		}
		private void closeProgressDialog()
		{
			pb.cancel();
		}
	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.main, menu);

	        return true;
	    }
	    @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	       super.onActivityResult(requestCode, resultCode, data);
	       ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
	    }
	    /***
		    * create the user settings object and save it to the cloud
		    * when saving the data a progress dialog will appear, this pd will close
		    * only after the completed data will be save
		    */
		   private void createUserSettingParseObject()
		   {
			   ParseObject po=new ParseObject("UserSettings");
			   po.add("trend",String.valueOf(trendSpinner.getSelectedItem()));
			   po.add("year",String.valueOf(yearSpinner.getSelectedItem()));
			   po.add("displayMyFriendOnly",displayFriendOnlyRB.isChecked());
			   po.add("displayAll",displayAllRB.isChecked());
			   po.add("UserId",ParseUser.getCurrentUser().getObjectId());
			   showProgressDialog("Saving...");
			   po.saveInBackground(new SaveCallback() {
				@Override
				public void done(ParseException arg0) {
					closeProgressDialog();
					
				}
			});
		   }

}

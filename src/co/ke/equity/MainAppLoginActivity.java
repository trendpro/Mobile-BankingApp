
package co.ke.equity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This is the activity for feature 2 in the dashboard application.
 * It displays some text and provides a way to get back to the home activity.
 *
 */

public class MainAppLoginActivity extends ParentActivity 
{
	//Variable declaration
	Button linkToUserRegistration=null;
	Button userLogin=null;
	QuestAccessClient qAccessClient = QuestAccessClient.getQuestAccessInstance();
	EditText username = null;
    EditText password = null;
    TextView loginError = null;
    public static String USERNAME = null;
    
    //use http://10.0.2.2/ to connect to your localhost ie http://localhost/
    private static String loginURL = "http://10.0.2.2/eserver/request.php";//HERE...
    private static String login_tag = "login";
    private JSONParser jsonParser = new JSONParser();
    
    // JSON Response node names
    private static String TAG_SUCCESS = "success";
   
/**
 * onCreate
 *
 * Called when the activity is first created. 
 * This is where you should do all of your normal static set up: create views, bind data to lists, etc. 
 * This method also provides you with a Bundle containing the activity's previously frozen state, if there was one.
 * 
 * Always followed by onStart().
 *
 * @param savedInstanceState Bundle
 */

protected void onCreate(Bundle savedInstanceState) 
{
    super.onCreate(savedInstanceState);
    setContentView (R.layout.main_app_login_form);
    setTitleFromActivityLabel (R.id.title_text);
    
    //Find views by id
    userLogin = (Button)findViewById(R.id.btn_main_Login);
    linkToUserRegistration = (Button)findViewById(R.id.btn_Link_To_Signup_Screen);
    username = (EditText)findViewById(R.id.main_login_username);
    password = (EditText)findViewById(R.id.main_loginPassword);
    loginError = (TextView)findViewById(R.id.main_login_error);
    
    //Add onClick Listener for Login button
    userLogin.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO User Login logic
			USERNAME = username.getText().toString().trim();	
			String pass = password.getText().toString();
	             
	             try {
	            	 	//check if device is connected to internet.
	                 	if(isOnline() == false)
	                 	{
	                	 	//No internet connection.Alert user
	                	 	displayAlert("Network Error!", "Your device does not have internet connection.");
	                	 	return;
	                 	}
	                 
	            	 JSONObject json = loginUser(USERNAME, pass);
	            	// check for login response
	                 if (json.getString(TAG_SUCCESS) != null) {
	                     loginError.setText("");
	                     String res = json.getString(TAG_SUCCESS);
	                     if(Integer.parseInt(res) == 1){
	                         // user successfully logged in
	                         String user_id = username.getText().toString();//use this later to make requests
	                         
	                         //Launch HomeScreenActivity
	                         Intent startHomeScreenIntent = new Intent(getApplicationContext(), HomeActivity.class);
	                         startHomeScreenIntent.putExtra("username",user_id);
	                         
	                         startActivity(startHomeScreenIntent);

	                         // Close Login Screen
	                         finish();
	                     }else{
	                         // Error in login
	                    	 loginError.setText(json.getString("error_msg"));
	                    	 //loginError.setText("Incorrect email or password!");
	         				 loginError.setTextColor(Color.RED);
	                     }
	                 }
	             } catch (Exception e) {
	            	 //network error
	            	 displayAlert("Network Error!", e.getMessage());
	             }
			
		}
	});//end method
    
    //Add onClick Listener for linkToUserReg button
    linkToUserRegistration.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			startActivity (new Intent(getApplicationContext(), UserSignUpActivity.class));
		}
	});//end of method
}
    
/**
 * function make Login Request
 * @param email
 * @param password
 * @throws Exception 
 * */
public JSONObject loginUser(String email, String password) throws Exception{
    // Building Parameters
    List<NameValuePair> params = new ArrayList<NameValuePair>();
    params.add(new BasicNameValuePair("tag", login_tag));
    params.add(new BasicNameValuePair("email", email));
    params.add(new BasicNameValuePair("password", password));
    JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
    
    return json;
}


protected void displayAlert( String title, String message ) {
    AlertDialog.Builder confirm = new AlertDialog.Builder( this );
    confirm.setTitle( title);
    confirm.setMessage( message );
    confirm.setNegativeButton( "OK", new DialogInterface.OnClickListener() {
        public void onClick( DialogInterface dialog, int which ) {
        }
    } );
    confirm.show().show();                
}

/*
 * Checks whether the device is connected to internet
 */
public boolean isOnline() {
    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

    NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    if (wifiNetwork != null && wifiNetwork.isConnected()) {
      return true;
    }

    NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    if (mobileNetwork != null && mobileNetwork.isConnected()) {
      return true;
    }

    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
    if (activeNetwork != null && activeNetwork.isConnected()) {
      return true;
    }

    return false;
}//end of isOnline();
} // end class

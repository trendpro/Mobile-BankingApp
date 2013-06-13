package co.ke.equity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * This is the activity for feature 2 in the dashboard application.
 * It displays some text and provides a way to get back to the home activity.
 *
 */

public class UserSignUpActivity extends ParentActivity implements OnItemSelectedListener
{
	//Instance Variables
	private ProgressDialog m_DownloadProgressDialog = null;
	QuestAccessClient qAccessClient = QuestAccessClient.getQuestAccessInstance();
	private static String loginURL = "http://10.0.2.2/eserver/request.php";
    private JSONParser jsonParser = new JSONParser();
    
    //JSON Response node names
    private static String TAG_SUCCESS = "success";
    private String gender = null;
    int mDay;
    int mMonth;
    int mYear;
    private TextView mDateDisplay;
    private Button mPickDate;

    static final int DATE_DIALOG_ID = 0;
	
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

@SuppressLint({ "ParserError", "ParserError" })
protected void onCreate(Bundle savedInstanceState) 
{
    super.onCreate(savedInstanceState);
    setContentView (R.layout.activity_sign_up_form);
    setTitleFromActivityLabel (R.id.title_text);
    
    Button alreadyHaveAnAccountButton = (Button)findViewById(R.id.btn_go_to_login_screen);
    Button signUpUser = (Button)findViewById(R.id.btn_sign_up_user);
    
    final EditText fullNameET = (EditText)findViewById(R.id.full_name_edittext);
    final EditText emailET = (EditText)findViewById(R.id.emailaddress_edittext);
    final EditText passET = (EditText)findViewById(R.id.enter_password_edittext);
    final EditText confirmPassET = (EditText)findViewById(R.id.confirm_password_edittext);
    
    Spinner spinner = (Spinner) findViewById(R.id.gender_spinner);
    
    // Create an ArrayAdapter using the string array and a default spinner layout
    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
         R.array.gender_array, android.R.layout.simple_spinner_item);
    
    //Specify the layout to use when the list of choices appears
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    
    //Apply the adapter to the spinner
    spinner.setAdapter(adapter);
    spinner.setOnItemSelectedListener(this);
    
    mDateDisplay = (TextView) findViewById(R.id.showMyDate);        
    mPickDate = (Button) findViewById(R.id.myDatePickerButton);

    mPickDate.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            showDialog(DATE_DIALOG_ID);
        }
    });

    // get the current date
    final Calendar c = Calendar.getInstance();
    mYear = c.get(Calendar.YEAR);
    mMonth = c.get(Calendar.MONTH);
    mDay = c.get(Calendar.DAY_OF_MONTH);

    // display the current date
    updateDisplay();
    
    //wire in sign up user button
    signUpUser.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			//get
			try
			{
				String  fname = fullNameET.getText().toString();
				String email = emailET.getText().toString();
				String pass = passET.getText().toString();
				String confirmPass = confirmPassET.getText().toString();
	    		
	    		//input validate user input
				if(fname.equals("") || fname ==null )
				{
					displayAlert("Invalid Data", "Name Field cannot be empty!");
					return;
				}
				else if(email.equals("") || email ==null)
				{
					displayAlert("Invalid Data", "Email Field cannot be empty!");
					return;
				}
				else if(pass.equals("") || pass ==null)
				{
					displayAlert("Invalid Data", "Password Field cannot be empty!");
					return;
				}
				else if(pass.equals(confirmPass) == false)
				{
					displayAlert("Invalid Data Entered", "Passwords do not match!");
					return;
				}
				else if(gender.equals("")|| gender==null)
				{
					displayAlert("Invalid Data Entered", "Gender of User cannot be blank!");
					return;
				}
				
				//TODO Auto-generated method stub
				SignUpUserAsyncTask signUpTask = new SignUpUserAsyncTask();
				int month = mMonth+1;
				signUpTask.execute(new UserSignUpDataType(fname, email, pass, confirmPass,mYear+"-"+month+"-"+mDay, gender));
			   
			}
			catch(Exception e)
			{
				displayAlert("Invalid Data", e.getMessage());
				return;
			}
			
		}
	});//end of anonymous inner class
    
    //add handle
    alreadyHaveAnAccountButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			startActivity (new Intent(getApplicationContext(), MainAppLoginActivity.class));
		}
	});
}
    
private class SignUpUserAsyncTask extends AsyncTask<Object, Boolean, Boolean> 
{

	String result = null;
	boolean isSignUpSuccessful = false;
	
	
	@Override
	protected Boolean doInBackground(Object... params)
	{
		UserSignUpDataType data= (UserSignUpDataType) params[0];
		
	try 
	{
		//Building Parameters
	    List<NameValuePair> parameters = new ArrayList<NameValuePair>();
	    parameters.add(new BasicNameValuePair("tag", "signup"));
	    parameters.add(new BasicNameValuePair("email",data.getEmail()));
	    parameters.add(new BasicNameValuePair("password",data.getPasswprd()));
	    parameters.add(new BasicNameValuePair("name", data.getName()));
	    parameters.add(new BasicNameValuePair("dob",data.getDateOfBirth()));
	    parameters.add(new BasicNameValuePair("gender", data.getGender()));
	    
	    Looper.prepare();
	    JSONObject json =jsonParser.getJSONFromUrl(loginURL, parameters);
	    //Looper.loop();
	    
	    try {
    	 		    	
         if(json.getString(TAG_SUCCESS) != null) {
             String res = json.getString(TAG_SUCCESS);
             displayAlert("Server response", res);
             if(Integer.parseInt(res) == 1)
             {
                 // user successfully signed up
            	 isSignUpSuccessful = true;
            	 return true;
             }
             else{
                 // Error in signup process
            	 return  false;
             }
         }
     } catch (Exception e) {
    	 //network error
    	 isSignUpSuccessful = false;
     }
	    
	    if(isSignUpSuccessful == false)
	    {
	    	displayAlert("Sign up error", "signup error");
	    }
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		result = e.getMessage();
		isSignUpSuccessful = false;		
	}
	return isSignUpSuccessful;
	}

	

	protected void onPostExecute(Boolean rsl) {
		// TODO Auto-generated method stub
		m_DownloadProgressDialog.dismiss();
		
			if(rsl)
			{
				displayAlert("User Sign Up","User Sign Up was successful.");
				startActivity (new Intent(getApplicationContext(), MainAppLoginActivity.class));
			}
			else
			{
				displayAlert("User Sign Up Failed", result);
			}
		super.onPostExecute(rsl);
	}



	@Override
	protected void onPreExecute() {
		m_DownloadProgressDialog = ProgressDialog.show(UserSignUpActivity.this,    
	              "Please wait...", "Signing up User ...", true);
	super.onPreExecute();
	}



}//end of inner class

/*
 * Displays an alert dialog to user
 */
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
 * (non-Javadoc)Gets the selected gender from the spinner.
 * @see android.widget.AdapterView.OnItemSelectedListener#onItemSelected(android.widget.AdapterView, android.view.View, int, long)
 */
public void onItemSelected(AdapterView<?> parent, View view, 
        int pos, long id) {
    // An item was selected. You can retrieve the selected item using
    gender = (String)parent.getItemAtPosition(pos);
}

/*
 * (non-Javadoc) When nothing is selected the gender defaults to Male.
 * @see android.widget.AdapterView.OnItemSelectedListener#onNothingSelected(android.widget.AdapterView)
 */
public void onNothingSelected(AdapterView<?> parent) {
    gender = "Male";
}

private void updateDisplay() {
    this.mDateDisplay.setText(
        new StringBuilder()
                // Month is 0 based so add 1
                .append(mMonth + 1).append("-")
                .append(mDay).append("-")
                .append(mYear).append(" "));
}
private DatePickerDialog.OnDateSetListener mDateSetListener =
new DatePickerDialog.OnDateSetListener() {
    public void onDateSet(DatePicker view, int year, 
                          int monthOfYear, int dayOfMonth) {
        mYear = year;
        mMonth = monthOfYear;
        mDay = dayOfMonth;
        updateDisplay();
    }
};

@Override
protected Dialog onCreateDialog(int id) {
   switch (id) {
   case DATE_DIALOG_ID:
      return new DatePickerDialog(this,
                mDateSetListener,
                mYear, mMonth, mDay);
   }
   return null;
}

} // end class

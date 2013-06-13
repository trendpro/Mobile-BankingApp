package co.ke.equity;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.widget.Button;

public class ChangeLanguagePreferenceActivity extends PreferenceActivity
{
	//Variable declaration
	Button mLogoutButton = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);		
		addPreferencesFromResource(R.xml.change_language_preferences);
		
		//Find views by id
		//mLogoutButton = (Button)findViewById(R.id.btn_logout_change_language_settings);
		
		
	}//end of onCreate()

}//end of class

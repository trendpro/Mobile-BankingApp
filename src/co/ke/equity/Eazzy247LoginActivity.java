
package co.ke.equity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * This is the activity for feature 2 in the dashboard application.
 * It displays some text and provides a way to get back to the home activity.
 *
 */

public class Eazzy247LoginActivity extends ParentActivity 
{
	//Variable declaration
	Button linkToUserRegistration=null;
	Button userLogin=null;
	private  String username = MainAppLoginActivity.USERNAME;
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
    setContentView (R.layout.activity_eazzy247_login);
    setTitleFromActivityLabel (R.id.title_text);
    TextView tv = (TextView)findViewById(R.id.equity_app_footer);
    displayAdverts(username,tv);
    //Find views by id
    userLogin = (Button)findViewById(R.id.btnLogin);
    linkToUserRegistration = (Button)findViewById(R.id.btnLinkToRegisterScreen);
    
    //Add onClick Listener for Login button
    userLogin.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO User Login logic
			
			//testing
			//startActivity (new Intent(getApplicationContext(), Eazzy247MainMenuActivity.class));
			//displayAdverts(username);
		}
	});//end method
    
    //Add onClick Listener for linkToUserReg button
    linkToUserRegistration.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			startActivity (new Intent(getApplicationContext(), UserRegistrationNormalActivity.class));
		}
	});//end of method
}
    
} // end class

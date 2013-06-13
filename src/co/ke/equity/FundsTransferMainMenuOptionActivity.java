
package co.ke.equity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * This is the activity for Funds Transfer Main Menu Option.It enables one to transfer funds
 * from an Equity Bank Account to another Equity Bank Account or any other supported bank's
 * Account.
 *
 */

public class FundsTransferMainMenuOptionActivity extends ParentActivity 
{
	//Variables declaration
	Button mCancelButton = null;
	Button mTransferFunds = null;
	Button mLogoutButton = null;

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
    setContentView (R.layout.activity_funds_transfer_main_menu);
    setTitleFromActivityLabel (R.id.title_text);
    
    //Find views by id
    mCancelButton = (Button)findViewById(R.id.btn_cancel_transfer_funds_mm_option);
    mTransferFunds = (Button)findViewById(R.id.btn_transfer_funds_mm_option);
    mLogoutButton = (Button)findViewById(R.id.btn_logout_funds_transfer_main_menu_option);
    
    
    //Add onClick() Listener to logout button
    mLogoutButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Save state and logout user
			
			//Go back to login screen
			startActivity (new Intent(getApplicationContext(), Eazzy247LoginActivity.class));
			
		}
	});//end of anonymous inner class
    
}//end of onCreate()

@Override
public boolean onCreateOptionsMenu(Menu menu) 
{
	// TODO Auto-generated method stub
	new MenuInflater(this).inflate(R.menu.funds_transfer_options_menu, menu);
	return super.onCreateOptionsMenu(menu);
}

@Override
public boolean onOptionsItemSelected(MenuItem item)
{
	// TODO Auto-generated method stub
	if(item.getItemId()==R.id.add_beneficiary_funds_transfer)
	{
		//code for adding a beneficiary
	}
	else if(item.getItemId()==R.id.delete_beneficiary_funds_transfer)
	{
		//code for deleting a beneficiary
	}
	return super.onOptionsItemSelected(item);
}
    
} // end class


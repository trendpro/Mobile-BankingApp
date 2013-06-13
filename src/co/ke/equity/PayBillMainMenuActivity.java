
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
 * This is the activity for bill payment module of Eazzy 24/7.It enables one to pay for electricity
 * water,HELB ,Tv e.t.c bills 
 *
 */

public class PayBillMainMenuActivity extends ParentActivity 
{
	//Variables declaration
	Button mCancelButton = null;
	Button mPaybillButton = null;
	Button mLogoutButton = null;
	Button mViewMyBillsButton = null;

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
    setContentView (R.layout.activity_pay_bill_main_menu_option);
    setTitleFromActivityLabel (R.id.title_text);
    
    //Find views by id
    mCancelButton = (Button)findViewById(R.id.btn_cancel_pay_bill_mm_option);
    mPaybillButton = (Button)findViewById(R.id.btn_pay_bill_bill_payment_mm_option);
    mLogoutButton = (Button)findViewById(R.id.btn_logout_pay_bill_main_menu_option);
    mViewMyBillsButton = (Button)findViewById(R.id.btn_view_my_bills_bill_payment_mm_option);
    
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
	new MenuInflater(this).inflate(R.menu.bill_payment_options_menu, menu);
	return super.onCreateOptionsMenu(menu);
}

@Override
public boolean onOptionsItemSelected(MenuItem item) 
{
	// TODO Auto-generated method stub
	if(item.getItemId()==R.id.add_bill_bill_payment)
	{
		//code for adding a bill
		startActivity (new Intent(getApplicationContext(), AddABillBillPaymentMenuOptionActivity.class));
	}
	else if(item.getItemId()==R.id.delete_bill_bill_payment)
	{
		//code for deleting a bill
		startActivity (new Intent(getApplicationContext(), DeleteABillBillPaymentMenuOptionActivity.class));
	}
	return super.onOptionsItemSelected(item);
}
    
} // end class


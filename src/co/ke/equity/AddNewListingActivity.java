package co.ke.equity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * This is the activity for feature 2 in the dashboard application.
 * It displays some text and provides a way to get back to the home activity.
 *
 */

public class AddNewListingActivity extends ParentActivity 
{
	//Instance Variables
	private ProgressDialog m_AddListingProgressDialog = null;
	QuestAccessClient qAccessClient = QuestAccessClient.getQuestAccessInstance();
	private String Username = MainAppLoginActivity.USERNAME;
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
    setContentView (R.layout.activity_add_new_listing);
    setTitleFromActivityLabel (R.id.title_text);
    
    Button addCompanyListingButton = (Button)findViewById(R.id.btn_add_company_listing);
    Button addShareListing = (Button)findViewById(R.id.btn_add_share_listing);
    
    final EditText companyNameET = (EditText)findViewById(R.id.company_name_edittext);
    final EditText companyListingIDET = (EditText)findViewById(R.id.compny_listing_id_edittext);
    final EditText noOfShareET = (EditText)findViewById(R.id.no_of_shares_edittext);
    
    final EditText shareListingListingIDET = (EditText)findViewById(R.id.share_listing_id);
    
    //wire in the buttons
    addCompanyListingButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String coName;
			String listingID;
			int noOfShare=0;
			//validate input
			try
			{
				coName = companyNameET.getText().toString();
				listingID = companyListingIDET.getText().toString();
				noOfShare = Integer.parseInt(noOfShareET.getText().toString());
				
				//check for blank and null strings
				if(coName.equals("") || coName == null)
				{
					displayAlert("Invalid Data", "Company Name cannot be empty!");
					return;
				}
				else if(listingID.equals("") || listingID == null)
				{
					displayAlert("Invalid Data","Listing ID cannot be empty.Check at information section for all IDs.");
					return;
				}
			
			}
			catch(Exception e)
			{
				displayAlert("Invalid Data", e.getMessage());
				return;
			}
			
			//call AsyncTask
			AddCompanyListingAsyncTask addCompanyTask = new AddCompanyListingAsyncTask();
			addCompanyTask.execute(listingID,coName,noOfShare,Username);
		}
	});
    
    addShareListing.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String shareListID = null;
			
			//validate input
			try
			{
				shareListID = shareListingListingIDET.getText().toString();
				
				//check for null strings and blank strings
				if(shareListID.equals("") || shareListID ==  null)
				{
					displayAlert("Invalid Data", "Share Listing ID cannot be blank. Check information section above for all IDs.");
					return;
				}
			}
			catch(Exception e)
			{
				displayAlert("Invalid Data", e.getMessage());
				return;
			}
			
			//call AsyncTask 
			AddShareListingAsyncTask addTask = new AddShareListingAsyncTask();
			addTask.execute(shareListID,Username);
		}
	});
}//end of onCreate()
    
private class AddCompanyListingAsyncTask extends AsyncTask<Object, Boolean, Boolean> 
{

	String result = null;
	boolean isSuccessful = false;
	
	
	@Override
	protected Boolean doInBackground(Object... params)
	{
		String companyListingID= (String) params[0];
		String companyName= (String) params[1];
		int noOfShares= (Integer) params[2];
		String username= (String) params[3];
	try 
	{
		//add company listing
		isSuccessful =  qAccessClient.addCompanyToMyPortfolio(companyListingID, companyName, noOfShares,username);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		result = e.getMessage();
		isSuccessful = false;		
	}
	return isSuccessful;
	}

	

	protected void onPostExecute(Boolean rsl) {
		// TODO Auto-generated method stub
		m_AddListingProgressDialog.dismiss();
		
			if(rsl)
			{
				displayAlert("Add Company Listing","Adding company listing was successful.");
			}
			else
			{
				displayAlert("Adding Company Listing Failed", result);
			}
		super.onPostExecute(rsl);
	}



	@Override
	protected void onPreExecute() {
		m_AddListingProgressDialog = ProgressDialog.show(AddNewListingActivity.this,    
	              "Please wait...", "Adding company listing to My Portfolio ...", true);
	super.onPreExecute();
	}



}//end of inner class

private class AddShareListingAsyncTask extends AsyncTask<Object, Boolean, Boolean> 
{

	String result = null;
	boolean isSuccessful = false;
	
	
	@Override
	protected Boolean doInBackground(Object... params)
	{
		String shareListingID= (String) params[0];
		String username= (String) params[1];
	try 
	{
		//Add share listing
		isSuccessful =  qAccessClient.addSharelistingToMyWatchList(shareListingID,username);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		result = e.getMessage();
		isSuccessful = false;		
	}
	return isSuccessful;
	}

	

	protected void onPostExecute(Boolean rsl) {
		// TODO Auto-generated method stub
		m_AddListingProgressDialog.dismiss();
		
			if(rsl)
			{
				displayAlert("Add share Listing","Adding share Listing was successful.");
			}
			else
			{
				displayAlert("Adding Share Listing Failed", result);
			}
		super.onPostExecute(rsl);
	}



	@Override
	protected void onPreExecute() {
		m_AddListingProgressDialog = ProgressDialog.show(AddNewListingActivity.this,    
	              "Please wait...", "Adding share listing to my watchlist ...", true);
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
} // end class

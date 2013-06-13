
package co.ke.equity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * This is the activity for NSE Live!feature application.
 * It displays latest stock news/prices from NSE and provides a way to get back to the home activity.
 *
 */

public class NseLiveActivity extends ParentActivity 
{
	//Instance Variables
	Button myPortfolioButon = null;
	Button myWatchListButton = null;
	Button topGainersButton = null;
	Button topLosersButton = null;
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
    setContentView (R.layout.activity_f4);
    setTitleFromActivityLabel (R.id.title_text);
    TextView tv = (TextView)findViewById(R.id.equity_app_footer);
    displayAdverts(username,tv);
    
    //find buttons
    myPortfolioButon =(Button)findViewById(R.id.btn_my_portfolio);
    myWatchListButton = (Button)findViewById(R.id.btn_my_watchlist);
    topGainersButton = (Button)findViewById(R.id.btn_top_gainers);
    topLosersButton = (Button)findViewById(R.id.btn_top_losers);
    
    myPortfolioButon.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO naviagate user to myPortfolio activity
			startActivity (new Intent(getApplicationContext(), NSELiveMyPortfolioActivity.class));			
		}
	});
    
    myWatchListButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			startActivity (new Intent(getApplicationContext(), NSELiveMyWatchListActivity.class));
		}
	});
    
    topGainersButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			startActivity (new Intent(getApplicationContext(), NSELiveTopGainersActivity.class));
		}
	});
    
    topLosersButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			startActivity (new Intent(getApplicationContext(), NSELiveTopLosersActivity.class));
		}
	});

}//end of onCreate()
    
} // end class

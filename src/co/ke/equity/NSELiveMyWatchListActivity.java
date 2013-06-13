package co.ke.equity;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class NSELiveMyWatchListActivity extends ParentListActivity{
	
	//Instance Variables
	private ProgressDialog m_ProgressDialog = null;
	private ArrayList<ShareListingDataType> m_orders = null;
    private Runnable viewOrders;
    private ListItemAdapter m_adapter;
    
    QuestAccessClient qAccessClient = QuestAccessClient.getQuestAccessInstance();
    private ProgressDialog m_RemoveShareListingProgressDialog = null;
    private String mUSERNAME = MainAppLoginActivity.USERNAME;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView (R.layout.activity_nse_live_my_watchlist);
	    setTitleFromActivityLabel (R.id.title_text);
	    TextView tv = (TextView)findViewById(R.id.equity_app_footer);
	    displayAdverts(mUSERNAME,tv);
	    
	    m_orders = new ArrayList<ShareListingDataType>();
        this.m_adapter = new ListItemAdapter(this, R.layout.share_listing_row, m_orders);
        setListAdapter(this.m_adapter);
	    
	    viewOrders = new Runnable(){
            public void run() {
            	Looper.prepare();
                getOrders();
                //Looper.loop();
            }
        };
    Thread thread =  new Thread(null, viewOrders, "MagentoBackground");
        thread.start();
        m_ProgressDialog = ProgressDialog.show(NSELiveMyWatchListActivity.this,    
              "Please wait...", "Retrieving data ...", true);
        
        getListView().setOnItemClickListener(new OnItemClickListener() 
        {
            public void onItemClick(AdapterView<?> parent, View view,
                int position, long id) {
        
            	ShareListingDataType item = m_orders.get(position);
            	
            	//code for removing share
            	RemoveShareListingAsyncTask removeTask = new RemoveShareListingAsyncTask();
            	removeTask.execute(mUSERNAME,item.getRecordID(),item);
            	
            	m_adapter.notifyDataSetChanged();
            	m_adapter.remove(item);
            	m_adapter.notifyDataSetChanged();
            }
          });//end of anonymous inner class
	}//end of onCreate()
	
		//inner class
		private class ListItemAdapter extends ArrayAdapter<ShareListingDataType> {

	        private ArrayList<ShareListingDataType> items;

	        public ListItemAdapter(Context context, int textViewResourceId, ArrayList<ShareListingDataType> items) {
	                super(context, textViewResourceId, items);
	                this.items = items;
	        }

	        @Override
	        public View getView(int position, View convertView, ViewGroup parent) {
	                View v = convertView;
	                if (v == null) {
	                    LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	                    v = vi.inflate(R.layout.share_listing_row, null);
	                }
	                ShareListingDataType o = items.get(position);
	                if (o != null) {
	                        TextView cn = (TextView) v.findViewById(R.id.company_name_share_listing);
	                        TextView ltp = (TextView) v.findViewById(R.id.ltp_share_listing);
	                        TextView pp = (TextView) v.findViewById(R.id.pp_share_listing);
	                        TextView pc = (TextView) v.findViewById(R.id.pc_share_listing);
	                        
	                        
	                        	if (cn != null) 
	                        	{
	                        		cn.setText(o.getCompanyName());   
	                        		ltp.setText("Last Traded Price : Kes." +o.getLastTradedPrice()); 
	                        		pp.setText("Previous Price : Kes."+o.getPreviousPrice());
	                        		pc.setText("Percentage Change:  "+o.getPercentageChange());
	                        		
	                        		//set colors
	                        		if(o.getPercentageChange() < 0)
	                        		{
	                        			pc.setBackgroundColor(Color.RED);
	                        		}
	                        		else if (o.getPercentageChange() >= 0)
	                        		{
	                        			pc.setBackgroundColor(Color.GREEN);
	                        		}
	                        			
	                        	}
	                        
	                        	else if (ltp != null) 
	                        	{
	                        		ltp.setText("Last Traded Price(KES): " +o.getLastTradedPrice());                       
	                        	}
	                        	
	                        	else if (pp != null) 
	                        	{
	                        		pp.setText("Previous Price(KES): "+o.getPreviousPrice());                       
	                        	}
	                        	
	                        	else if(pc != null) 
	                        	{
	                        		//display different colors
	                        		pc.setText("% Change:  "+o.getPercentageChange());                       
	                        	}
	                        
	                       
	                }
	                return v;
	        }//end of getView()
		}//end of inner class
		
	private void getOrders(){
        try{
        	//remove this line of code
            Thread.sleep(5000);
            m_orders = new ArrayList<ShareListingDataType>();           	
           
            m_orders = (ArrayList<ShareListingDataType>)qAccessClient.getMyWatchList(mUSERNAME);
            
            Log.i("ARRAY", ""+ m_orders.size());
          } catch (Exception e) {
            Log.e("BACKGROUND_PROC", e.getMessage());
          }
          runOnUiThread(returnRes);
      }//end of getOrders()
	
	private Runnable returnRes = new Runnable() {

        public void run() {
        	if(m_orders != null && m_orders.size() > 0){
                m_adapter.notifyDataSetChanged();
                for(int i=0;i<m_orders.size();i++)
                m_adapter.add(m_orders.get(i));
            }
            m_ProgressDialog.dismiss();
            m_adapter.notifyDataSetChanged();
        }
    };//end of runnable  returnRes
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		new MenuInflater(this).inflate(R.menu.my_watchlist_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId()==R.id.add_sharelisting_my_watchlist_menu)
		{
			//code for adding sharelisting to my watchlist
			startActivity (new Intent(getApplicationContext(), AddNewListingActivity.class));
		}
		else if(item.getItemId()==R.id.refresh_my_watchlist_menu)
		{
			//code for refreshing my porfolio
			startActivity(getIntent()); 
  			finish();
		}
		return super.onOptionsItemSelected(item);
	}

	private class RemoveShareListingAsyncTask extends AsyncTask<Object, Boolean, Boolean> 
	{

		String result = null;
		boolean isSuccessful = false;
		ShareListingDataType item = null;
		
		@Override
		protected Boolean doInBackground(Object... params)
		{
			String username= (String) params[0];
			String recordID= (String) params[1];
			item = (ShareListingDataType) params[2];
			
			try 
			{
				//sign up users
				isSuccessful =  qAccessClient.removeShareListingFromMyWatchList(recordID, username);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				result = e.getMessage();
				isSuccessful = false;		
			}
		return isSuccessful;
		}

		

		protected void onPostExecute(Boolean rsl) {
			// TODO Auto-generated method stub
			m_RemoveShareListingProgressDialog.dismiss();
			
				if(rsl)
				{
					//intentionaly empty if clause
				}
				else
				{
					displayAlert("Removing share Listing Failed", result);
					m_adapter.notifyDataSetChanged();
	            	m_adapter.add(item);
	            	m_adapter.notifyDataSetChanged();
				}
			super.onPostExecute(rsl);
		}



		@Override
		protected void onPreExecute() {
			m_RemoveShareListingProgressDialog = ProgressDialog.show(NSELiveMyWatchListActivity.this,    
		              "Please wait...", "Removing share listing from your watchlist...", true);
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
	}//end of method
	
}//end of class

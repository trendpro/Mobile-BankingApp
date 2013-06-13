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

public class NSELiveMyPortfolioActivity extends ParentListActivity{
	
	//Instance Variables
	private ProgressDialog m_ProgressDialog = null;
	private ArrayList<MyPortfolioDataType> m_orders = null;
    private Runnable viewOrders;
    private ListItemAdapter m_adapter;
    
    QuestAccessClient qAccessClient = QuestAccessClient.getQuestAccessInstance();
    private TextView myApproxWorthTextView = null;
    double approxWorth =0.0;
    private ProgressDialog m_RemoveCompanyListingProgressDialog = null;
    private String mUSERNAME = MainAppLoginActivity.USERNAME;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView (R.layout.activity_nse_live_my_portfolio);
	    setTitleFromActivityLabel (R.id.title_text);
	    TextView tv = (TextView)findViewById(R.id.equity_app_footer);
	    displayAdverts(mUSERNAME,tv);
	    
	    myApproxWorthTextView = (TextView)findViewById(R.id.portfolio_worth_display);
	    
	    m_orders = new ArrayList<MyPortfolioDataType>();
        this.m_adapter = new ListItemAdapter(this, R.layout.share_listing_row, m_orders);
        setListAdapter(this.m_adapter);
	    
	    viewOrders = new Runnable(){
            public void run() {
            	Looper.prepare();
                getOrders();
                Looper.loop();
            }
        };
    Thread thread =  new Thread(null, viewOrders, "MagentoBackground");
        thread.start();
        m_ProgressDialog = ProgressDialog.show(NSELiveMyPortfolioActivity.this,    
              "Please wait...", "Retrieving data ...", true);
        
        getListView().setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                int position, long id) {
        
            	MyPortfolioDataType item = m_orders.get(position);
            	
            	//code for removing company listing            	
            	RemoveCompanyListingAsyncTask removeTask = new RemoveCompanyListingAsyncTask();
            	removeTask.execute(mUSERNAME,item.getRecordID(),item);
            	
            	
            	m_adapter.notifyDataSetChanged();
            	m_adapter.remove(item);
            	m_adapter.notifyDataSetChanged();
            }
          });//end of anonymous inner class
	}//end of onCreate()
	
			//inner class
			private class ListItemAdapter extends ArrayAdapter<MyPortfolioDataType> {

		        private ArrayList<MyPortfolioDataType> items;

		        public ListItemAdapter(Context context, int textViewResourceId, ArrayList<MyPortfolioDataType> items) {
		                super(context, textViewResourceId, items);
		                this.items = items;
		        }

		        @Override
		        public View getView(int position, View convertView, ViewGroup parent) {
		                View v = convertView;
		                if (v == null) {
		                    LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		                    v = vi.inflate(R.layout.my_portfolio_row, null);
		                }
		                MyPortfolioDataType o = items.get(position);
		                if (o != null) {
		                        TextView cn = (TextView) v.findViewById(R.id.company_name_my_portfolio);
		                        TextView sp = (TextView) v.findViewById(R.id.share_price_my_portfolio);
		                        TextView nOS = (TextView) v.findViewById(R.id.number_of_shares_my_portfolio);
		                        TextView aW = (TextView) v.findViewById(R.id.total_worth_my_portfolio);
		                        
		                        
		                        	if (cn != null) 
		                        	{
		                        		cn.setText(o.getCompanyName());   
		                        		nOS.setText("Number of shares : "+ o.getNoOfShares());
		                        		sp.setText("Share Price : Kes." + o.getSharePrice());
		                        		aW.setText("Total worth : Kes."+o.getApproxWorth());
		                        	
		                        	}        
		                       
		                }
		                return v;
		        }//end of getView()
			}//end of inner class 
			
	private void getOrders(){
        try{
        	//Simulate long running task
            Thread.sleep(5000);
            m_orders = new ArrayList<MyPortfolioDataType>();           	
           
            m_orders = (ArrayList<MyPortfolioDataType>)qAccessClient.getMyPortfolio(mUSERNAME);
            
            //Calculate users approximate worth
            for(MyPortfolioDataType myPortFolio : m_orders)
            {
            	approxWorth += myPortFolio.getApproxWorth();
            }
            
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
            myApproxWorthTextView.setText("My Approx. Worth : Kes:"+approxWorth);
            myApproxWorthTextView.setBackgroundColor(Color.CYAN);//change this color later to a better one
            m_adapter.notifyDataSetChanged();
        }
    };//end of runnable  returnRes
	

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		new MenuInflater(this).inflate(R.menu.my_portfolio_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId()==R.id.add_company_my_portfolio_menu)
		{
			//code for adding company to my portfolio
			startActivity (new Intent(getApplicationContext(), AddNewListingActivity.class));
		}
		else if(item.getItemId()==R.id.refresh_my_portfolio_menu)
		{
			//code for refreshing my porfolio
			startActivity(getIntent()); 
  			finish();
		}
		return super.onOptionsItemSelected(item);
	}
	
	private class RemoveCompanyListingAsyncTask extends AsyncTask<Object, Boolean, Boolean> 
	{

		String result = null;
		boolean isSuccessful = false;
		MyPortfolioDataType item = null;
		
		@Override
		protected Boolean doInBackground(Object... params)
		{
			String username= (String) params[0];
			String recordID= (String) params[1];
			item = (MyPortfolioDataType)params[2];
		try 
		{
			//remove company listing
			isSuccessful =  qAccessClient.removeCompanyFromMyPortfolio(recordID, username);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result = e.getMessage();
			isSuccessful = false;		
		}
		return isSuccessful;
		}

		

		protected void onPostExecute(Boolean rsl) {
			// TODO Auto-generated method stub
			m_RemoveCompanyListingProgressDialog.dismiss();
			
				if(rsl)
				{
					//displayAlert("Remove Compay Listing","Company Listing removal was successful.");
					//startActivity (new Intent(getApplicationContext(), MainAppLoginActivity.class));
				}
				else
				{
					displayAlert("Removing Company Listing Failed!", result);
					m_adapter.notifyDataSetChanged();
	            	m_adapter.add(item);
	            	m_adapter.notifyDataSetChanged();
				}
			super.onPostExecute(rsl);
		}



		@Override
		protected void onPreExecute() {
			m_RemoveCompanyListingProgressDialog = ProgressDialog.show(NSELiveMyPortfolioActivity.this,    
		              "Please wait...", "Removing Company Listing from your Portfolio ...", true);
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

}//end of class

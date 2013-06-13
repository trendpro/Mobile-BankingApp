package co.ke.equity;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class NSELiveTopGainersActivity extends ParentListActivity{
	
	//Instance Variables
	private ProgressDialog m_ProgressDialog = null;
	private ArrayList<ShareListingDataType> m_orders = null;
	private Runnable viewOrders;
    private ListItemAdapter m_adapter;
    
    QuestAccessClient qAccessClient = QuestAccessClient.getQuestAccessInstance();
    private  String username = MainAppLoginActivity.USERNAME;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView (R.layout.activity_nse_live_top_gainers);
	    setTitleFromActivityLabel (R.id.title_text);
	    TextView tv = (TextView)findViewById(R.id.equity_app_footer);
	    displayAdverts(username,tv);
	    
	    m_orders = new ArrayList<ShareListingDataType>();
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
        m_ProgressDialog = ProgressDialog.show(NSELiveTopGainersActivity.this,    
              "Please wait...", "Retrieving data ...", true);
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
        	//Simulate long running task
            Thread.sleep(5000);
            m_orders = new ArrayList<ShareListingDataType>();           	
           
            m_orders = (ArrayList<ShareListingDataType>)qAccessClient.getTopGainers();
            
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
	
}//end of class

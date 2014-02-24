package alberto.test.wifiinfo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class ChannelActivity extends Activity {

	// Result code
	private static final int CLOSE_ALL_ACTIVITIES = 900;
	
	// IDs for menu items
	private static final int MENU_REFRESH = Menu.FIRST;
	private static final int MENU_INFO = Menu.FIRST + 1;
	
	
	// Progress bars
	private List<ProgressBar> progressBars = new ArrayList<ProgressBar>();
	
	// Best chanel TextView
	private TextView txtBestChannels = null;
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.channel);
		
		// Populate TableLayout
		TableLayout table = (TableLayout)ChannelActivity.this.findViewById(R.id.table_channel);
		txtBestChannels = (TextView)table.findViewById(R.id.txtBestChannels);
		for (int i=1; i<=14; i++) {
			TableRow row = (TableRow)LayoutInflater.from(ChannelActivity.this).inflate(R.layout.channel_row, null);
			
		    ((TextView)row.findViewById(R.id.lblChannel)).setText(getText(R.string.channel_string) + " " + i);
		    
		    ProgressBar progressBar = (ProgressBar)row.findViewById(R.id.progress);
		    progressBars.add(progressBar);
		    
		    table.addView(row);
		}
		
		setInfo();
		//setMockupInfo();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		menu.add(Menu.NONE, MENU_REFRESH, Menu.NONE, R.string.refresh_string);
		menu.add(Menu.NONE, MENU_INFO, Menu.NONE, R.string.info_string);
		return true;
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_REFRESH:
			setInfo();
			return true;
		case MENU_INFO:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	
	private void setInfo() {
		ChannelUsage channelUsage = getChannelUsage();
		
		for (int i=1; i<=14; i++) {
			progressBars.get(i-1).setProgress(channelUsage.getUsagePercentage(i));
		}
		
		String bestChannels = channelUsage.getBestChannel(4);
		txtBestChannels.setText(
				getText(R.string.best_channels_string) + ": " + bestChannels + "...");
	}
	
	
	private void setMockupInfo() {
		for (int i=1; i<=14; i++) {
		    progressBars.get(i-1).setProgress((int)(Math.random()*100));
		}
		String bestChannels = "2, 3, 1, 4...";
		txtBestChannels.setText(
				getText(R.string.best_channels_string) + ": " + bestChannels + "...");
	}
	
	
	private ChannelUsage getChannelUsage() {
		ChannelUsage usage = new ChannelUsage();
		
		// Check if WiFi is connected
        ConnectivityManager connManager = 
                (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo wifiStatus = 
                connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if ( !wifiStatus.isConnected() ) {
            Toast.makeText(getApplicationContext(), 
                    R.string.connection_string, Toast.LENGTH_LONG).show();
            setResult(CLOSE_ALL_ACTIVITIES);
            finish();
        } else {
            WifiManager wm = (WifiManager) getSystemService(Context.WIFI_SERVICE);
            List<ScanResult> networkList = wm.getScanResults();
        	for (ScanResult network : networkList) {
        		usage.addNetwork(network);
            }
        }
		return usage;
	}
}

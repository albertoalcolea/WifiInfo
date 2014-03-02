package alberto.test.wifiinfo;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class NetworksActivity extends ListActivity {
	
	// Result code
	private static final int CLOSE_ALL_ACTIVITIES = 900;
	
	// IDs for menu items
	private static final int MENU_REFRESH = Menu.FIRST;
	private static final int MENU_INFO = Menu.FIRST + 1;
		
	
	private NetworksAdapter mAdapter;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (Constants.USE_MOCKUPS) {
			List<ScanResult> networks = new ArrayList<ScanResult>(); 
			mAdapter = new NetworksAdapter(this, networks);
		} else {
			mAdapter = new NetworksAdapter(this, getNetworks());
		}
		getListView().setAdapter(mAdapter);
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		if (Constants.USE_MOCKUPS) {
			setMockupInfo();
		} else {
			setInfo();
		}
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
		mAdapter.update(getNetworks());
	}
	
	
	private void setMockupInfo() {
		List<ScanResult> networks = new ArrayList<ScanResult>();
		mAdapter.update(networks);
	}
	
	
	private List<ScanResult> getNetworks() {
		List<ScanResult> networks = null;
		
		// Check if WiFi is connected
        ConnectivityManager connManager = 
                (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo wifiStatus = 
                connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if ( !wifiStatus.isAvailable() ) {
            Toast.makeText(getApplicationContext(), 
                    R.string.connection_string, Toast.LENGTH_LONG).show();
            setResult(CLOSE_ALL_ACTIVITIES);
            finish();
        } else {
            WifiManager wm = (WifiManager) getSystemService(Context.WIFI_SERVICE);
            networks = wm.getScanResults();

        }
		return networks;
	}
}

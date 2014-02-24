package alberto.test.wifiinfo;

import java.nio.ByteOrder;
import java.util.List;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class MainActivity extends Activity {

	// Result code
	private static final int CLOSE_ALL_ACTIVITIES = 900;
	
	// Activities code
	private static final int CHANNEL_ACTIVITY = 1000;
	
	
	// IDs for menu items
	private static final int MENU_REFRESH = Menu.FIRST;
	private static final int MENU_CHANNEL = Menu.FIRST + 1;
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
	
	
	@Override
	public void onResume() {
		super.onResume();
		setInfo();
        //setMockupInfo();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		menu.add(Menu.NONE, MENU_REFRESH, Menu.NONE, R.string.refresh_string);
		menu.add(Menu.NONE, MENU_CHANNEL, Menu.NONE, R.string.channels_string);
		return true;
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_REFRESH:
			setInfo();
			return true;
		case MENU_CHANNEL:
			Intent i = new Intent(getApplicationContext(), ChannelActivity.class);
			startActivityForResult(i, CHANNEL_ACTIVITY);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    switch (resultCode) {
	        case CLOSE_ALL_ACTIVITIES:
				setResult(CLOSE_ALL_ACTIVITIES);
				finish();
				break;
	    }
	    super.onActivityResult(requestCode, resultCode, data);
	}

	
	private void setInfo() {
		// Check if WiFi is connected
        ConnectivityManager connManager = 
                (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo wifiStatus = 
                connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if ( !wifiStatus.isConnected() ) {
            Toast.makeText(getApplicationContext(), 
                    R.string.connection_string, Toast.LENGTH_LONG).show();
            finish();
        } else {
           
	        // Fields
	        TextView txtIP  		= (TextView) findViewById(R.id.txtIP);
	        TextView txtMAC 		= (TextView) findViewById(R.id.txtMAC);
	        TextView txtBSSID 		= (TextView) findViewById(R.id.txtBSSID);
	        TextView txtSSID 		= (TextView) findViewById(R.id.txtSSID);
	        TextView txtHiddenSSID  = (TextView) findViewById(R.id.txtHiddenSSID);
	        TextView txtLinkSpeed   = (TextView) findViewById(R.id.txtLinkSpeed);
	        TextView txtRSSI	    = (TextView) findViewById(R.id.txtRSSI);
	        TextView txtLevel		= (TextView) findViewById(R.id.txtLevel);
	        TextView txtEncryption  = (TextView) findViewById(R.id.txtEncryption);
	        TextView txtChannel		= (TextView) findViewById(R.id.txtChannel);
	        TextView txtFrecuency	= (TextView) findViewById(R.id.txtFrecuency);
	        TextView txtDHCPServer	= (TextView) findViewById(R.id.txtDHCPServer);
	        TextView txtDNS1		= (TextView) findViewById(R.id.txtDNS1);
	        TextView txtDNS2		= (TextView) findViewById(R.id.txtDNS2);
	        TextView txtGateway		= (TextView) findViewById(R.id.txtGateway);
	        TextView txtNetmask		= (TextView) findViewById(R.id.txtNetmask);
	        TextView txtLease		= (TextView) findViewById(R.id.txtLease);
	
	
	        WifiManager wm = (WifiManager) getSystemService(Context.WIFI_SERVICE);
	
	        // Connection Info
	        txtMAC.setText(wm.getConnectionInfo().getMacAddress());
	        txtIP.setText(intToIp(wm.getConnectionInfo().getIpAddress()));
	        txtBSSID.setText(wm.getConnectionInfo().getBSSID());
	        txtSSID.setText(wm.getConnectionInfo().getSSID());
	        if (wm.getConnectionInfo().getHiddenSSID()) {
	            txtHiddenSSID.setText(R.string.hidden_string);
	        } else {
	            txtHiddenSSID.setText(R.string.visible_string);
	        }
	        txtLinkSpeed.setText(
	        		wm.getConnectionInfo().getLinkSpeed() + " Mbps");
	        txtRSSI.setText(wm.getConnectionInfo().getRssi() + " dBm");
	        
	        // ScanResult
	        ScanResult network = getScanResult(wm, wm.getConnectionInfo().getBSSID());
	        txtLevel.setText(network.level + " dBm");
	        txtEncryption.setText(network.capabilities);
	        txtChannel.setText(String.valueOf(ChannelExtractor.getChannelFromFrequency(network.frequency)));
	        txtFrecuency.setText(network.frequency + " MHz");
	
	        // DHCP Info
	        txtDHCPServer.setText(intToIp(wm.getDhcpInfo().serverAddress));
	        txtDNS1.setText(intToIp(wm.getDhcpInfo().dns1));
	        txtDNS2.setText(intToIp(wm.getDhcpInfo().dns2));
	        txtGateway.setText(intToIp(wm.getDhcpInfo().gateway));
	        txtNetmask.setText(intToIp(wm.getDhcpInfo().netmask));
	        txtLease.setText(String.valueOf(
	                wm.getDhcpInfo().leaseDuration / 60 / 60 ) + " h.");
        }
	}
	
	
	private void setMockupInfo() {
        // Fields
        TextView txtIP  		= (TextView) findViewById(R.id.txtIP);
        TextView txtMAC 		= (TextView) findViewById(R.id.txtMAC);
        TextView txtBSSID 		= (TextView) findViewById(R.id.txtBSSID);
        TextView txtSSID 		= (TextView) findViewById(R.id.txtSSID);
        TextView txtHiddenSSID  = (TextView) findViewById(R.id.txtHiddenSSID);
        TextView txtLinkSpeed   = (TextView) findViewById(R.id.txtLinkSpeed);
        TextView txtRSSI	    = (TextView) findViewById(R.id.txtRSSI);
        TextView txtLevel		= (TextView) findViewById(R.id.txtLevel);
        TextView txtEncryption	= (TextView) findViewById(R.id.txtEncryption);
        TextView txtChannel		= (TextView) findViewById(R.id.txtChannel);
        TextView txtFrecuency	= (TextView) findViewById(R.id.txtFrecuency);
        TextView txtDHCPServer	= (TextView) findViewById(R.id.txtDHCPServer);
        TextView txtDNS1		= (TextView) findViewById(R.id.txtDNS1);
        TextView txtDNS2		= (TextView) findViewById(R.id.txtDNS2);
        TextView txtGateway		= (TextView) findViewById(R.id.txtGateway);
        TextView txtNetmask		= (TextView) findViewById(R.id.txtNetmask);
        TextView txtLease		= (TextView) findViewById(R.id.txtLease);
        
        txtIP.setText("192.168.0.92");
        txtMAC.setText("00:11:22:33:44:55");
        txtSSID.setText("WLAN");
        txtHiddenSSID.setText(R.string.visible_string);
        txtBSSID.setText("00:11:00:11:00:11");
        txtLinkSpeed.setText("36 Mbps");
        txtRSSI.setText("-74 dBm");
        txtLevel.setText("-84 dBm");
        txtEncryption.setText("[WPA2-PSK-CCMP]");
        txtChannel.setText("11");
        txtFrecuency.setText("2462");
        txtDHCPServer.setText("192.168.0.2");
        txtDNS1.setText("52.128.98.1");
        txtDNS2.setText("52.128.98.2");
        txtGateway.setText("192.168.0.1");
        txtNetmask.setText("255.255.255.0");
        txtLease.setText("24 h.");
	}
	

    private String intToIp(int i) {
        // Convert little-endian to big-endianif needed
        if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
            i = Integer.reverseBytes(i);
        }

        return ((i >> 24 ) & 0xFF ) + "." +
            ((i >> 16 ) & 0xFF) + "." +
            ((i >> 8 ) & 0xFF) + "." +
            ( i & 0xFF) ;
    }
    
    private ScanResult getScanResult(WifiManager wm, String BSSID) {
    	List<ScanResult> networkList = wm.getScanResults();
    	for (ScanResult network : networkList) {
    		if (network.BSSID.equals(BSSID)) {
    			return network;
    		} 
        }
    	return null;
    }
}

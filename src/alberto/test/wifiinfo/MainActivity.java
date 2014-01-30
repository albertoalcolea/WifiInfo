package alberto.test.wifiinfo;

import java.nio.ByteOrder;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        TextView txtIP  		= (TextView) findViewById(R.id.txtIP);
        TextView txtMAC 		= (TextView) findViewById(R.id.txtMAC);
        TextView txtBSSID 		= (TextView) findViewById(R.id.txtBSSID);
        TextView txtSSID 		= (TextView) findViewById(R.id.txtSSID);
        TextView txtHiddenSSID  = (TextView) findViewById(R.id.txtHiddenSSID);
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

        // DHCP Info
        txtDHCPServer.setText(intToIp(wm.getDhcpInfo().serverAddress));
        txtDNS1.setText(intToIp(wm.getDhcpInfo().dns1));
        txtDNS2.setText(intToIp(wm.getDhcpInfo().dns2));
        txtGateway.setText(intToIp(wm.getDhcpInfo().gateway));
        txtNetmask.setText(intToIp(wm.getDhcpInfo().netmask));
        txtLease.setText(String.valueOf(wm.getDhcpInfo().leaseDuration / 60 / 60 ) + "h.");
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
}

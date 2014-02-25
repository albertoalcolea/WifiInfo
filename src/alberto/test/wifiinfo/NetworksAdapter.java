package alberto.test.wifiinfo;

import java.util.List;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class NetworksAdapter extends BaseAdapter {

	// List of networks
	private List<ScanResult> networks;
	
	private final Context mContext;
	
	
	public NetworksAdapter(Context context, List<ScanResult> networks) {
		this.mContext = context;
		this.networks = networks;
	}
	
	
	public void update(List<ScanResult> networks) {
        this.networks = networks;
        notifyDataSetChanged();
    }
	
	
	@Override
	public int getCount() {
		return networks.size();
	}
	

	@Override
	public Object getItem(int position) {
		return networks.get(position);
	}
	

	@Override
	public long getItemId(int position) {
		return position;
	}
	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ScanResult network = (ScanResult) getItem(position);
		
		final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final RelativeLayout networkLayout = (RelativeLayout) inflater.inflate(R.layout.network, parent, false);
		
		final TextView txtSSID 		 = (TextView) networkLayout.findViewById(R.id.txtSSID);
		final TextView txtBSSID 	 = (TextView) networkLayout.findViewById(R.id.txtBSSID);
		final TextView txtLevel 	 = (TextView) networkLayout.findViewById(R.id.txtLevel);
		final TextView txtEncryption = (TextView) networkLayout.findViewById(R.id.txtEncryption);
		final TextView txtChannel	 = (TextView) networkLayout.findViewById(R.id.txtChannel);
		
		txtSSID.setText(network.SSID);
		txtBSSID.setText(network.BSSID);
		txtLevel.setText(String.valueOf(network.level) + " dBm");
		txtEncryption.setText(network.capabilities);
		txtChannel.setText(mContext.getText(R.string.channel_string) + ": " +
				String.valueOf(ChannelExtractor.getChannelFromFrequency(network.frequency)));
		
		return networkLayout;
	}

}

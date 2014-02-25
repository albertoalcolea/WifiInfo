package alberto.test.wifiinfo;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import android.annotation.SuppressLint;
import android.net.wifi.ScanResult;

public class ChannelUsage {
	@SuppressLint("UseSparseArrays")
	private Map<Integer, Integer> usage = new HashMap<Integer, Integer>();
	private int numNetworks = 0;
	
	public ChannelUsage() {
		for (int i=1; i<=14; i++) {
			usage.put(i, 0);
		}
	}
	
	public void addNetwork(ScanResult network) {
		numNetworks++;
		int channel = ChannelExtractor.getChannelFromFrequency(network.frequency); 
		usage.put(channel, usage.get(channel) + 1);
	}
	
	public int getUsage(int channel) {
		return usage.get(channel);
	}
	
	public int getUsagePercentage(int channel) {
		return (int)(((float)usage.get(channel) * 100.0)/(float)numNetworks);
	}
	
	public int getNumNetworks() {
		return numNetworks;
	}
	
	public String getBestChannel(int num) {
		TreeMap<Integer, Integer> sorted = (TreeMap<Integer, Integer>) sortByValues(usage);
		LinkedList<Integer> listSorted = new LinkedList<Integer>(sorted.keySet());
		
		String bestChannels = "";
		for (int i=0; i<num; i++) {
			bestChannels = bestChannels + listSorted.removeFirst() + ", ";
		}
		
		return bestChannels;
	}
	
	
	private static <K, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map) {
	    Comparator<K> valueComparator =  new Comparator<K>() {
	        public int compare(K k1, K k2) {
	            int compare = map.get(k2).compareTo(map.get(k1));
	            if (compare == 0) return 1;
	            else return -compare;
	        }
	    };
	    Map<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
	    sortedByValues.putAll(map);
	    return sortedByValues;
	}
}

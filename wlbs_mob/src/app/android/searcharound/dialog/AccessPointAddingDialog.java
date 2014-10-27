package app.android.searcharound.dialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.json.JSONObject;
import android.content.Context;
import android.content.DialogInterface;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import app.android.searcharound.R;
import app.android.searcharound.adapter.ListViewAccessPointAddingAdatper;
import app.android.searcharound.common.OPCODE;
import app.android.searcharound.common.PREFS_CODE;
import app.android.searcharound.model.ShopAccessPoint;
import app.android.searcharound.service.ShopOwnerService;
import app.android.searcharound.utility.AlertBox;
import app.android.searcharound.utility.InterfaceManager;
import app.android.searcharound.utility.SecurePreferences;

public class AccessPointAddingDialog implements DialogInterface.OnClickListener
{
	private Context context;
	private ListView accessPointList;
	private ListViewAccessPointAddingAdatper accessPointAdapter;
	private InterfaceManager iManager;
	private View view;
	
	public AccessPointAddingDialog(Context context)
	{
		this.context = context;
	}
	
	public void setInterfaceManager(InterfaceManager iManager)
	{
		this.iManager = iManager;
	}
	
	public void show()
	{
		LayoutInflater inflater = LayoutInflater.from(context);
		view = inflater.inflate(R.layout.dialog_access_point_adding, null);
		
	
		accessPointList = (ListView) view.findViewById(R.id.listview_access_point);
		accessPointAdapter = new ListViewAccessPointAddingAdatper(context);
		
		WifiManager wf_manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		List<ScanResult> results = wf_manager.getScanResults();
		
		ArrayList<ShopAccessPoint> items = new ArrayList<>();
		Collections.sort(results, new Comparator<ScanResult>() {

			@Override
			public int compare(ScanResult lhs, ScanResult rhs) {
				return String.CASE_INSENSITIVE_ORDER.compare(lhs.SSID,
						rhs.SSID);
			}
		});
		
		for (ScanResult result : results)
		{
			ShopAccessPoint item = new ShopAccessPoint();
			item.name = result.SSID;
			item.bssid = result.BSSID;
			items.add(item);
		}
		
		accessPointAdapter.setItems(items);
		accessPointList.setAdapter(accessPointAdapter);
		
		AlertBox.showInputMessageBox(context, "Add new access point", this, null, view);
	}
	
	@Override
	public void onClick(DialogInterface dialog, int which)
	{
			
		SecurePreferences pref = new SecurePreferences(context, PREFS_CODE.PREFS_CODE_NAME, 
				PREFS_CODE.PRIVATE_KEY, true);
		String shopId_str = pref.getString(PREFS_CODE.SHOP_ID);
		if (shopId_str != null)
		{
			try
			{
				int shopId = Integer.parseInt(shopId_str);
				ShopAccessPoint ap = accessPointAdapter.getSelectedAccessPoint();
				ShopOwnerService service = new ShopOwnerService();
				JSONObject response = service.addAccessPoint(ap.name, ap.bssid, shopId);
				
				int responseCode = response.getInt("ResponseCode");
				switch (responseCode) 
				{
					case OPCODE.SERVER_SAVE_SUCCESS_RESPONSE:
						AlertBox.showMessageBox(context, "Succesfully", 
								"System has been added access point to system.");
						break;
	
					case OPCODE.SERVER_SAVE_UNSUCCESS_RESPONSE:
						AlertBox.showErrorMessage(context, "(Cannot Saved!)");
						break;
						
					case OPCODE.SERVER_ERROR_DUPLICATE_BSSID_RESPONSE:
						AlertBox.showMessageBox(context, "Duplication", 
								"This access point has been registered.");
						break;

				}
			}
			catch (Exception e)
			{
				AlertBox.showErrorMessage(context, "(Network unavailable)");
			}
		}
		if (iManager != null) iManager.setData();
	}

}

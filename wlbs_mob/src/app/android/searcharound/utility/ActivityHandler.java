package app.android.searcharound.utility;

import android.app.Activity;
import android.os.Bundle;

public abstract class ActivityHandler 
{
	public Activity root;
	public abstract void onCreate(Bundle savedInstanceState, Bundle param);
}

package app.android.searcharound.utility;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class ImgLoader 
{
	private Context context;
	private String url;
	private ImageLoader imageLoader;
	private DisplayImageOptions.Builder optionsBuilder;
	private ProgressBar spinner;
	private ImageView imgView;
	private int w, h;

	public ImgLoader(String url, ImageView imgView, Context context,
			ProgressBar spinner) {
		this.imgView = imgView;
		this.url = url;
		this.context = context;
		this.spinner = spinner;
		init();
	}

	private void init() {
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheInMemory(true).imageScaleType(ImageScaleType.EXACTLY)
				.displayer(new FadeInBitmapDisplayer(300)).build();

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).defaultDisplayImageOptions(defaultOptions)
				.memoryCache(new WeakMemoryCache()).build();

		ImageLoader.getInstance().init(config);
	}

	public void showImageOnFail(int imgRes) {

		imageLoader = ImageLoader.getInstance();
		optionsBuilder = new DisplayImageOptions.Builder();
		optionsBuilder.cacheInMemory(true);
		optionsBuilder.cacheOnDisk(true);
		optionsBuilder.resetViewBeforeLoading(true);
		optionsBuilder.showImageForEmptyUri(imgRes);
		optionsBuilder.showImageOnFail(imgRes);

	}

	public void setSize(int w, int h) {
		this.w = w;
		this.h = h;
	}

	public void load() {
		if (w + h != 0)
		{
			optionsBuilder.postProcessor(new BitmapProcessor() {
	
				@Override
				public Bitmap process(Bitmap bmp) {
	
					return Bitmap.createScaledBitmap(bmp, w, h, false);
				}
			});
		}

		imageLoader.displayImage(url, imgView, optionsBuilder.build(), 
				new SimpleImageLoadingListener() {
					@Override
					public void onLoadingStarted(String imageUri, View view) {
						spinner.setVisibility(View.VISIBLE);
					}

					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						spinner.setVisibility(View.GONE);
					}

					@Override
					public void onLoadingCancelled(String imageUri, View view) {
						spinner.setVisibility(View.GONE);
					}
					
					@Override
					public void onLoadingFailed(String imageUri, View view, FailReason reason) {
						spinner.setVisibility(View.GONE);
						
					}	
				});
	}
}

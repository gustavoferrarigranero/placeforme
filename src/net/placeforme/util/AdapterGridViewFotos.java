package net.placeforme.util;

import java.util.ArrayList;

import net.placeforme.model.Foto;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class AdapterGridViewFotos extends BaseAdapter {

	private Context mContext;
	private ArrayList<Foto> fotos;

	public AdapterGridViewFotos(Context c, ArrayList<Foto> fotos) {
		mContext = c;
		this.fotos = fotos;
	}

	public int getCount() {
		return fotos.size();
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	// create a new ImageView for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) { // if it's not recycled, initialize some
									// attributes
			imageView = new ImageView(mContext);
			imageView.setLayoutParams(new GridView.LayoutParams(180, 180));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(8, 8, 8, 8);
		} else {
			imageView = (ImageView) convertView;
		}

		imageView.setImageBitmap(fotos.get(position).getFoto());

		return imageView;
	}

}
package net.placeforme.util;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

/**
 * Created by Gustavo on 14/08/2014.
 */
public class Conv {

	/**
	 * @param encodedString
	 * @return java.sql.Date (from given date string)
	 */
	public static java.sql.Date stringToSqlDate(String data) {
		java.sql.Date dataFinal = null;
		try {
			dataFinal = new java.sql.Date(new SimpleDateFormat("dd/MM/yyyy").parse(data).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dataFinal; 
	}
	
	/**
	 * @param java.sql.Date
	 * @return String (from given java.sql.Date)
	 */
	public static String sqlDateToString(java.sql.Date data) {
	  
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");  
		String text = df.format(data);  
		return text;
	
	}

	/**
	 * @param bitmap
	 * @return converting bitmap and return a string
	 */
	public static String BitMapToString(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		byte[] b = baos.toByteArray();
		String temp = Base64.encodeToString(b, Base64.DEFAULT);
		return temp;
	}

	/**
	 * @param encodedString
	 * @return bitmap (from given string)
	 */
	public static Bitmap StringToBitMap(String encodedString) {
		try {
			byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
			Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
					encodeByte.length);
			return bitmap;
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}
}

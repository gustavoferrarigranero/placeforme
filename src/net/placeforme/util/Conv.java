package net.placeforme.util;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
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
			dataFinal = new java.sql.Date(new SimpleDateFormat("dd/MM/yyyy")
					.parse(data).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dataFinal;
	}

	/**
	 * @param java
	 *            .sql.Date
	 * @return String (from given java.sql.Date)
	 */
	public static String sqlDateToString(java.sql.Date data) {

		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String text = df.format(data);
		return text;

	}
	
	/**
	 * @param encodedString
	 * @return java.sql.Time (from given time string)
	 */
	public static java.sql.Time stringToSqlTime(String hora) {
		java.sql.Time horaFinal = null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
			horaFinal = new java.sql.Time(formatter.parse(hora).getTime());

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return horaFinal;
	}

	/**
	 * @param java
	 *            .sql.Date
	 * @return String (from given java.sql.Date)
	 */
	public static String sqlTimeToString(java.sql.Time hora) {

		SimpleDateFormat df = new SimpleDateFormat("H:mm");
		String text = df.format(hora);
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

	public static Bitmap ShrinkBitmap(String file, int width, int height) {

		BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
		bmpFactoryOptions.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);

		int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight
				/ (float) height);
		int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth
				/ (float) width);

		if (heightRatio > 1 || widthRatio > 1) {
			if (heightRatio > widthRatio) {
				bmpFactoryOptions.inSampleSize = heightRatio;
			} else {
				bmpFactoryOptions.inSampleSize = widthRatio;
			}
		}

		bmpFactoryOptions.inJustDecodeBounds = false;
		bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);
		return bitmap;
	}
	
	public static Bitmap circleImage(Bitmap image){
		
		 Bitmap circleBitmap = Bitmap.createBitmap(image.getWidth(), image.getHeight(), Bitmap.Config.ARGB_8888);
         
         BitmapShader shader = new BitmapShader (image,  TileMode.CLAMP, TileMode.CLAMP);
         Paint paint = new Paint();
         paint.setShader(shader);
         
         Canvas c = new Canvas(circleBitmap);
         c.drawCircle(image.getWidth()/2, image.getHeight()/2, image.getWidth()/2, paint);

		return circleBitmap;
	
	}
}

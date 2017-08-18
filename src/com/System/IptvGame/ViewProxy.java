package com.System.IptvGame;

import java.nio.Buffer;
import java.nio.ShortBuffer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

public class ViewProxy
{
	private static Paint fbPaint;
	private static Canvas txtCanvas;
	
	public ViewProxy()
	{
		fbPaint = new Paint();
		fbPaint.setAntiAlias(true);
		fbPaint.setTextAlign(Paint.Align.LEFT);
		txtCanvas = new Canvas();
	}
	
	public void drawChars(int paramInt1, Bitmap paramBitmap, int paramInt2,
			int paramInt3, int paramInt4, String paramString, int paramInt5)
	{
		fbPaint.setColor(0xFF000000 | paramInt1);
		if (((paramInt3 & 0x1) != 0) && ((paramInt3 & 0x2) != 0))
		{
			fbPaint.setTypeface(Typeface.defaultFromStyle(3));
		}
		else if ((paramInt3 & 0x1) != 0)
		{
			fbPaint.setTypeface(Typeface.defaultFromStyle(1));
		}
		else if ((paramInt3 & 0x2) != 0)
		{
			fbPaint.setTypeface(Typeface.defaultFromStyle(2));
		}
		else
		{
			fbPaint.setTypeface(Typeface.defaultFromStyle(0));
		}
		txtCanvas.setBitmap(paramBitmap);
		txtCanvas.drawText(paramString, 0.0F, paramInt5, fbPaint);
	}

	public void drawChars(int paramInt1, Buffer paramBuffer, int paramInt2,
			int paramInt3, int paramInt4, int paramInt5, int paramInt6,
			String paramString, int paramInt7)
	{
		fbPaint.setTextSize(paramInt6);
		fbPaint.setColor(0xFF000000 | paramInt1);
		if (((paramInt5 & 0x1) != 0) && ((paramInt5 & 0x2) != 0))
		{
			fbPaint.setTypeface(Typeface.defaultFromStyle(3));
		}
		else if ((paramInt5 & 0x1) != 0)
		{
			fbPaint.setTypeface(Typeface.defaultFromStyle(1));
		}
		else if ((paramInt5 & 0x2) != 0)
		{
			fbPaint.setTypeface(Typeface.defaultFromStyle(2));
		}
		else
		{
			fbPaint.setTypeface(Typeface.defaultFromStyle(0));
		}
		Bitmap localBitmap = Bitmap.createBitmap(paramInt2, paramInt3,
				Bitmap.Config.RGB_565);
		localBitmap.copyPixelsFromBuffer(paramBuffer);
		txtCanvas.setBitmap(localBitmap);
		txtCanvas.drawText(paramString, 0.0F, paramInt7, fbPaint);
		localBitmap.copyPixelsToBuffer(paramBuffer);
	}

	public void drawChars(int paramInt1, short[] paramArrayOfShort,
			int paramInt2, int paramInt3, int paramInt4, int paramInt5,
			int paramInt6, String paramString, int paramInt7)
	{
		fbPaint.setTextSize(paramInt6);
		fbPaint.setColor(0xFF000000 | paramInt1);
		if (((paramInt5 & 0x1) != 0) && ((paramInt5 & 0x2) != 0))
		{
			fbPaint.setTypeface(Typeface.defaultFromStyle(3));
		}
		else if ((paramInt5 & 0x1) != 0)
		{
			fbPaint.setTypeface(Typeface.defaultFromStyle(1));
		}
		else if ((paramInt5 & 0x2) != 0)
		{
			fbPaint.setTypeface(Typeface.defaultFromStyle(2));
		}
		else
		{
			fbPaint.setTypeface(Typeface.defaultFromStyle(0));
		}
		ShortBuffer localShortBuffer = ShortBuffer.wrap(paramArrayOfShort);
		Bitmap localBitmap = Bitmap.createBitmap(paramInt2, paramInt3,
				Bitmap.Config.RGB_565);
		localBitmap.copyPixelsFromBuffer(localShortBuffer);
		txtCanvas.setBitmap(localBitmap);
		txtCanvas.drawText(paramString, 0.0F, paramInt7, fbPaint);
		localBitmap.copyPixelsToBuffer(localShortBuffer);
	}

	public int getCharsWidth(int paramInt1, int paramInt2, int paramInt3,
			String paramString, int paramInt4)
	{
		fbPaint.setTextSize(paramInt3);
		if (((paramInt2 & 0x1) != 0) && ((paramInt2 & 0x2) != 0))
		{
			fbPaint.setTypeface(Typeface.defaultFromStyle(3));
		}
		else if ((paramInt2 & 0x1) != 0)
		{
			fbPaint.setTypeface(Typeface.defaultFromStyle(1));
		}
		else if ((paramInt2 & 0x2) != 0)
		{
			fbPaint.setTypeface(Typeface.defaultFromStyle(2));
		}
		else
		{
			fbPaint.setTypeface(Typeface.defaultFromStyle(0));
		}
		if(paramInt4 < paramString.length())
			return (int) fbPaint.measureText(paramString, 0, paramInt4);
		else
			return (int) fbPaint.measureText(paramString);
		
	}

	public void getFontInfo(int paramInt1, int paramInt2, int paramInt3,
			int[] paramArrayOfInt)
	{
		fbPaint.setTextSize(paramInt3);
		if (((paramInt2 & 0x1) != 0) && ((paramInt2 & 0x2) != 0))
		{
			fbPaint.setTypeface(Typeface.defaultFromStyle(3));
		}
		else if ((paramInt2 & 0x1) != 0)
		{
			fbPaint.setTypeface(Typeface.defaultFromStyle(1));
		}
		else if ((paramInt2 & 0x2) != 0)
		{
			fbPaint.setTypeface(Typeface.defaultFromStyle(2));
		}
		else
		{
			fbPaint.setTypeface(Typeface.defaultFromStyle(0));
		}
		Paint.FontMetricsInt localFontMetricsInt = fbPaint.getFontMetricsInt();
		paramArrayOfInt[0] = Math.abs(localFontMetricsInt.ascent);
		paramArrayOfInt[1] = localFontMetricsInt.descent;
		paramArrayOfInt[2] = localFontMetricsInt.leading;
	}
}

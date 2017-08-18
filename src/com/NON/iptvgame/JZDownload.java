package com.NON.iptvgame;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.content.Context;
import android.util.Log;

public class JZDownload {

	private static final String TAG = "JZDownload";
	public static final int DOWNLOAD_SUCCESS = 0;
	public static final int DOWNLOAD_FAILED = 1;
	private Context mContext;
	private String mUrl;
	private String mPath;
	private IDownloadFinish mListener;
	private int mId;

	public interface IDownloadFinish {
		void onDownloadFinish(int id, int arg);
	}

	public JZDownload(Context context, String url, String path, int timeout) {
		mContext = context;
		mUrl = url;
		mPath = path;
		mId = hashCode();
	}

	public void setOnDownloadFinish(IDownloadFinish listener) {
		mListener = listener;
	}

	public int getDownloadId() {
		return mId;
	}

	public void startDownload() {

		new Thread() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();

				InputStream inputStream = null;
				FileOutputStream outputStream = null;

				try {

					HttpParams params = new BasicHttpParams();
					ConnManagerParams.setTimeout(params, 5000);
					HttpConnectionParams.setConnectionTimeout(params, 5000);
					HttpConnectionParams.setSoTimeout(params, 5000);

					HttpClient httpclient = new DefaultHttpClient(params);

					Log.i(TAG, "mUrl = " + mUrl);

					HttpGet httpget = new HttpGet(mUrl);
					httpget.setHeader("Connection", "Close");

					HttpResponse httpResponse = httpclient.execute(httpget);
					int statusCode = httpResponse.getStatusLine()
							.getStatusCode();
					Log.i(TAG, "statusCode = " + statusCode);
					if (statusCode == HttpStatus.SC_OK) {

						File file = new File(mPath);
						if (file.exists()) {
							file.delete();
						}

						outputStream = new FileOutputStream(file);
						inputStream = httpResponse.getEntity().getContent();
						byte b[] = new byte[4096];
						int i = 0;
						while ((i = inputStream.read(b)) != -1) {
							outputStream.write(b, 0, i);
						}
						outputStream.flush();

						Log.i(TAG, "download success");
						if (mListener != null) {
							mListener.onDownloadFinish(mId, DOWNLOAD_SUCCESS);
						}
						return;
					}

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				} finally {
					if (inputStream != null) {
						try {
							inputStream.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if (outputStream != null) {
						try {
							outputStream.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				Log.i(TAG, "download failed");
				if (mListener != null) {
					mListener.onDownloadFinish(mId, DOWNLOAD_FAILED);
				}
			}

		}.start();
	}
}

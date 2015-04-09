package com.ganggang.Stock.Monitor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.nodes.Document;


public class SinaProxy {

	public SinaProxy() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		GetPriceByCode("sh601003");
		for (int i = 0; i < 1000; i++) {
			Thread.currentThread().sleep(1000);
			System.out.println("a"+2);
		}
	}

	public static void GetPriceByCode(String code) {
		StockModel model = new StockModel();
		String priceData = requestStock(code);
		System.out.println(priceData);
	}

	public static String requestStock(String stockCode) {
		String urlAll = "http://hq.sinajs.cn/list=" + stockCode;
		String charset = "GBK";

		System.out.println(urlAll);

		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

		try {
			URL url = new URL(urlAll);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod("GET");
			connection.setReadTimeout(30000);
			connection.setConnectTimeout(30000);
			connection.setRequestProperty("User-agent", userAgent);
			connection.connect();
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, charset));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
				sbf.append("\r\n");
			}
			reader.close();
			result = sbf.toString();
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return result;
	}
}

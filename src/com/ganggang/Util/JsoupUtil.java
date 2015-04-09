package com.ganggang.Util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JsoupUtil {
	public static Document GetDocument(String url,String proxyIP,int proxyPort) {
		boolean flag = true;
		Document doc=null;
		int count=0;
		while (flag) {
			try {
				doc=Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31").timeout(60000).get();
//				String html=HttpClientUtil.GetHtml(url,proxyIP,proxyPort);
//				doc=Jsoup.parse(html);
				flag=false;
			} catch (Exception ex) {
				doc=null;
				flag=true;
				System.out.println("111111111111111111111");
				System.out.println(ex.getMessage());
				try {
					flag=false;
					System.out.println(ex.getMessage()+":"+url);
					Thread.sleep(3000);
				} catch (InterruptedException e) {
				}
			}
		}
		return doc;
	}
	public static Document GetDocument(String url) {
		return GetDocument(url, "64.239.223.66", 1080);
	}
	public static void main(String[] args){
		System.out.println(GetDocument("http://camelcamelcamel.com", "115.28.11.165", 8888));
	}
}
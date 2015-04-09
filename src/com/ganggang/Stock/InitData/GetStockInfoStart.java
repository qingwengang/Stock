package com.ganggang.Stock.InitData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import com.ganggang.Stock.Dao.StockInfoDao;
import com.ganggang.Stock.Entity.StockInfo;
import com.ganggang.Stock.Entity.StockTransactionDetail;
import com.ganggang.Util.JsoupUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GetStockInfoStart {

	public static void main(String[] args) {
//		getStockInfo();
		GetHistoryThread th = new GetHistoryThread(2014,2015);
		for (int i = 0; i < 30; i++) {
			Thread t1 = new Thread(th);
			t1.start();
		}
	}

	//获取股票名称等信息
	public static void getStockInfo() {
		System.out.println("获取股票信息开始--");
		String url = "http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHQNodeData?page=%s&num=80&sort=changepercent&asc=0&node=hs_a&symbol=&_s_r_a=page";
		for (int i = 1; i < 34; i++) {
			String ru = String.format(url, i);
			Document doc = JsoupUtil.GetDocument(ru);
			Gson gson = new Gson();
			LinkedList<StockPriceInfo> emp = gson.fromJson(doc.body().text(),
					new TypeToken<LinkedList<StockPriceInfo>>() {
					}.getType());
			for (StockPriceInfo info : emp) {
				StockInfo si = new StockInfo();
				si.setCode(info.code);
				si.setName(info.name);
				si.setNewPrice(Double.parseDouble(info.trade));
				si.setVolume(info.volume);
				si.setSymbol(info.symbol);
				si.setIfGetHistory(0);
				si.setIfSetVolumeRate(0);
				StockInfoDao.AddStockInfo(si);
			}
		}
		System.out.println("获取股票信息结束--");
	}
}

class StockPriceInfo {
	public String symbol;
	public String code;
	public String name;
	public String trade;
	public String pricechange;
	public String changepercent;
	public String buy;
	public String settlement;
	public String open;
	public String high;
	public String low;
	public long volume;
	public long amount;
	public String ticktime;

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTrade() {
		return trade;
	}

	public void setTrade(String trade) {
		this.trade = trade;
	}

	public String getPricechange() {
		return pricechange;
	}

	public void setPricechange(String pricechange) {
		this.pricechange = pricechange;
	}

	public String getChangepercent() {
		return changepercent;
	}

	public void setChangepercent(String changepercent) {
		this.changepercent = changepercent;
	}

	public String getBuy() {
		return buy;
	}

	public void setBuy(String buy) {
		this.buy = buy;
	}

	public String getSettlement() {
		return settlement;
	}

	public void setSettlement(String settlement) {
		this.settlement = settlement;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public String getHigh() {
		return high;
	}

	public void setHigh(String high) {
		this.high = high;
	}

	public String getLow() {
		return low;
	}

	public void setLow(String low) {
		this.low = low;
	}

	public long getVolume() {
		return volume;
	}

	public void setVolume(long volume) {
		this.volume = volume;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public String getTicktime() {
		return ticktime;
	}

	public void setTicktime(String ticktime) {
		this.ticktime = ticktime;
	}
}

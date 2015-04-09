package com.ganggang.Stock.InitData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ganggang.Stock.Dao.StockInfoDao;
import com.ganggang.Stock.Dao.StockTransactionDetailDao;
import com.ganggang.Stock.Entity.StockInfo;
import com.ganggang.Stock.Entity.StockTransactionDetail;
import com.ganggang.Util.FormatUtil;
import com.ganggang.Util.JsoupUtil;

public class GetHistoryThread implements Runnable {

	private int beginYear;
	private int endYear;
	public GetHistoryThread(int beginYear,int endYear){
		this.beginYear=beginYear;
		this.endYear=endYear;
	}
	
	private synchronized StockInfo GetUnSpiderData() {
		StockInfo info = null;
		try {
			info = StockInfoDao.GetUnSpiderHistoryStock();
		} catch (Exception e) {
			System.out.println(e);
		}
		return info;
	}

	@Override
	public void run() {
		GetHistory(beginYear,endYear);
	}

	private void GetHistory(int beginYear, int endYear) {
		StockInfo info = GetUnSpiderData();
		try {
			while (info != null) {
				List<Date> dts=GetHasHistoryDate(info.getCode());
				List<StockTransactionDetail> details=new LinkedList<StockTransactionDetail>();
				String url = "http://vip.stock.finance.sina.com.cn/corp/go.php/vMS_MarketHistory/stockid/%s.phtml?year=2015&jidu=1";
				String ur = String.format(url, info.getCode());
				Document doc = JsoupUtil.GetDocument(ur);
				Element year = doc.select("select[name=year]").get(0);
				Elements years = year.select("option");
				for (Element y : years) {
					if (Integer.parseInt(y.text().trim()) >= beginYear
							&& Integer.parseInt(y.text().trim()) <= endYear) {
						System.out.println(info.getCode() + "_" + y.text());
						String uurl = "http://vip.stock.finance.sina.com.cn/corp/go.php/vMS_MarketHistory/stockid/%s.phtml?year=%s&jidu=%s";
						for (int j = 1; j < 5; j++) {
							String uu = String.format(uurl, info.getCode(),
									y.text(), j);
							doc = JsoupUtil.GetDocument(uu);
							Element tb = doc
									.getElementById("FundHoldSharesTable");
							if (tb != null) {
								Elements trs = tb.getElementsByTag("tbody")
										.get(0).select("tr");
								for (int i = 1; i < trs.size(); i++) {
									StockTransactionDetail detail = new StockTransactionDetail();
									SimpleDateFormat sdf = new SimpleDateFormat(
											"yyyy-MM-dd");
									Date transactionDate=sdf.parse(trs
												.get(i).getElementsByTag("tr")
												.get(0).getElementsByTag("td")
												.get(0).text());
									if(!dts.contains(transactionDate)){
										detail.setTransactionDate(transactionDate);
										detail.setCode(info.getCode());
										detail.setBeginPrice(Double.parseDouble(trs
												.get(i).getElementsByTag("tr")
												.get(0).getElementsByTag("td")
												.get(1).text()));
										detail.setEndPrice(Double.parseDouble(trs
												.get(i).getElementsByTag("tr")
												.get(0).getElementsByTag("td")
												.get(3).text()));
										detail.setHighestPrice(Double
												.parseDouble(trs.get(i)
														.getElementsByTag("tr")
														.get(0)
														.getElementsByTag("td")
														.get(2).text()));
										detail.setLowestPrice(Double
												.parseDouble(trs.get(i)
														.getElementsByTag("tr")
														.get(0)
														.getElementsByTag("td")
														.get(4).text()));
										detail.setVolume(Long.parseLong(trs.get(i)
												.getElementsByTag("tr").get(0)
												.getElementsByTag("td").get(5)
												.text()));
										detail.setTransactionMoney(Long
												.parseLong(trs.get(i)
														.getElementsByTag("tr")
														.get(0)
														.getElementsByTag("td")
														.get(6).text()));
										details.add(detail);
									}
								}
							}
						}
					}
				}
				info.setIfGetHistory(1);
//				StockTransactionDetailDao.AddStockTransactionDetail(details);
				details.forEach(x->StockTransactionDetailDao.AddStockTransactionDetail(x));
				StockInfoDao.UpdateCategory(info);
				info = GetUnSpiderData();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	private List<Date> GetHasHistoryDate(String code){
		List sq = StockTransactionDetailDao
				.QuerySql(String.format("select DISTINCT TransactionDate from stocktransactiondetail where code='%s' order by TransactionDate",code));
		Iterator it = sq.iterator();
		List<Date> dts=new LinkedList<Date>();
		while (it.hasNext()) {
			dts.add((Date) it.next());
		}
		return dts;
	}
	

}

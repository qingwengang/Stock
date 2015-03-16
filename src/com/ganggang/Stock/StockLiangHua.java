package com.ganggang.Stock;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.ganggang.Stock.Dao.StockTransactionDetailDao;
import com.ganggang.Stock.Entity.StockTransactionDetail;
import com.ganggang.Util.FormatUtil;

public class StockLiangHua {

	public static void main(String[] args) throws ParseException {
		System.out.println(GetBump("600156"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateString="2014-01-16";
		Date date = sdf.parse(dateString);
		System.out.println(GetBumpTimeByPercent(5, 5, date, "600156"));
		System.out.println(GetBumpPriceByTime(date, "600156", 100));
	}
	
	public static List<StockTransactionDetail> GetBump(String code){
		String sql=String.format("select * from stocktransactiondetail where Code='%s' order by VolumeRate desc limit 0,10", code);
		List<StockTransactionDetail> result=StockTransactionDetailDao.Query(sql);
		return result;
	}
	
	public static List<StockTransactionDetail> GetBumpTimeByPercent(double low,double high,Date focusDate,String code){
		List<StockTransactionDetail> result=new LinkedList<StockTransactionDetail>();
		String sql=String.format("select * from stocktransactiondetail where TransactionDate='%s' and code='%s'", (new SimpleDateFormat("yyyy-MM-dd")).format(focusDate).toString(),code);
		StockTransactionDetail detail=StockTransactionDetailDao.QueryUnique(sql);
		double lowPrice=detail.getEndPrice()*(1-0.01*low);
		double highPrice=detail.getEndPrice()*(1+0.01*high);
		String getLowDate=String.format("select * from stocktransactiondetail where code='%s' and TransactionDate>'%s' and LowestPrice<%s order by TransactionDate limit 0,1",code,(new SimpleDateFormat("yyyy-MM-dd")).format(focusDate).toString(),lowPrice);
		StockTransactionDetail lowDetail=StockTransactionDetailDao.QueryUnique(getLowDate);
		if(lowDetail!=null){
			result.add(lowDetail);
			String getHighDate=String.format("select * from stocktransactiondetail where code='%s' and TransactionDate>'%s' and HighestPrice>%s order by TransactionDate limit 0,1",code,lowDetail.getTransactionDate().toString(),highPrice);
			StockTransactionDetail highDetail=StockTransactionDetailDao.QueryUnique(getHighDate);
			if(highDetail!=null){
				result.add(highDetail);
			}
		}
		return result;
	}
	
	public static List<StockTransactionDetail> GetBumpPriceByTime(Date focusDate,String code,int days){
		List<StockTransactionDetail> result=new LinkedList<StockTransactionDetail>();
		String sql=String.format("select * from stocktransactiondetail where TransactionDate>'%s' and code='%s' order by TransactionDate limit 0,%s", FormatUtil.GetStringByDate(focusDate),code,days);
		List<StockTransactionDetail> details=StockTransactionDetailDao.Query(sql);
		int begin=0,end=0;double price=0;
		for(int i=0;i<details.size();i++){
			for(int j=i+1;j<details.size();j++){
				if(details.get(j).getHighestPrice()-details.get(i).getLowestPrice()>price){
					begin=i;end=j;price=details.get(j).getHighestPrice()-details.get(i).getLowestPrice();
				}
			}
		}
		result.add(details.get(begin));
		result.add(details.get(end));
		return result;
	}

}

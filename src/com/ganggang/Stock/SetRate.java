package com.ganggang.Stock;

import java.text.DecimalFormat;
import java.util.List;

import com.ganggang.Stock.Dao.StockInfoDao;
import com.ganggang.Stock.Dao.StockTransactionDetailDao;
import com.ganggang.Stock.Entity.StockInfo;
import com.ganggang.Stock.Entity.StockTransactionDetail;

public class SetRate implements Runnable {

	private int rateType;
	public void setRateType(int rateType){
		this.rateType=rateType;
	}

	private synchronized StockInfo GetStocks() {
		if (rateType == 1) {
			String sql = "select * from stockinfo where IfSetVolumeRate is null or IfSetVolumeRate=0 limit 0,1";
			StockInfo stock=StockInfoDao.QueryUnique(sql);
			if(stock!=null){
				stock.setIfSetVolumeRate(2);
				StockInfoDao.Update(stock);
				return stock;
			}
		}
		return null;
	}

	@Override
	public void run() {
		StockInfo stock = GetStocks();
		while(stock!=null){
			String sqlGetAll = String
					.format("select *  from stocktransactiondetail where code='%s' order by TransactionDate",
							stock.getCode());
			List<StockTransactionDetail> details = StockTransactionDetailDao
					.Query(sqlGetAll);
			for (int i = 0; i < details.size(); i++) {
				if (i <5) {
					details.get(i).setVolumeRate((double)0);
				} else {
					DecimalFormat df = new DecimalFormat("0.0000");
//					long avergVolume=(details.get(i-1).getVolume()+details.get(i-2).getVolume()+details.get(i-3).getVolume()+details.get(i-4).getVolume()+details.get(i-5).getVolume())/5;
					long avergVolume=details.get(i-1).getVolume();
					double rate = Double.parseDouble(df
							.format((float) details.get(i).getVolume()
									/ avergVolume));
					details.get(i).setVolumeRate(rate);
				}
			}
			StockTransactionDetailDao.UpdateCategory(details);
			stock.setIfSetVolumeRate(1);
			StockInfoDao.Update(stock);
			System.out.println(stock.getCode());
			stock=GetStocks();
		}
		System.out.println("end");
	}

}

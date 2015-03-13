package com.ganggang.Stock;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.SQLQuery;

import com.ganggang.Stock.Dao.PlanDealDao;
import com.ganggang.Stock.Dao.StockTransactionDetailDao;
import com.ganggang.Stock.Entity.*;

public class StockAnaly {

	public static void main(String[] args) {
//		SetVolumeRate();
//		List sq=StockTransactionDetailDao.QuerySql("select DISTINCT TransactionDate from stocktransactiondetail where TransactionDate>'2014-01-01' order by TransactionDate");
//		sq.forEach((x)->System.out.println((Date)x));
//		System.out.println(sq);
//		Moni();
		PlanDeal de=new PlanDeal();
		de.setBuyDate(new Date());
		de.setClosePrice(12.11);
		PlanDealDao.AddStockInfo(de);
	}

	public static void SetVolumeRate() {
		String sqlGetOne = "select  *  from stocktransactiondetail where VolumeRate=-1 limit 0,1";
		StockTransactionDetail detail = StockTransactionDetailDao
				.QueryUnique(sqlGetOne);
		while (detail != null) {
			if (detail != null) {
				String sqlGetAll = String
						.format("select *  from stocktransactiondetail where code='%s' and VolumeRate=-1 order by TransactionDate",
								detail.getCode());
				List<StockTransactionDetail> details = StockTransactionDetailDao
						.Query(sqlGetAll);
				for (int i = 0; i < details.size(); i++) {
					if (i == 0) {
						details.get(i).setVolumeRate(0);
					} else {
						DecimalFormat df = new DecimalFormat("0.0000");
						double rate = Double.parseDouble(df
								.format((float) details.get(i - 1).getVolume()
										/ details.get(i).getVolume()));
						details.get(i).setVolumeRate(rate);
					}
					StockTransactionDetailDao.UpdateCategory(details.get(i));
				}
			}
			detail = StockTransactionDetailDao.QueryUnique(sqlGetOne);
			System.out.println("1");
		}
		System.out.println("end");
	}

	public static void Moni(){
		List sq=StockTransactionDetailDao.QuerySql("select DISTINCT TransactionDate from stocktransactiondetail where TransactionDate>'2014-01-01' order by TransactionDate");
		Iterator it=sq.iterator();
		while(it.hasNext()){
			String transactionDate=((Date)it.next()).toString();
			StockTransactionDetail detail = StockTransactionDetailDao
					.QueryUnique(String.format("select * from stocktransactiondetail where TransactionDate='%s' order by VolumeRate desc LIMIT 0,1",transactionDate));
			if(detail!=null){
				PlanDeal deal=new PlanDeal();
				deal.setTransactionDate(detail.getTransactionDate());
				deal.setCode(detail.getCode());
				deal.setVolumeRate(detail.getVolumeRate());
				deal.setClosePrice(detail.getEndPrice());
				PlanDealDao.AddStockInfo(deal);
			}
		}
		
	}
}

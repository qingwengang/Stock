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
//		Moni();
//		SetBuyDate();
//		SetSellDate();
		AanalyZhuang("600628","2014-01-01","2015-02-02",2,3);
		SetLowPercent();
		SetLastClosePrice();
	}
	
	public static void AanalyZhuang(String code,String begin,String end,int days,double times){
		System.out.println("AanalyZhuang start");
		String sql="select * from stocktransactiondetail where code='%s' and TransactionDate>='%s' and TransactionDate<='%s' order by TransactionDate";
		List<StockTransactionDetail> details=StockTransactionDetailDao.Query(String.format(sql, code,begin,end));
		for(int i=20;i<details.size();i++){
			Boolean flag=true;
			for(int j=0;j<days;j++){
				for(int k=i-1;k>=i-5;k--){
					if(details.get(i+j).getVolume()/details.get(k).getVolume()<times){
						flag=false;
						break;
					}
				}
				if(!flag)break;
			}
			if(!flag) continue;
			System.out.println("code:"+code+",date:"+details.get(i).getTransactionDate());
		}
		System.out.println("AanalyZhuang end");
	}

	public static void Moni() {
		System.out.println("moni start!");
		List sq = StockTransactionDetailDao
				.QuerySql("select DISTINCT TransactionDate from stocktransactiondetail where TransactionDate>'2014-01-01' and TransactionDate<'2015-01-01' order by TransactionDate");
		Iterator it = sq.iterator();
		while (it.hasNext()) {
			String transactionDate = ((Date) it.next()).toString();
			List<StockTransactionDetail> details = StockTransactionDetailDao
					.Query(String
							.format("select * from stocktransactiondetail where TransactionDate='%s' and EndPrice>BeginPrice order by VolumeRate desc LIMIT 0,100",
									transactionDate));
			if (details != null) {
				int i=1;
				for (StockTransactionDetail detail : details) {
					if(!detail.getCode().startsWith("3")){
						String sqlSecond=String.format("select *  from stocktransactiondetail where Code='%s' and TransactionDate<'%s' order by TransactionDate desc limit 0,2", detail.getCode(),detail.getTransactionDate().toString());
						List<StockTransactionDetail> secondDetails=StockTransactionDetailDao.Query(sqlSecond);
						if(secondDetails.get(0).getEndPrice()<detail.getEndPrice()&&secondDetails.get(1).getEndPrice()>secondDetails.get(0).getEndPrice()){
							PlanDeal deal = new PlanDeal();
							deal.setTransactionDate(detail.getTransactionDate());
							deal.setCode(detail.getCode());
							deal.setVolumeRate(detail.getVolumeRate());
							deal.setClosePrice(detail.getEndPrice());
							deal.setExceptBuyPrice(detail.getLowestPrice());
							deal.setExceptSellPrice(detail.getHighestPrice());
							PlanDealDao.AddStockInfo(deal);
							i--;
							if(i<1){
								break;
							}
						}
					}
				}
			}
		}
		System.out.println("moni end!");
	}

	public static void SetBuyDate() {
		System.out.println("buy start!");
		List<PlanDeal> deals = PlanDealDao
				.Query("select *  from plandeal where BuyPrice=0  order by transactiondate");
		for (PlanDeal deal : deals) {
			double buyPrice = deal.getExceptBuyPrice();
			StockTransactionDetail dealBuy = StockTransactionDetailDao
					.QueryUnique(String
							.format("select * from stocktransactiondetail where code='%s' and TransactionDate>'%s' and LowestPrice<%s order by TransactionDate limit 0,1",
									deal.getCode(), deal.getTransactionDate(),
									buyPrice));
			if (dealBuy != null) {
				deal.setBuyDate(dealBuy.getTransactionDate());
				deal.setBuyPrice(buyPrice);
				PlanDealDao.Update(deal);
			}
		}
		System.out.println("buy end!");
	}

	public static void SetSellDate() {
		System.out.println("sell start!");
		List<PlanDeal> deals = PlanDealDao
				.Query("select *  from plandeal where buyPrice!=0 and SellPrice=0  order by transactiondate");
		for (PlanDeal deal : deals) {
			double sellPrice = deal.getExceptSellPrice();
			StockTransactionDetail dealBuy = StockTransactionDetailDao
					.QueryUnique(String
							.format("select * from stocktransactiondetail where code='%s' and TransactionDate>'%s' and HighestPrice>%s order by TransactionDate limit 0,1",
									deal.getCode(), deal.getBuyDate(),
									sellPrice));
			if (dealBuy != null) {
				deal.setSellDate(dealBuy.getTransactionDate());
				deal.setSellPrice(sellPrice);
				PlanDealDao.Update(deal);
			}
		}
		System.out.println("sell end!");
	}
	public static void SetLowPercent(){
		System.out.println("setlowpercent start!");
		List<PlanDeal> deals = PlanDealDao
				.Query("select *  from plandeal where buyPrice!=0 and SellPrice!=0  order by transactiondate");
		for (PlanDeal deal : deals) {
			List l=StockTransactionDetailDao.QuerySql(String.format("select min(LowestPrice) from stocktransactiondetail where CODE='%s' and TransactionDate>='%s' and TransactionDate<='%s'", deal.getCode(),deal.getBuyDate(),deal.getSellDate()));
			double lowestPrice=(double)l.get(0);
			deal.setLowPercent(lowestPrice/deal.getClosePrice());
			deal.setHandDays(0);
			PlanDealDao.Update(deal);
		}
		System.out.println("setlowpercent end!");
	}
	public static void SetLastClosePrice(){
		System.out.println("SetLastClosePrice start!");
		List<PlanDeal> deals = PlanDealDao
				.Query("select *  from plandeal  order by transactiondate");
		for (PlanDeal deal : deals) {
			StockTransactionDetail detail=StockTransactionDetailDao.QueryUnique(String.format("select * FROM stocktransactiondetail where code='%s'  order by TransactionDate desc limit 0,1",deal.getCode()));
			deal.setLastClosePrice(detail.getEndPrice());
			PlanDealDao.Update(deal);
		}
		System.out.println("SetLastClosePrice end!");
	}
}

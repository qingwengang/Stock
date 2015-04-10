package com.ganggang.Stock;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.SQLQuery;

import com.ganggang.Stock.Dao.PlanDealDao;
import com.ganggang.Stock.Dao.StockInfoDao;
import com.ganggang.Stock.Dao.StockTransactionDetailDao;
import com.ganggang.Stock.Entity.*;
import com.ganggang.Util.FormatUtil;

public class StockAnaly {

	public static void main(String[] args) {
//		Moni(0.92,1.05,2);
		Moni(0.90,1.05,3);
		SetBuyDate();
		SetSellDate();
		SetLastClosePrice();
		
//		AanalyZhuang("600628","2014-01-01","2015-02-02",1,5,5);
//		SetLowPercent();
		
	}
	
	public static void AanalyZhuang(String code,String begin,String end,int days,int beforeDays,double times){
		System.out.println("AanalyZhuang start");
		String sqlGetAll="select * from stockinfo";
		List<StockInfo> stocks=StockInfoDao.Query(sqlGetAll);
		for (StockInfo stockInfo : stocks) {
			code=stockInfo.getCode();
			String sql="select * from stocktransactiondetail where code='%s' and TransactionDate>='%s' and TransactionDate<='%s' order by TransactionDate";
			List<StockTransactionDetail> details=StockTransactionDetailDao.Query(String.format(sql, code,begin,end));
			if(details.size()>20){
				for(int i=beforeDays;i<details.size();i++){
					Boolean flag=true;
					for(int j=0;j<days&&j+i<details.size();j++){
						for(int k=i-1;k>=i-beforeDays;k--){
							if(details.get(i+j).getVolume()/details.get(k).getVolume()<times){
								flag=false;
								break;
							}
						}
						if(!flag)break;
					}
					if(!flag) continue;
					System.out.println("code:"+code+",date:"+details.get(i).getTransactionDate());
					PlanDeal deal = new PlanDeal();
					deal.setTransactionDate(details.get(i).getTransactionDate());
					deal.setCode(details.get(i).getCode());
					deal.setVolumeRate(details.get(i).getVolumeRate());
					deal.setClosePrice(details.get(i).getEndPrice());
					deal.setExceptBuyPrice(details.get(i).getEndPrice()*0.9);
					deal.setExceptSellPrice(details.get(i).getEndPrice());
					PlanDealDao.AddStockInfo(deal);
				}
			}
		}
		System.out.println("AanalyZhuang end");
	}
	
	public static void Moni(double buyRate,double sellRate,int planType){
		System.out.println("moni start!");
		List sq = StockTransactionDetailDao
				.QuerySql("select DISTINCT TransactionDate from stocktransactiondetail where TransactionDate>'2014-01-01'  order by TransactionDate");
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
						if(secondDetails.size()>=2 && secondDetails.get(0).getEndPrice()<detail.getEndPrice()&&secondDetails.get(1).getEndPrice()>secondDetails.get(0).getEndPrice()){
							PlanDeal deal = new PlanDeal();
							deal.setTransactionDate(detail.getTransactionDate());
							deal.setCode(detail.getCode());
							deal.setVolumeRate(detail.getVolumeRate());
							deal.setClosePrice(detail.getEndPrice());
							deal.setExceptBuyPrice(detail.getEndPrice()*buyRate);
							deal.setExceptSellPrice(detail.getEndPrice()*sellRate);
							deal.setSymbol(GetSymbolByCode(detail.getCode()));
							deal.setPlantype(planType);
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
	public static void Moni() {
		System.out.println("moni start!");
		List sq = StockTransactionDetailDao
				.QuerySql("select DISTINCT TransactionDate from stocktransactiondetail where TransactionDate>'2014-01-01'  order by TransactionDate");
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
						if(secondDetails.size()>=2 && secondDetails.get(0).getEndPrice()<detail.getEndPrice()&&secondDetails.get(1).getEndPrice()>secondDetails.get(0).getEndPrice()){
							PlanDeal deal = new PlanDeal();
							deal.setTransactionDate(detail.getTransactionDate());
							deal.setCode(detail.getCode());
							deal.setVolumeRate(detail.getVolumeRate());
							deal.setClosePrice(detail.getEndPrice());
							deal.setExceptBuyPrice(detail.getEndPrice()*0.95);
							deal.setExceptSellPrice(detail.getEndPrice()*1.05);
							deal.setSymbol(GetSymbolByCode(detail.getCode()));
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
				if(FormatUtil.AddDate(deal.getTransactionDate(), 15).after(dealBuy.getTransactionDate())){
					deal.setBuyDate(dealBuy.getTransactionDate());
					deal.setBuyPrice(buyPrice);
					PlanDealDao.Update(deal);
				}
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
	public static String GetSymbolByCode(String code){
		String symbol="";
		String sql=String.format("select * from stockinfo where code='%s'", code);
		StockInfo stock=StockInfoDao.QueryUnique(sql);
		if(stock!=null){
			symbol=stock.getSymbol();
		}
		return symbol;
	}
	
}

package com.ganggang.Stock.Monitor;

import java.util.List;

import com.ganggang.Stock.Dao.PlanDealDao;
import com.ganggang.Stock.Entity.PlanDeal;

public class StockMonitor {

	public static void main(String[] args){
		Monitor();
	}
	public static void Monitor(){
		List<PlanDeal> deals=PlanDealDao.Query("select * from plandeal where BuyPrice=0 or SellPrice=0");
		for (PlanDeal planDeal : deals) {
			String content=SinaProxy.requestStock(planDeal.getSymbol());
			String[] prices=content.substring(content.indexOf('"')+1, content.lastIndexOf('"')).split(",");
//			System.out.println(prices.length);
			if(planDeal.getBuyPrice()<=0 &&Double.parseDouble(prices[3])>0 && Double.parseDouble(prices[3])<=planDeal.getExceptBuyPrice()){
				SendNotifier(planDeal.getCode()+"可能已经买入成功！");
			}
		}
//		StringBuilder sbCode=new StringBuilder();
//		deals.forEach(x->sbCode.append(x.getSymbol()+","));
//		String content=SinaProxy.requestStock(sbCode.toString());
//		String[] contents=content.split(";");
//		for (String con : contents) {
//			if(con.length()>10){
//				System.out.println(con.length());
//				String code=con.substring(con.indexOf('=')-6, con.indexOf('='));
//				System.out.println(con.substring(con.indexOf('=')-6, con.indexOf('=')));
//				System.out.println(con.substring(con.indexOf('"')+1, con.lastIndexOf('"')));
//				String[] prices=con.substring(con.indexOf('"')+1, con.lastIndexOf('"')).split(",");
//			}
//		}
	}
	private static void SendNotifier(String message){
		System.out.println(message+"-------------------");
	}

}

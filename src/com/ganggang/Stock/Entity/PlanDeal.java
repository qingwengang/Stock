package com.ganggang.Stock.Entity;

import java.util.Date;

import javax.persistence.Entity;
@Entity
public class PlanDeal {
	private int Id;
	private Date TransactionDate;
	private String Code;
	private double VolumeRate;
	private double ClosePrice;
	private Date BuyDate;
	private double BuyPrice;
	private Date SellDate;
	private double SellPrice;
	private double LowPercent;
	private int HandDays;
	private double LastClosePrice;
	private double ExceptBuyPrice;
	private double ExceptSellPrice;
	private int Plantype;
	private String Symbol;
	@javax.persistence.Id
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public Date getTransactionDate() {
		return TransactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		TransactionDate = transactionDate;
	}
	public String getCode() {
		return Code;
	}
	public void setCode(String code) {
		Code = code;
	}
	public double getVolumeRate() {
		return VolumeRate;
	}
	public void setVolumeRate(double volumeRate) {
		VolumeRate = volumeRate;
	}
	public double getClosePrice() {
		return ClosePrice;
	}
	public void setClosePrice(double closePrice) {
		ClosePrice = closePrice;
	}
	public Date getBuyDate() {
		return BuyDate;
	}
	public void setBuyDate(Date buyDate) {
		BuyDate = buyDate;
	}
	public double getBuyPrice() {
		return BuyPrice;
	}
	public void setBuyPrice(double buyPrice) {
		BuyPrice = buyPrice;
	}
	public Date getSellDate() {
		return SellDate;
	}
	public void setSellDate(Date sellDate) {
		SellDate = sellDate;
	}
	public double getSellPrice() {
		return SellPrice;
	}
	public void setSellPrice(double sellPrice) {
		SellPrice = sellPrice;
	}
	public double getLowPercent() {
		return LowPercent;
	}
	public void setLowPercent(double lowPercent) {
		LowPercent = lowPercent;
	}
	public int getHandDays() {
		return HandDays;
	}
	public void setHandDays(int handDays) {
		HandDays = handDays;
	}
	public double getLastClosePrice() {
		return LastClosePrice;
	}
	public void setLastClosePrice(double lastClosePrice) {
		LastClosePrice = lastClosePrice;
	}
	public double getExceptBuyPrice() {
		return ExceptBuyPrice;
	}
	public void setExceptBuyPrice(double exceptBuyPrice) {
		ExceptBuyPrice = exceptBuyPrice;
	}
	public double getExceptSellPrice() {
		return ExceptSellPrice;
	}
	public void setExceptSellPrice(double exceptSellPrice) {
		ExceptSellPrice = exceptSellPrice;
	}
	public int getPlantype() {
		return Plantype;
	}
	public void setPlantype(int plantype) {
		Plantype = plantype;
	}
	public String getSymbol() {
		return Symbol;
	}
	public void setSymbol(String symbol) {
		Symbol = symbol;
	}
	
}

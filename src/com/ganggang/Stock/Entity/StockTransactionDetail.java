package com.ganggang.Stock.Entity;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class StockTransactionDetail {
	public StockTransactionDetail() {
	}
	private int Id;
	private String Code;
	private Date TransactionDate;
	private double BeginPrice;
	private double EndPrice;
	private double HighestPrice;
	private double LowestPrice;
	private long Volume;
	private long TransactionMoney;
	private Double VolumeRate;
	@javax.persistence.Id
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getCode() {
		return Code;
	}
	public void setCode(String code) {
		Code = code;
	}
	public Date getTransactionDate() {
		return TransactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		TransactionDate = transactionDate;
	}
	public double getBeginPrice() {
		return BeginPrice;
	}
	public void setBeginPrice(double beginPrice) {
		BeginPrice = beginPrice;
	}
	public double getEndPrice() {
		return EndPrice;
	}
	public void setEndPrice(double endPrice) {
		EndPrice = endPrice;
	}
	public double getHighestPrice() {
		return HighestPrice;
	}
	public void setHighestPrice(double highestPrice) {
		HighestPrice = highestPrice;
	}
	public double getLowestPrice() {
		return LowestPrice;
	}
	public void setLowestPrice(double lowestPrice) {
		LowestPrice = lowestPrice;
	}
	public long getVolume() {
		return Volume;
	}
	public void setVolume(long volume) {
		Volume = volume;
	}
	public long getTransactionMoney() {
		return TransactionMoney;
	}
	public void setTransactionMoney(long transactionMoney) {
		TransactionMoney = transactionMoney;
	}
	public Double getVolumeRate() {
		return VolumeRate;
	}
	public void setVolumeRate(Double volumeRate) {
		VolumeRate = volumeRate;
	}
}

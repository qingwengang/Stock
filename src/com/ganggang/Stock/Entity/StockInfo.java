package com.ganggang.Stock.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class StockInfo {

	public StockInfo() {
	}
	private int Id;
	private String Code;
	private String Name;
	private double NewPrice;
	private long Volume;
	private int IfGetHistory;
	private Integer IfSetVolumeRate;
	private String Symbol;
	@javax.persistence.Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
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
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public double getNewPrice() {
		return NewPrice;
	}
	public void setNewPrice(double newPrice) {
		NewPrice = newPrice;
	}
	public long getVolume() {
		return Volume;
	}
	public void setVolume(long volume) {
		Volume = volume;
	}
	public int getIfGetHistory() {
		return IfGetHistory;
	}
	public void setIfGetHistory(int ifGetHistory) {
		IfGetHistory = ifGetHistory;
	}
	public Integer getIfSetVolumeRate() {
		return IfSetVolumeRate;
	}
	public void setIfSetVolumeRate(Integer ifSetVolumeRate) {
		IfSetVolumeRate = ifSetVolumeRate;
	}
	public String getSymbol() {
		return Symbol;
	}
	public void setSymbol(String symbol) {
		Symbol = symbol;
	}
	
}

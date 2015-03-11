package com.ganggang.Stock;

import java.text.DecimalFormat;
import java.util.List;

import com.ganggang.Stock.Dao.StockTransactionDetailDao;
import com.ganggang.Stock.Entity.StockTransactionDetail;

public class StockAnaly {

	public static void main(String[] args) {
		SetVolumeRate();
		// DecimalFormat df = new DecimalFormat("0.0000");
		// double rate=Double.parseDouble(df.format((float)12/14));
		// System.out.println(rate);
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

}

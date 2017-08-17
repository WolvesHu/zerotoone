package com.wolves.zerotoone.orm.datasource.pooled;

import com.wolves.zerotoone.orm.datasource.unpooled.MyUnpooledDataSourceFactory;

public class MyPooledDataSourceFactory extends MyUnpooledDataSourceFactory {

	public MyPooledDataSourceFactory() {
		    this.dataSource = new MyPooledDataSource();
	}

}

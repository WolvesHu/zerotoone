package com.wolves.zerotoone.orm.datasource.pooled;

import java.util.ArrayList;
import java.util.List;

public class MyPoolState {
	protected MyPooledDataSource dataSource;

	protected final List<MyPooledConnection> idleConnections = new ArrayList<MyPooledConnection>();
	protected final List<MyPooledConnection> activeConnections = new ArrayList<MyPooledConnection>();
	protected long accumulatedRequestTime = 0;
	protected long requestCount = 0;
	protected long accumulatedCheckoutTime;
	protected long claimedOverdueConnectionCount = 0;
	protected long accumulatedCheckoutTimeOfOverdueConnections = 0;
	protected long badConnectionCount = 0;
	protected long hadToWaitCount = 0;
	protected long accumulatedWaitTime = 0;

	public MyPoolState(MyPooledDataSource myPooledDataSource) {

	}

}

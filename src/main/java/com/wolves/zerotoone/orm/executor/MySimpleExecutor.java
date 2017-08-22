package com.wolves.zerotoone.orm.executor;

import com.wolves.zerotoone.orm.mapping.MyConfiguration;
import com.wolves.zerotoone.orm.session.MyDefaultSqlSession;
import com.wolves.zerotoone.orm.transaction.MyTransaction;

public class MySimpleExecutor extends MyBaseExecutor {

	public MySimpleExecutor(MyConfiguration myConfiguration, MyTransaction transaction) {
	}

	@Override
	public MyDefaultSqlSession getTransaction() {
		return null;
	}

}

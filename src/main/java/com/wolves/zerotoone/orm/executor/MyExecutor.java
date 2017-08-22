package com.wolves.zerotoone.orm.executor;

import com.wolves.zerotoone.orm.session.MyDefaultSqlSession;

public interface MyExecutor {

	public MyDefaultSqlSession getTransaction();

}

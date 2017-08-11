package com.wolves.test.zerotoone.orm.session.factory;

import com.wolves.test.zerotoone.orm.session.Session;

public interface SessionFactory {
	<T> Session<T> openSession();
}

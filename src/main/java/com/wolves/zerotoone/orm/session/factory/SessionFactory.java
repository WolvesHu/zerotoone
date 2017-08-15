package com.wolves.zerotoone.orm.session.factory;

import com.wolves.zerotoone.orm.session.Session;

public interface SessionFactory {
	<T> Session<T> openSession();
}

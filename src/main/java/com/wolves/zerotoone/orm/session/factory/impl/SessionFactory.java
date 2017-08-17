package com.wolves.zerotoone.orm.session.factory.impl;

import com.wolves.zerotoone.orm.session.Session;

public interface SessionFactory {
	<T> Session<T> openSession();
}

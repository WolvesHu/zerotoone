package com.wolves.zerotoone.orm.session.factory.impl;

import com.wolves.zerotoone.orm.session.MySqlSession;

public interface SessionFactory {
	<T> MySqlSession openSession();
}

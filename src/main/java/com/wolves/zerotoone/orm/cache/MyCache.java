package com.wolves.zerotoone.orm.cache;

import java.util.concurrent.locks.ReadWriteLock;

public interface MyCache {
	String getId();

	void putObject(Object key, Object value);

	Object getObject(Object key);

	Object removeObject(Object key);

	void clear();

	int getSize();

	ReadWriteLock getReadWriteLock();
}

package com.wolves.zerotoone.orm.builder;

import com.wolves.zerotoone.orm.cache.MyCache;
import com.wolves.zerotoone.orm.mapping.MyConfiguration;

public class MyMapperBuilderAssistant {
	private String currentNamespace;
	private String resource;
	private MyCache currentCache;
	private boolean unresolvedCacheRef;

	public MyMapperBuilderAssistant(MyConfiguration configuration, String resource) {

	}

}

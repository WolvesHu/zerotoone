package com.wolves.zerotoone.orm.mapping;

import java.util.List;

import com.wolves.zerotoone.orm.builder.MyMapperBuilderAssistant;

public class MyResultMapResolver {
	private final MyMapperBuilderAssistant assistant;
	private String id;
	private Class<?> type;
	private String extend;
	private List<MyResultMapping> resultMappings;
	private Boolean autoMapping;

	public MyResultMapResolver(MyMapperBuilderAssistant assistant, String id, Class<?> type, Object object,
			Object object2, List<MyResultMapping> resultMappings, boolean autoMapping) {
		this.assistant = assistant;
		this.id = id;
		this.type = type;
		this.resultMappings = resultMappings;
		this.autoMapping = autoMapping;
	}

	public MyResultMap resolve() {
		return assistant.addResultMap(this.id, this.type, this.extend, null, this.resultMappings, this.autoMapping);
	}

}

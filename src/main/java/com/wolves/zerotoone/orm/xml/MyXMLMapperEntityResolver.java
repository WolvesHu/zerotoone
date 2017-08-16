package com.wolves.zerotoone.orm.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import org.apache.ibatis.io.Resources;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class MyXMLMapperEntityResolver implements EntityResolver {
	private static final String MYBATIS_CONFIG_SYSTEM = "mybatis-3-config.dtd";
	private static final String MYBATIS_MAPPER_SYSTEM = "mybatis-3-mapper.dtd";

	private static final String MYBATIS_CONFIG_DTD = "com/wolves/zerotoone/orm/xml/mybatis-3-config.dtd";
	
	private static final String MYBATIS_MAPPER_DTD = "com/wolves/zerotoone/orm/xml/mybatis-3-mapper.dtd";

	@Override
	public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
		try {
			if (systemId != null) {
				String lowerCaseSystemId = systemId.toLowerCase(Locale.ENGLISH);
				if (lowerCaseSystemId.contains(MYBATIS_CONFIG_SYSTEM)) {
					return getInputSource(MYBATIS_CONFIG_DTD, publicId, systemId);
				} else if (lowerCaseSystemId.contains(MYBATIS_MAPPER_SYSTEM)) {
					return getInputSource(MYBATIS_MAPPER_DTD, publicId, systemId);
				}
			}
			return null;
		} catch (Exception e) {
			throw new SAXException(e.toString());
		}
	}

	private InputSource getInputSource(String path, String publicId, String systemId) {
		InputSource source = null;
		if (path != null) {
			try {
				InputStream in = Resources.getResourceAsStream(path);
				source = new InputSource(in);
				source.setPublicId(publicId);
				source.setSystemId(systemId);
			} catch (IOException e) {
				// ignore, null is ok
			}
		}
		return source;
	}
}

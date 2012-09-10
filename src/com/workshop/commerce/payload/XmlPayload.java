package com.workshop.commerce.payload;

public class XmlPayload implements Payload {

	String xml;

	public XmlPayload(String xml) {
		super();
		this.xml = xml;
	}

	@Override
	public Type getType() {
		return Payload.Type.XML;
	}

	@Override
	public Object getPayload() {
		return xml;
	}

}

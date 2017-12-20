package net.cactusthorn.switches.xml;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.apache.commons.net.util.SubnetUtils;

public class SubnetAdapter extends XmlAdapter<String,SubnetUtils.SubnetInfo> {

	@Override
	public SubnetUtils.SubnetInfo unmarshal(String value) throws Exception {
		return new SubnetUtils(value).getInfo();
	}

	@Override
	public String marshal(SubnetUtils.SubnetInfo value) throws Exception {
		return value.getCidrSignature();
	}
}

package com.whir.plugins.sso.login;

import java.util.List;
import java.util.Map;

import com.whir.component.security.SecurityList;
import org.apache.commons.lang.StringUtils;
import com.thunisoft.summer.component.ssoclient.auth.Auth;
import com.thunisoft.summer.component.ssoclient.config.AbstractSSOconfig;
import com.whir.component.config.ConfigXMLReader;

public class SSOConfig extends AbstractSSOconfig {

	private String[] excludeUrlArr;

	@Override
	public Auth getAuth() {
		return new AuthSSOImpl();  
	}
  
	@Override
	public String getCasServerUrlPrefix() {
		String ssoUrl = new ConfigXMLReader().getAttribute("uimmanager", "ssoUrl");//单点登录的服务器地址。
		if (StringUtils.isBlank(ssoUrl)) {
			throw new IllegalArgumentException("单点登录主页地址为空,请检查config.properties配置");
		}
		return ssoUrl;
	}

	@Override
	protected String[] getExclueURL() {
		ConfigXMLReader reader = new ConfigXMLReader();
		//读取单点不拦截得地址
		List urlList = reader.getElePropertyMap("exclueURLlist", "exclueURL");
		String excludes = "";
		if (urlList != null) {
			for (int i = 0; i < urlList.size(); ++i) {
				Map map = (Map) urlList.get(i);
				if(i==0){
					excludes += map.get("url") ;  
				}else{
					excludes += "," + map.get("url") ;
				}
				
			}
		}
		if (null == excludeUrlArr) {
			if (StringUtils.isNotBlank(excludes)) {
				excludeUrlArr = excludes.split(",");
			} else {
				excludeUrlArr = new String[0];
			}
		}
		return excludeUrlArr;
	}
}

package com.farm.parameter;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.farm.core.ParameterService;
import com.farm.parameter.service.DictionaryEntityServiceInter;
import com.farm.parameter.service.ParameterServiceInter;
import com.farm.parameter.service.impl.ConstantVarService;
import com.farm.parameter.service.impl.PropertiesFileService;
import com.farm.util.spring.BeanFactory;
//import com.farm.wcp.know.service.impl.KnowServiceImpl;

/**
 * 框架系统参数服务
 * 
 * @author Administrator
 * 
 */
public class FarmParameterService implements ParameterService {
	private static ParameterServiceInter parametersLocal;
	private static DictionaryEntityServiceInter dictionaryentitysLocal;
	private static ParameterService localstatic;
	private static final Logger log = Logger.getLogger(FarmParameterService.class);  // remyxo, 2017-12-12
	/**
	 * @return
	 */
	public static ParameterService getInstance() {
		if (localstatic == null) {
			localstatic = new FarmParameterService();
		}
		return localstatic;
	}

	private ParameterServiceInter getParameterService() {
		if (parametersLocal == null) {
			parametersLocal = (ParameterServiceInter) BeanFactory
					.getBean("parameterServiceImpl");
		}
		return parametersLocal;
	}

	private DictionaryEntityServiceInter getDictionaryEntityService() {
		if (dictionaryentitysLocal == null) {
			dictionaryentitysLocal = (DictionaryEntityServiceInter) BeanFactory
					.getBean("dictionaryEntityServiceImpl");
		}
		return dictionaryentitysLocal;
	}

	public void setPropertiesFiles(List<String> propertiesFiles) {
		for (String name : propertiesFiles) {
			if (PropertiesFileService.registConstant(name)) {
				System.out
						.println("注册配置文件:"
								+ name
								+ ".properties(com.farm.parameter.FarmParameterService)");
			}
		}
	}

	

	@Override
	public Map<String, String> getDictionary(String key) {
		return getDictionaryEntityService().getDictionary(key);
	}

	@Override
	public List<Entry<String, String>> getDictionaryList(String key) {
		return getDictionaryEntityService().getDictionaryList(key);
	}

	@Override
	public String getParameter(String key) {
		key = key.trim();
		// 先找用户参数和系统参数
		String value = getParameterService().getValue(key);
		if (value != null) {
			return value;
		}
		// 再找properties文件参数
		value = PropertiesFileService.getValue(key);
		if (value != null) {
			return value;
		}
		// 找常量
		value = ConstantVarService.getValue(key);
		if (value != null) {
			return value;
		}
		throw new RuntimeException("无法获得参数:" + key);
	}
	
	@Override
	public String getParameter(String key, String userId) {
		String value = getParameterService().getValue(key, userId);
		if (value != null) {
			return value;
		}
		// 再找properties文件参数
		value = PropertiesFileService.getValue(key);
		if (value != null) {
			return value;
		}
		// 找常量
		value = ConstantVarService.getValue(key);
		if (value != null) {
			return value;
		}
		throw new RuntimeException("无法获得参数:" + key);
	}
	
	// remyxo, 2017-11-11, 获得整形参数，若未配置或转换错误，返回默认值。
	@Override
	public int getIntParameter(String key, int def_val) 
	{	
		String value;
		int val;
		
		key = key.trim();
		// 先找用户参数和系统参数
		value = getParameterService().getValue(key);
		if (value == null) {
			// 再找properties文件参数
			value = PropertiesFileService.getValue(key);
			if (value == null) {
				// 找常量
				value = ConstantVarService.getValue(key);
			}
		}
		if (value != null) {
			try {
				val = Integer.valueOf(value);
			}catch(Exception e) {
				log.warn("***** FarmParameterService.getIntParameter: parameter \"" + key + "\" is not a integer, use default value " + def_val);
				val = def_val;
			}
			return val;
		}
		//throw new RuntimeException("无法获得参数:" + key);
		log.warn("***** FarmParameterService.getIntParameter: parameter \"" + key + "\" not configured, use default value " + def_val);
		return def_val;
	}
	// end, 2017-11-11
}

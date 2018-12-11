package com.farm.parameter.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.farm.parameter.controller.ParameterController;

@Service
public class PropertiesFileService {
	private static Set<ResourceBundle> constant = new HashSet<ResourceBundle>();
	private static final Logger log = Logger
			.getLogger(ParameterController.class);
	/**
	 * 注册配置文件
	 * 
	 * @param fileName
	 *            "config"=config.properties
	 */
	public static boolean registConstant(String fileName) {
		log.info("注册配置文件"+fileName+".properties");
		return constant.add(ResourceBundle.getBundle(fileName));
	}

	public static List<Entry<String, String>> getEntrys() {
		List<Entry<String, String>> list = new ArrayList<Entry<String, String>>();
		for (ResourceBundle node : constant) {
			Enumeration<String> enums = node.getKeys();
			while (enums.hasMoreElements()) {
				String key = enums.nextElement();
				Entry<String, String> entry = new SimpleEntry<String, String>(
						key, node.getString(key));
				list.add(entry);
			}
			
			// remyxo, 2017-12-13, sort according to key
			Collections.sort(list,new Comparator<Entry<String, String>>(){
	            public int compare(Entry<String, String> arg0, Entry<String, String> arg1) {
	                return arg0.getKey().compareTo(arg1.getKey());
	            }
	        });
			// end, 2017-12-13
			
		}
		return list;
	}

	public static String getValue(String key) {
		for (ResourceBundle node : constant) {
			String value = getString(key, node);
			if (value != null) {
				return value;
			}
		}
		return null;
	}

	private static String getString(String key, ResourceBundle bundle) {
		try {
			String messager = bundle.getString(key);
			return messager;
		} catch (MissingResourceException e) {
			return null;
		}
	}
}

package com.farm.parameter.service.impl;

import java.util.ArrayList;
import java.util.Collections;   // remyxo, 2017-12-13
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.farm.core.config.ReadKey;
@Service
public class ConstantVarService {
	private static Map<String, String> constant = new HashMap<String, String>();

	public static void registConstant(String key, String value) {
		constant.put(key, value);
		if(getValue("farm.constant.webroot.path")!=null){
			ReadKey.read(getValue("farm.constant.webroot.path"));
		}
	}

	public static List<Entry<String, String>> getEntrys() {
		List<Entry<String, String>> list = new ArrayList<Entry<String, String>>();
		for (Entry<String, String> node : constant.entrySet()) {
			list.add(node);
		}
		
		// remyxo, 2017-12-13, sort according to key
		Collections.sort(list,new Comparator<Entry<String, String>>(){
            public int compare(Entry<String, String> arg0, Entry<String, String> arg1) {
                return arg0.getKey().compareTo(arg1.getKey());
            }
        });
		// end, 2017-12-13
		
		return list;
	}
	public static String getValue(String key){
		return constant.get(key);
	}

}

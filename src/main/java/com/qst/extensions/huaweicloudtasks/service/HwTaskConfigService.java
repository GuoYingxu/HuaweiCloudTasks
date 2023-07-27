package com.qst.extensions.huaweicloudtasks.service;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.components.Service;

@Service
public final class HwTaskConfigService {
    private final PropertiesComponent propertiesComponent = PropertiesComponent.getInstance();

    public void save(String key,String value) {
        propertiesComponent.setValue(key,value);
    }
    public void save(String key,boolean value) {
        propertiesComponent.setValue(key,value);
    }
    public void save(String key,int value) {
        propertiesComponent.setValue(key, String.valueOf(value));
    }
    public String get(String key,String defaultValue) {
        return propertiesComponent.getValue(key,defaultValue);
    }
    public boolean getBoolean(String key,boolean defaultValue) {
        return propertiesComponent.getBoolean(key,defaultValue);
    }
    public int getInt(String key,int defaultValue){
        return propertiesComponent.getInt(key,defaultValue);
    }

    public  void clear(String key) {
        propertiesComponent.unsetValue(key);
    }

}

package com.footprint.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class Props extends Properties {
		private static final long serialVersionUID = 0L;
		
		//----------------------------------------------------------------------- 私有属性 start
		/** 属性文件的URL */
		private URL propertiesFileUrl;
		//----------------------------------------------------------------------- 私有属性 end
		
		/**
		 * 构造，相对于classes读取文件
		 * @param path 相对路径
		 * @param clazz 基准类
		 */
		public Props(String path){
			final URL url = this.getClass().getResource(path);
			if(url == null) {
				throw new RuntimeException("Can not find Setting file: " + path);
			}
			this.load(url);
		}
		
		/**
		 * 初始化配置文件
		 * @param propertiesFileUrl 配置文件URL
		 */
		public  void load(URL propertiesFileUrl){
			if(propertiesFileUrl == null){
				throw new RuntimeException("Null properties file url define.");
			}
			InputStream in = null;
			try {
				in = propertiesFileUrl.openStream();
				super.load(in);
			} catch (IOException e) {
				throw new RuntimeException("Load properties file exception.", e);
			}finally{
				if(in != null) {
					try {
						in.close();
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
			}
			this.propertiesFileUrl = propertiesFileUrl;
		}
		
		/**
		 * 重新加载配置文件
		 */
		public void reload() {
			this.load(propertiesFileUrl);
		}
		
		//----------------------------------------------------------------------- Get start
		/**
		 * 获取字符型型属性值
		 * @param key 属性名
		 * @param defaultValue 默认值
		 * @return 属性值
		 */
		public String getString(String key, String defaultValue){
			return super.getProperty(key, defaultValue);
		}
		
		/**
		 * 获取字符型型属性值
		 * @param key 属性名
		 * @return 属性值
		 */
		public String getString(String key){
			String val = super.getProperty(key);
			if(val != null) {
				val = val.trim();
			}
			return val;
		}
		
		/**
		 * 获取数字型型属性值
		 * @param key 属性名
		 * @param defaultValue 默认值
		 * @return 属性值
		 */
		public Integer getInt(String key, Integer defaultValue){
			return ConvertUtils.toInt(this.getString(key), defaultValue);
		}
		
		/**
		 * 获取数字型型属性值
		 * @param key 属性名
		 * @return 属性值
		 */
		public Integer getInt(String key){
			return getInt(key, null);
		}
		
		/**
		 * 获取波尔型属性值
		 * @param key 属性名
		 * @param defaultValue 默认值
		 * @return 属性值
		 */
		public Boolean getBoolean(String key, Boolean defaultValue){
			return ConvertUtils.toBoolean(this.getString(key), defaultValue);
		}
		
		/**
		 * 获取波尔型属性值
		 * @param key 属性名
		 * @return 属性值
		 */
		public Boolean getBoolean(String key){
			return getBoolean(key, null);
		}
		
		/**
		 * 获取long类型属性值
		 * @param key 属性名
		 * @param defaultValue 默认值
		 * @return 属性值
		 */
		public Long getLong(String key, Long defaultValue){
			return ConvertUtils.toLong(this.getString(key), defaultValue);
		}
		
		/**
		 * 获取long类型属性值
		 * @param key 属性名
		 * @return 属性值
		 */
		public long getLong(String key){
			return getLong(key, null);
		}
		
		/**
		 * 获取char类型属性值
		 * 
		 * @param key 属性名
		 * @param defaultValue 默认值
		 * @return 属性值
		 */
		public Character getChar(String key, String defaultValue) {
			final String value =this. getString(key, defaultValue);
			if(StringUtils.isBlank(value)) {
				return null;
			}
			return value.charAt(0);
		}
		
		/**
		 * 获取char类型属性值
		 * 
		 * @param key 属性名
		 * @return 属性值
		 */
		public Character getChar(String key) {
			return getChar(key, null);
		}
		
		/**
		 * 获取double类型属性值
		 * 
		 * @param key 属性名
		 * @param defaultValue 默认值
		 * @return 属性值
		 */
		public Double getDouble(String key, Double defaultValue) throws NumberFormatException {
			return ConvertUtils.toDouble(this.getString(key), defaultValue);
		}
		
		/**
		 * 获取double类型属性值
		 * 
		 * @param key 属性名
		 * @return 属性值
		 */
		public Double getDouble(String key) throws NumberFormatException {
			return getDouble(key, null);
		}
		
		//----------------------------------------------------------------------- Get end
		
		/**
		 * 设置值，无给定键创建之。设置后未持久化
		 * @param key 属性键
		 * @param value 属性值
		 */
		public void setProperty(String key, Object value){
			super.setProperty(key, value.toString());
		}
		
		public static void main(String[] args) {
			System.out.println(Props.class.getClassLoader().getResource(""));
		}
	}


package com.wfcsu.wfweb.mapping.impl;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

import com.wfcsu.wfweb.action.ActionForword;
import com.wfcsu.wfweb.action.ActionMapping;
import com.wfcsu.wfweb.mapping.IActionConfig;
import com.wfcsu.wfweb.mapping.IParser;




public class Parser implements IParser {
	private String file;
	public Parser(String file){
		this.file=file;
	}
	public IActionConfig parser() {
		
		IActionConfig config = new ActionConfig();
		ActionMapping mapping = null;
		ActionForword forward = null;
		//jdom解析
		SAXBuilder builder = new SAXBuilder();
		
		try {
			
			Document doc = builder.build( new File( file ) );
			//查询form-bean节点中的数据
			XPath xpath = XPath.newInstance( "//form-bean" );
			List list = xpath.selectNodes( doc.getRootElement() );
			
			for( Iterator i = list.iterator();i.hasNext(); ){
				
				Element form = (Element)i.next();
				
				String name = form.getAttributeValue( "name" );
				String type = form.getAttributeValue( "type" );
				
				config.setActionForm( name, type );//把form-bean中的属性值添加 到集合中
				//key为name属性	
			}
			//XPath  查询指定标签的集合
			 xpath = XPath.newInstance( "//action" );
			 list = xpath.selectNodes( doc.getRootElement() );
			
			for( Iterator i = list.iterator();i.hasNext(); ){
				
				Element action = (Element)i.next();//action节点
				mapping = new ActionMapping();
				
				//取节点属性放于mapping中
				mapping.setPath( action.getAttributeValue( "path" ) );
				mapping.setType( action.getAttributeValue( "type" ) );
				mapping.setParameter( action.getAttributeValue( "parameter" ) );
				mapping.setName(action.getAttributeValue("name"));
				//取子节点forward
				List childList = action.getChildren();
				for( int j = 0;j < childList.size();j++ ){
					
					Element child = (Element)childList.get( j );//forward节点
					
					forward = new ActionForword();
					
					//把节点的属性值放于forward对像中
					forward.setName( child.getAttributeValue( "name" ) );
					forward.setPath( child.getAttributeValue( "path" ) );
					forward.setRedirect( Boolean.parseBoolean( child.getAttributeValue( "redirect" ) ) );
					
					//把forward添加 到mapping中去
					mapping.setForword( forward );
				}
				//把mapping添加到ActionConfig中去
				config.setActionMapping( mapping );
			}
		
		} catch ( Exception e) {
			e.printStackTrace();
		} 
		return config;
	}

}

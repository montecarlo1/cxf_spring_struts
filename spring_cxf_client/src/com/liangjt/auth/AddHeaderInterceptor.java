package com.liangjt.auth;

import java.util.List;

import javax.xml.namespace.QName;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class AddHeaderInterceptor extends AbstractPhaseInterceptor<SoapMessage>{
	private String userId;
	private String userPass;

	public AddHeaderInterceptor(String userId, String userPass) {
		//在发送soap消息时调用该拦截器
		super(Phase.PREPARE_SEND);
		this.userId= userId;
		this.userPass = userPass;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handleMessage(SoapMessage msg) throws Fault {
		List<Header> headers = msg.getHeaders();
		//创建dom对象
		Document doc = DOMUtils.createDocument();
		Element e = doc.createElement("authHeader");
		
		//此处创建的元素要和服务器端一一对应
		Element id = doc.createElement("userId");
		id.setTextContent(userId);
		Element pass = doc.createElement("userPass");
		pass.setTextContent(userPass);
		
		e.appendChild(id);
		e.appendChild(pass);
		
		/*
		 * 生成了一个xml片段
		 * <authHeader>
		 * 		<userId>zhangsan</userId>
		 * 		《userPass>1234</userPass>
		 * <authHeader>
		 * 
		 */
		
		//把e元素包装秤Header，并添加到soap消息列表中
		headers.add(new Header(new QName("webService"),e));
		
		
		
	}

}

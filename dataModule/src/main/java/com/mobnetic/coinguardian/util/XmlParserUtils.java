package com.mobnetic.coinguardian.util;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlParserUtils {

	public static Node getFirstElementByTagName(Document doc, String name) {
		NodeList nodes = doc.getElementsByTagName(name);
        if(nodes!=null && nodes.getLength()>0)
        	return nodes.item(0);
        return null;
	}
        
	public static double getDoubleNodeValue(Node node) throws Exception {
		return Double.parseDouble(getTextNodeValue(node));
	}
	
	public static String getTextNodeValue(Node node) throws Exception {
        Node child;
        if (node != null) {
            if (node.hasChildNodes()) {
                child = node.getFirstChild();
                while(child != null) {
                    if (child.getNodeType() == Node.TEXT_NODE) {
                        return child.getNodeValue();
                    }
                    child = child.getNextSibling();
                }
            }
        }
        return "";
    }
}

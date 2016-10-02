package com.mobnetic.coinguardian.model.market;

import java.io.StringReader;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.text.TextUtils;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;
import com.mobnetic.coinguardian.util.XmlParserUtils;

public class McxNOW extends Market {

	private final static String NAME = "McxNOW";
	private final static String TTS_NAME = "MCX now";
	private final static String URL = "https://mcxnow.com/orders?cur=%1$s";
	private final static String URL_CURRENCY_PAIRS = "https://mcxnow.com/current";
	
	public McxNOW() {
		super(NAME, TTS_NAME, null);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyBase());
	}
	
	@Override
	protected void parseTicker(int requestId, String responseString, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(responseString));
        Document doc = db.parse(is);
        
        ticker.bid = getFirstPriceFromOrder(doc, "buy");
        ticker.ask = getFirstPriceFromOrder(doc, "sell");
        ticker.vol = XmlParserUtils.getDoubleNodeValue(XmlParserUtils.getFirstElementByTagName(doc, "curvol"));
        ticker.high = XmlParserUtils.getDoubleNodeValue(XmlParserUtils.getFirstElementByTagName(doc, "priceh"));
        ticker.low = XmlParserUtils.getDoubleNodeValue(XmlParserUtils.getFirstElementByTagName(doc, "pricel"));
        ticker.last = XmlParserUtils.getDoubleNodeValue(XmlParserUtils.getFirstElementByTagName(doc, "lprice"));
	}
	
	private double getFirstPriceFromOrder(Document doc, String arrayName) throws Exception {
		Node arrayNode = XmlParserUtils.getFirstElementByTagName(doc, arrayName);
		if(arrayNode!=null) {
			NodeList orderNodes = ((Element)arrayNode).getElementsByTagName("o");
			if(orderNodes!=null && orderNodes.getLength()>1) {
				Node orderNode = orderNodes.item(1);
				if(orderNode!=null && orderNode instanceof Element) {
					NodeList priceNodes = ((Element)orderNode).getElementsByTagName("p");
					if(priceNodes!=null && priceNodes.getLength()>0) {
						return XmlParserUtils.getDoubleNodeValue(priceNodes.item(0));
					}
				}
			}
		}
        return Ticker.NO_DATA;
	}
	
	// ====================
	// Get currency pairs
	// ====================
	@Override
	public String getCurrencyPairsUrl(int requestId) {
		return URL_CURRENCY_PAIRS;
	}
	
	@Override
	protected void parseCurrencyPairs(int requestId, String responseString, List<CurrencyPairInfo> pairs) throws Exception {
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(responseString));
        Document doc = db.parse(is);
        
        
        final NodeList nodes = doc.getElementsByTagName("cur");
        Element node = null;
        if(nodes!=null) {
        	for(int i=0; i<nodes.getLength(); ++i) {
        		node = (Element)nodes.item(i);
        		if(node!=null) {
        			final String currency = node.getAttribute("tla");
        			if(!TextUtils.isEmpty(currency) && !VirtualCurrency.BTC.equals(currency))
        			pairs.add(new CurrencyPairInfo(currency, VirtualCurrency.BTC, null));
        		}
        	}
        }
	}
}

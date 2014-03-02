package com.mobnetic.coinguardian.model.market;

import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;
import com.mobnetic.coinguardian.util.XmlParserUtils;

public class McxNOW extends Market {

	public final static String NAME = "McxNOW";
	public final static String TTS_NAME = "MCX now";
	public final static String URL = "https://mcxnow.com/orders?cur=%1$s";
	public final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.LTC, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.XPM, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.PPC, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.FTC, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.MAX, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.MNC, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.SC, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.WDC, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.CL, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.MXB, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.MCX, new String[]{
				VirtualCurrency.BTC
			});
	}
	
	public McxNOW() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyBase());
	}
	
	@Override
	protected void parseTickerInner(int requestId, String responseString, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
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
}

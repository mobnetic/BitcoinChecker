package com.mobnetic.coinguardian.config;

import java.util.LinkedHashMap;

import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.market.Anxpro;
import com.mobnetic.coinguardian.model.market.BitKonan;
import com.mobnetic.coinguardian.model.market.BitMarketPL;
import com.mobnetic.coinguardian.model.market.BitX;
import com.mobnetic.coinguardian.model.market.BitcoinAverage;
import com.mobnetic.coinguardian.model.market.BitcoinCentralNet;
import com.mobnetic.coinguardian.model.market.Bitcurex;
import com.mobnetic.coinguardian.model.market.Bitfinex;
import com.mobnetic.coinguardian.model.market.Bitorado;
import com.mobnetic.coinguardian.model.market.Bitstamp;
import com.mobnetic.coinguardian.model.market.Btcchina;
import com.mobnetic.coinguardian.model.market.Btce;
import com.mobnetic.coinguardian.model.market.Btcturk;
import com.mobnetic.coinguardian.model.market.Bter;
import com.mobnetic.coinguardian.model.market.CCex;
import com.mobnetic.coinguardian.model.market.Campbx;
import com.mobnetic.coinguardian.model.market.CexIO;
import com.mobnetic.coinguardian.model.market.CoinDesk;
import com.mobnetic.coinguardian.model.market.CoinJar;
import com.mobnetic.coinguardian.model.market.CoinMarketIO;
import com.mobnetic.coinguardian.model.market.Coinbase;
import com.mobnetic.coinguardian.model.market.Coinse;
import com.mobnetic.coinguardian.model.market.CryptoRush;
import com.mobnetic.coinguardian.model.market.CryptoTrade;
import com.mobnetic.coinguardian.model.market.Cryptsy;
import com.mobnetic.coinguardian.model.market.Fxbtc;
import com.mobnetic.coinguardian.model.market.FybSE;
import com.mobnetic.coinguardian.model.market.FybSG;
import com.mobnetic.coinguardian.model.market.Huobi;
import com.mobnetic.coinguardian.model.market.Justcoin;
import com.mobnetic.coinguardian.model.market.Koinim;
import com.mobnetic.coinguardian.model.market.Kraken;
import com.mobnetic.coinguardian.model.market.McxNOW;
import com.mobnetic.coinguardian.model.market.Mercado;
import com.mobnetic.coinguardian.model.market.MintPal;
import com.mobnetic.coinguardian.model.market.Okcoin;
import com.mobnetic.coinguardian.model.market.Poloniex;
import com.mobnetic.coinguardian.model.market.Prelude;
import com.mobnetic.coinguardian.model.market.TheRock;
import com.mobnetic.coinguardian.model.market.VaultOfSatoshi;
import com.mobnetic.coinguardian.model.market.Vircurex;
import com.mobnetic.coinguardian.model.market.Virtex;
import com.mobnetic.coinguardian.model.market.Winkdex;

public class MarketsConfig {

	public final static LinkedHashMap<String, Market> MARKETS = new LinkedHashMap<String, Market>();
	private final static void addMarket(Market market) {
		MarketsConfig.MARKETS.put(market.key, market);
	}
	
	static {
		addMarket(new Bitstamp());
//		addMarket(new Mtgox());
		addMarket(new Btcchina());
		addMarket(new Btce());
		addMarket(new Bitcurex());
		addMarket(new Mercado());
		addMarket(new Kraken());
		addMarket(new Bitfinex());
		addMarket(new Okcoin());
		addMarket(new Cryptsy());
		addMarket(new Coinbase());
		addMarket(new Vircurex());
		addMarket(new Fxbtc());
		addMarket(new Bter());
		addMarket(new BitcoinCentralNet());
		addMarket(new Justcoin());
		addMarket(new Btcturk());
		addMarket(new Coinse());
		addMarket(new Campbx());
		addMarket(new BitcoinAverage());
		addMarket(new TheRock());
		addMarket(new CexIO());
		addMarket(new Virtex());
		addMarket(new Huobi());
		addMarket(new VaultOfSatoshi());
		addMarket(new CoinMarketIO());
		addMarket(new McxNOW());
		addMarket(new CryptoTrade());
		addMarket(new MintPal());
		addMarket(new CoinJar());
		addMarket(new Poloniex());
		addMarket(new Winkdex());
		addMarket(new Anxpro());
		addMarket(new BitX());
		addMarket(new CCex());
		addMarket(new BitMarketPL());
		addMarket(new Bitorado());
		addMarket(new CryptoRush());
		addMarket(new CoinDesk());
		addMarket(new Koinim());
		addMarket(new FybSE());
		addMarket(new FybSG());
		addMarket(new Prelude());
		addMarket(new BitKonan());
	}
}

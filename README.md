__Bitcoin Checker__ is a FREE app to track the most recent prices of your favourite currency pairs (on over 30 supported exchanges) in many customizable ways (such as rich notifications, TTS voice announcements, Home and Lockscreen widget or multiple alarms).

###Hello Bitcoin Checker users!  
As you know, number of virtual currencies increasing very fast. Currencie pairs set on existing exchanges changes almost everyday and also there is need to add new and new exchanges over time.
_We proudly announce_ that [DataModule](https://github.com/mobnetic/BitcoinChecker/tree/master/DataModule) (containing exchanges and currency pairs) for Bitcoin Checker app is now _OPEN_ for our users to make this application _even better_! This means that _anyone_ can now:
* Add support for new exchange
* Update currency pairs on their favourite exchange

###Issues
Please submit all requests for new exchanges/currency pairs or bugs in Bitcoin Checker apps in the [Issues](https://github.com/mobnetic/BitcoinChecker/issues) section.

#####Bitcoin Checker on Google Play Store:
https://play.google.com/store/apps/details?id=com.mobnetic.coinguardian

###Donate to Bitcoin Checker project:
♥ __BTC__: 1KyLY5sT1Ffa6ctFPFpdL2bxhSAxNqfvMA  
♥ __DOGE__: D81kyZ49E132enb7ct7RcPGpjgsrN7bsd7  
♥ __LTC__: LZ3EiK42o5nbDW3cwiaKUptFQ9eBA3x1vw  

#Introduction
To start working you should fork this repo. It basically contains two projects:
* [DataModule](https://github.com/mobnetic/BitcoinChecker/tree/master/DataModule): library project that stores information about exchanges and currencies used in Bitcoin Checker. This is the project that you will work with.
* [DataModuleTester](https://github.com/mobnetic/BitcoinChecker/tree/master/DataModuleTester): simple project that provides minimal interface in order to launch and test your changes - whether they work:)

The whole tutorial described below reffers to the [DataModule](https://github.com/mobnetic/BitcoinChecker/tree/master/DataModule) project because only thit project is meant to be edited by users. After doing your changes please create pull request to the original repo.


#Updating currency pairs on existing exchange:
To update currency pairs on your favourite exchange you have to find corresponding exchange class file in [com.mobnetic.coinguardian.model.market](https://github.com/mobnetic/BitcoinChecker/tree/master/DataModule/src/com/mobnetic/coinguardian/model/market) package.  
In every exchange file there is `CURRENCY_PAIRS` HashMap that contains a base currency (as a key) and a list of counter currencies. Every combination of base and counter currency represents one currency pair.
```java
CURRENCY_PAIRS.put(VirtualCurrency.LTC, new String[]{		// Base currency
		VirtualCurrency.BTC,						        // Counter currency
		Currency.USD,										// Counter currency
		Currency.RUR,										// Counter currency
		Currency.EUR										// Counter currency
	});
```
This example from [BTC-e](https://github.com/mobnetic/BitcoinChecker/blob/master/DataModule/src/com/mobnetic/coinguardian/model/market/Btce.java) represents 4 pairs: `LTC/BTC`, `LTC/USD`, `LTC/RUR` and `LTC/EUR`.

__HINT:__ Good practise is to keep alphabetical order of base currencies (or even with counter currencies) but sometimes it's also good to mirror order from exchange site.

While adding new pairs you should use currency names from these two classes:
- [Currency](https://github.com/mobnetic/BitcoinChecker/blob/master/DataModule/src/com/mobnetic/coinguardian/model/currency/Currency.java) - where you can find fiat currencies
- [VirtualCurrency](https://github.com/mobnetic/BitcoinChecker/blob/master/DataModule/src/com/mobnetic/coinguardian/model/currency/VirtualCurrency.java) - where all crypto/virtual currencies are stored

###Some currencies are missing?
You want to add some currency pairs but one currency (or both) is missing in Currency or VirtualCurrency class?  
Just just add them to Currency or VirtualCurrency class. Please put all fiat/normal currencies in Currency file and all crypto/virtual currencies in VirtualCurrency.


#Adding new exchange:
###Example:
Please see example of a class that represents single exchange here - [MarketExample](https://github.com/mobnetic/BitcoinChecker/blob/master/DataModule/src/com/mobnetic/coinguardian/model/market/example/MarketExample.java)

##1. New exchange configuration:
To add support for new exchange you have to provide some constants describing that particular exchange:
* `NAME` - name of exchange that will be displayed in app.  
* `TTS_NAME` - name of exchange that will be used in spoken announements. Sometimes it's just fine to put `NAME` here (see [Kraken](https://github.com/mobnetic/BitcoinChecker/blob/master/DataModule/src/com/mobnetic/coinguardian/model/market/Kraken.java)), but sometines it's better to provide more spoken friendly version (like on [McxNOW](https://github.com/mobnetic/BitcoinChecker/blob/master/DataModule/src/com/mobnetic/coinguardian/model/market/McxNOW.java) - "MCX now").  
* `URL` - this field stores Url for Ticker API. Most often it contains some two (or one) parameters (%1$s and %2$s). These parameters will be replaces with currency names or selected currency pair. Providing URL is described in the next section. 
* `CURRENCY_PAIRS` - map of all currencies supported by this exchange - described later. 

These constants (without `URL`) should be provided in default constructor:
```java
public MarketExample() {
	super(NAME, TTS_NAME, CURRENCY_PAIRS);
}
```
##2. Providing currency pairs:
You have to specify which currency pairs are supported by your new exchange. Description for this is done above, in 
[Updating currency pairs on existing exchange](https://github.com/mobnetic/BitcoinCheckerDataModule#updating-currency-pairs-on-existing-exchange) section.

##3. Providing API Url:
API Url is provided by getUrl method. The simplest implementation is to just return URL field. Sometimes Url requires some additionsl parameters (line currency names) - then you have to provide them using ```String.format()``` method.  
See examples below:

```java
Example without parameters:
API example: https://www.bitstamp.net/api/ticker/
URL field: https://www.bitstamp.net/api/ticker/

@Override
public String getUrl(int requestId, CheckerInfo checkerInfo) {
	return URL;
}
```

```java
Example with arguments - for given currency pair:
API example: https://anxpro.com/main/stats?ccyPair=BTCUSD
URL field: https://anxpro.com/main/stats?ccyPair=%1$s%2$s

@Override
public String getUrl(int requestId, CheckerInfo checkerInfo) {
	return String.format(URL, checkerInfo.getCurrencyBase(), checkerInfo.getCurrencyCounter());
}
```


Note that currency names are always in uppercase and some APIs requires them to be in lowercase
```java
Example with lowercase currency parameters:
API example: https://bter.com/api/1/ticker/btc_cny
URL field: https://bter.com/api/1/ticker/%1$s_%2$s

@Override
public String getUrl(int requestId, CheckerInfo checkerInfo) {
	return String.format(URL, checkerInfo.getCurrencyBaseLowerCase(), checkerInfo.getCurrencyCounterLowerCase());
}
```

###3a. Providing other parameters in URL (advanced):
Sometimes there is a need to include some kind of pair ID instead of just currencies names. Please see [Cryptsy](https://github.com/mobnetic/BitcoinChecker/blob/master/DataModule/src/com/mobnetic/coinguardian/model/market/Cryptsy.java) as en example. There is separate `CURRENCY_PAIRS_IDS` map that holds pairs ids:
```java
[...]
CURRENCY_PAIRS_IDS.put("DMD_BTC", 72);
CURRENCY_PAIRS_IDS.put("DOGE_BTC", 132);
CURRENCY_PAIRS_IDS.put("DOGE_LTC", 135);
CURRENCY_PAIRS_IDS.put("DVC_BTC", 40);
[...]
```

While providing URL we need to obtain proper ID that is associated with this pair:
```java
Example for DOGE/BTC (id=132) on Cryptsy:
API example: http://pubapi.cryptsy.com/api.php?method=singlemarketdata&marketid=132
URL field: http://pubapi.cryptsy.com/api.php?method=singlemarketdata&marketid=%1$s

@Override
public String getUrl(int requestId, CheckerInfo checkerInfo) {
	final String pairString = String.format("%1$s_%2$s", checkerInfo.getCurrencyBase(), checkerInfo.getCurrencyCounter());
	if(CURRENCY_PAIRS_IDS.containsKey(pairString))
		return String.format(URL, String.valueOf(CURRENCY_PAIRS_IDS.get(pairString)));	
	return URL;
}
```

##4. Parsing API response:
While parsing response from exchange you have to fill fieds of [Ticker](https://github.com/mobnetic/BitcoinChecker/blob/master/DataModule/src/com/mobnetic/coinguardian/model/Ticker.java) object.  
If API response is just in plain JSON object you can parse it in parseTickerInnerFromJsonObject method:
```java
@Override
protected void parseTickerInnerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerRecord) throws Exception {
  ticker.bid = jsonObject.getDouble("bid");
  ticker.ask = jsonObject.getDouble("ask");
  ticker.vol = jsonObject.getDouble("volume");
  ticker.high = jsonObject.getDouble("high");
  ticker.low = jsonObject.getDouble("low");
  ticker.last = jsonObject.getDouble("last");
  ticker.timestamp = jsonObject.getLong("timestamp");
}
```

__IMPORTANT:__ that the ticker.last field is obligated, all the rest of fields are optional.  
__NOTE:__ parsing `timestamp` field (in millis) is not required. If omitted, Bitcoin Checker would fill it with `now` date. If you want to parse this information please note that some exchanges provides time in different formats (like seconds or nanos) so you have to multiply or divide it to get time in millis format. You can use `TimeUtils.NANOS_IN_MILLIS` or `TimeUtils.MILLIS_IN_SECOND` constants from [TimeUtils](https://github.com/mobnetic/BitcoinChecker/blob/master/DataModule/src/com/mobnetic/coinguardian/util/TimeUtils.java) for that.

###4a. Parsing non JSONObject responses (advanced):
Sometimes responses are more complicated than plain JSON, then you should use `parseTickerInner` method. The default implementation try to parse received response as a `JSONObject`, but you can parse also other formats but overriding this method:
```java
protected void parseTickerInner(int requestId, String responseString, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
	parseTickerInnerFromJsonObject(requestId, new JSONObject(responseString), ticker, checkerInfo);
}
```

You can find examples of usage:
* [Huobi](https://github.com/mobnetic/BitcoinChecker/blob/master/DataModule/src/com/mobnetic/coinguardian/model/market/Huobi.java): "almost" JSON object response, there is a need to trim some characters at the begining and at the end of the response
* [MintPal](https://github.com/mobnetic/BitcoinChecker/blob/master/DataModule/src/com/mobnetic/coinguardian/model/market/MintPal.java): JSON array response (instead of JSON object)
* [McxNOW](https://github.com/mobnetic/BitcoinChecker/blob/master/DataModule/src/com/mobnetic/coinguardian/model/market/McxNOW.java): XML based response

##5. Parsing error (not required):
Sometimes an exchange is down but with some error message in their API (See [Crypto-Trade](https://github.com/mobnetic/BitcoinChecker/blob/master/DataModule/src/com/mobnetic/coinguardian/model/market/CryptoTrade.java) as an example). You can also handle this situation and display error message directly from exchange to the user. There are two methods related with it and they are designed in similar way to parsing normal response:

```java
protected String parseErrorFromJsonObject(int requestId, JSONObject jsonObject, CheckerInfo checkerInfo);
```
or if JSONObject is not suitable you can override following method:
```java
public String parseError(int requestId, String responseString, CheckerInfo checkerInfo);
```

##6. Enablind exchange:
To enable newly created exchange you should add corresponding line at the bottom of `MarketsConfig` file:
```java
static {
  [...]
  addMarket(new MyNewExchangeClass());
}
```

#Advanced things
##Multiple requests per exchange:
Some exchanges does not provide nice ticker api with all important information (bid, ask, vol, high, low, last), so there is a need to perform few requests (for example 2) to acquire as many information as possible.  
These requests will be performed in a sequense and new price notification would appear when all of these requests are finished.  
See [Poloniex](https://github.com/mobnetic/BitcoinChecker/blob/master/DataModule/src/com/mobnetic/coinguardian/model/market/Poloniex.java) exchange as a good example. In order to perform 2 requests you have to override `getNumOfRequests` method:
```java
@Override
public int getNumOfRequests(CheckerInfo checkerRecord) {
	return 2;
}
```

Then make use of requestId variable passed to `getUrl` and `parseTickerInnerFromJsonObject` methods.  
`requestId` variable is incremented from `0` to `numOfRequests-1` for every new request made.
From first request we are able to obtain only `last` price. We want to obtain also `bid` and `ask` values so we do another request for orders list:
```java
@Override
public String getUrl(int requestId, CheckerInfo checkerInfo) {
	if(requestId==0)
		return URL;
	else
		return String.format(URL_ORDERS, checkerInfo.getCurrencyCounter(), checkerInfo.getCurrencyBase()); // Reversed currencies
}
	
@Override
protected void parseTickerInnerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
	if(requestId==0) {
		ticker.last = jsonObject.getDouble(checkerInfo.getCurrencyCounter()+"_"+checkerInfo.getCurrencyBase());  // Reversed currencies
	} else {
		ticker.bid = getFirstPriceFromOrder(jsonObject, "bids");
		ticker.ask = getFirstPriceFromOrder(jsonObject, "asks");
	}
}
```


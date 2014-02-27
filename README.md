# Coming soon!
###Hello Bitcoin Checker users!  
As you know, number of virtual currencies increasing very fast. Currencie pairs set on existing exchanges changes almost everyday and also there is need to add new and new exchanges over time.
_We proudly announce_ that `DataModule` (containing exchanges and currency pairs) for Bitcoin Checker app is now _OPEN_ for our users to make this application _even better_! This means that _anyone_ can now:
* Add support for new exchange
* Update currency pairs on their favourite exchange

#####Bitcoin Checker on Google Play Store:
https://play.google.com/store/apps/details?id=com.mobnetic.coinguardian

###Donate to Bitcoin Checker project:
♥ __BTC__: 1KyLY5sT1Ffa6ctFPFpdL2bxhSAxNqfvMA  
♥ __DOGE__: D81kyZ49E132enb7ct7RcPGpjgsrN7bsd7  
♥ __LTC__: LZ3EiK42o5nbDW3cwiaKUptFQ9eBA3x1vw  

###Issues
Please put all requests for new exchanges/currency pairs or bugs in Bitcoin Checker apps in the Issues section.

#Introduction
To start working you should clone this repo. It basically contains two projects:
* `BitcoinCheckerDataModule`: library project that stores information about exchanges and currencies used in Bitcoin Checker. This is the project that you will work with.
* `BitcoinCheckerDataModuleTester`: simple project that you will launch in order to test your changes - whether they work:)

The whole tutorial described below reffers to the `BitcoinCheckerDataModule` project because only thit project is meant to be edited by users.
#####Let the crypto fun begin!

#Updating currency pairs on existing exchange:
To update currency pairs on youir favourite exchange you have to find corresponding exchange class file in `com.mobnetic.coinguardian.model.market` package.  
In every exchange file there is `CURRENCY_PAIRS` HashMap that contains a base currency (as a key) and a list of counter currencies. Every combination of base and counter currency represents one currency pair.
```java
CURRENCY_PAIRS.put(VirtualCurrency.LTC, new String[]{		// Base currency
		VirtualCurrency.BTC,						        // Counter currency
		Currency.USD,										// Counter currency
		Currency.CNY										// Counter currency
	});
```
This example represents 3 pairs: `LTC/BTC` and `LTC/USD` and `LTC/CNY`.

__HINT:__ Good practise is to keep alphabetical order of base currencies (or even with counter currencies) but sometimes it's also good to mirror order from exchange site.

While adding new pairs you should use currency names from these two classes:
- Currency - where you can find fiat currencies
- VirtualCurrency - where all crypto/virtual currencies are stored

###Some currencies are missing?
You want to add some currency pairs but one currency (or both) is missing in Currency or VirtualCurrency class?  
Just just add them to Currency or VirtualCurrency class. Please place all fiat/normal currencies in Currency file and all crypto/virtual currencies in VirtualCurrency.


#Adding new exchange:
##1. New exchange configuration
To add support for new exchange you have to provide some constants describing that particular exchange:
* `NAME` - Name of exchange that will be displayed in app.  
* `TTS_NAME` - Name of exchange that will be used in spoken announements. Sometimes it's just fine to put `NAME` here (see Kraken), but sometines it's better to provide more spoken friendly version (like on McxNOW - "MCX now").  
* `URL` - this field stores Url for Ticker API. Most often it contains some two (or one) parameters (%1$s and %2$s). These parameters will be replaces with currency names or selected currency pair. Providing URL is described in the next section. 
* `CURRENCY_PAIRS` - Map of all currencies supported by this exchange (described in other section).  

These constants (without `URL`) should be provided in default constructor:
```java
public MarketExample() {
	super(NAME, TTS_NAME, CURRENCY_PAIRS);
}
```

##2. Providing API Url:
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

##3. Parsing API response:
While parsing response from exchange you have to fill fieds of `Ticker` object.  
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
__NOTE:__ parsing `timestamp` field (in millis) is not required. If omitted, Bitcoin Checker would fill it with `now` date. If you want to parse this information please note that some exchanges provides time in different formats (like seconds or nanos) so you have to multiply or divide it to get time in millis format.  

###3a. Parsing non JSONObject responses:
Sometimes responses are more complicated than plain JSON, then you should use `parseTickerInner` method. The default implementation try to parse received response as a `JSONObject`, but you can parse also other formats but overriding this method:
```java
protected void parseTickerInner(int requestId, String responseString, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
	parseTickerInnerFromJsonObject(requestId, new JSONObject(responseString), ticker, checkerInfo);
}
```

You can find examples of usage:
* Huobi: "almost" JSON object response, there is a need to trim some characters at the begining and at the end of the response
* MintPal: JSON array response (instead of JSON object)
* McxNOW: XML based response

##4. Parsing error (not required):
Sometimes an exchange is down but with some error message in their API (See `Crypto-Trade` as an example). You can also handle this situation and display error message directly from exchange to the user. There are two methods related with it and they are designed in similar way to parsing normal response:

```java
protected String parseErrorFromJsonObject(int requestId, JSONObject jsonObject, CheckerInfo checkerInfo);
```
or if JSONObject is not suitable you can override following method:
```java
public String parseError(int requestId, String responseString, CheckerInfo checkerInfo);
```

##5. Enablind exchange:
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
See `Poloniex` exchange as a good example. In order to perform 2 requests you have to override `getNumOfRequests` method:
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


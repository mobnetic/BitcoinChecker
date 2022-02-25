> Notice: This page is slightly outdated and needs updating (java->kotlin).

# Contibuting to Bitcoin Checker

<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
## Table of Contents

- [Introduction](#introduction)
  - [Updating currency pairs on existing exchange:](#updating-currency-pairs-on-existing-exchange)
    - [Adding new pair on Cryptsy?](#adding-new-pair-on-cryptsy)
    - [Good practise:](#good-practise)
    - [Some currencies are missing?](#some-currencies-are-missing)
  - [Adding new exchange:](#adding-new-exchange)
      - [Example:](#example)
    - [1. New exchange configuration:](#1-new-exchange-configuration)
    - [2. Providing currency pairs:](#2-providing-currency-pairs)
    - [3. Providing API Url:](#3-providing-api-url)
      - [3a. Providing other parameters in URL (advanced):](#3a-providing-other-parameters-in-url-advanced)
    - [4. Parsing API response:](#4-parsing-api-response)
      - [4a. Parsing non JSONObject responses (advanced):](#4a-parsing-non-jsonobject-responses-advanced)
    - [5. Parsing error (not required):](#5-parsing-error-not-required)
    - [6. Fetching currency pairs directly from exchange:](#6-fetching-currency-pairs-directly-from-exchange)
    - [7. Enabling exchange:](#7-enabling-exchange)
  - [Advanced things](#advanced-things)
    - [Multiple requests per exchange:](#multiple-requests-per-exchange)
    - [Multiple requests while fetching currency pairs](#multiple-requests-while-fetching-currency-pairs)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->
<!-- To regenerate doctoc, run `doctoc --title "## Table of Contents" README.md` -->
<!-- https://github.com/thlorenz/doctoc -->

# Introduction
To start working, you should fork this repo. It basically contains two projects:
* [DataModule](/dataModule): Library project that stores information about exchanges and currencies used in Bitcoin Checker. This is the project that you will work with.
* [DataModuleTester](/dataModuleTester): Simple project that provides minimal interface in order to launch and test your changes - to see if they will work :)

The whole tutorial described below refers to the [DataModule](dataModule) project because only this project is meant to be edited by users. After making your changes, please create a pull request to the original repo.


## Updating currency pairs on existing exchange:
*__Note if particular exchange supports dynamic currency pairs syncing mechanism there is NO need to add pairs manually here.__ *

To update currency pairs on your favourite exchange, you have to find the corresponding exchange class file in the [com.aneonex.bitcoinchecker.datamodule.model.market](dataModule/src/main/java/com/aneonex/bitcoinchecker/datamodule/model/market) package.
In every exchange file there is a `CURRENCY_PAIRS` HashMap that contains a base currency (as a key) and a list of counter currencies. Every combination of base and counter currency represents one currency pair.

```java
CURRENCY_PAIRS.put(VirtualCurrency.LTC,  // Base currency
  new String[]{
    VirtualCurrency.BTC,             // Counter currency
    Currency.USD,                    // Counter currency
    Currency.RUR,                    // Counter currency
    Currency.EUR                     // Counter currency
  }
);
```
This example from [BTC-e](dataModule/src/main/java/com/aneonex/bitcoinchecker/datamodule/model/market/Btce.kt) represents 4 pairs: `LTC/BTC`, `LTC/USD`, `LTC/RUR` and `LTC/EUR`.

### Adding new pair on Cryptsy?
This is generally enough, but while adding a new currency pair on [Cryptsy](dataModule/src/main/java/com/aneonex/bitcoinchecker/datamodule/model/market/Cryptsy.kt) you also need to provide a special pair ID. Please include it in a map called `CURRENCY_PAIRS_IDS`, as shown here:

```java
[...]
CURRENCY_PAIRS_IDS.put("DOGE_BTC", 132);
CURRENCY_PAIRS_IDS.put("DOGE_LTC", 135);
[...]
```

The simplest way to find the pair ID is to click or hover on that particular pair in the trading section on the Cryptsy website. The number at the end of the page url represents the ID of that particular pair: https://www.cryptsy.com/markets/view/132

### Good practise:
Try to keep alphabetical order of base currencies (or even with counter currencies) but sometimes it's also good to mirror the order from the exchange site.

While adding new pairs, you should use currency names from these two classes:
- [Currency](dataModule/src/main/java/com/aneonex/bitcoinchecker/datamodule/model/currency/Currency.kt) - where you can find fiat currencies
- [VirtualCurrency](dataModule/src/main/java/com/aneonex/bitcoinchecker/datamodule/model/currency/VirtualCurrency.kt) - where all of the crypto/virtual currencies are stored

### Some currencies are missing?
You want to add some currency pairs but one currency (or both) is missing in Currency or VirtualCurrency class?
Just add them to the Currency or VirtualCurrency class. Please put all fiat/normal currencies in the Currency.kt file and all crypto/virtual currencies in VirtualCurrency.kt.


## Adding new exchange:
#### Example:
Please see the example of a class that represents a single exchange here - [MarketExample](dataModule/src/main/java/com/aneonex/bitcoinchecker/datamodule/model/market/example/MarketExample.kt)

### 1. New exchange configuration:
To add support for a new exchange, you have to provide some constants describing that particular exchange:
* `NAME` - name of the exchange that will be displayed in the app.
* `TTS_NAME` - name of the exchange that will be used in spoken announements. Sometimes it's just fine to put `NAME` here (see [Kraken](dataModule/src/main/java/com/aneonex/bitcoinchecker/datamodule/model/market/Kraken.kt)), but sometimes it's better to provide a more spoken friendly version (like on [McxNOW](dataModule/src/main/java/com/aneonex/bitcoinchecker/datamodule/model/market/McxNOW.kt) - "MCX now").
* `URL` - this field stores the Url for the Ticker API. Most often it contains two parameters, but sometimes it has one (`%1$s` and `%2$s`). These parameters are replaced with currency names or the selected currency pair. Providing a URL is described in the next section.
* `CURRENCY_PAIRS` - map of all currencies supported by this exchange - described later.

These constants (without `URL`) should be provided in the default constructor:

```java
public MarketExample() {
  super(NAME, TTS_NAME, CURRENCY_PAIRS);
}
```

### 2. Providing currency pairs:
If given exchanges provides a mechanism to fetch currency pairs dynamically, there is no need to specify them manually then.   Please see [this section](# 6-fetching-currency-pairs-directly-from-exchange).

Otherwise you have to specify which currency pairs are supported by your new exchange. Description for this is done above, in the [Updating currency pairs on existing exchange](https://github.com/aneonex/BitcoinChecker#updating-currency-pairs-on-existing-exchange) section.

### 3. Providing API Url:
The API Url is provided by the getUrl method. The simplest implementation is to just return the URL field. Sometimes, the Url requires some additional parameters (like currency names) - then you have to provide them using ```String.format()``` method.
See examples below:

##### Example without parameters:
* API example: https://www.bitstamp.net/api/ticker/
* URL field: https://www.bitstamp.net/api/ticker/

```java
@Override
public String getUrl(int requestId, CheckerInfo checkerInfo) {
  return URL;
}
```

##### Example with arguments - for given currency pair:
* API example: https://anxpro.com/main/stats?ccyPair=BTCUSD
* URL field: https://anxpro.com/main/stats?ccyPair=%1$s%2$s

```java
@Override
public String getUrl(int requestId, CheckerInfo checkerInfo) {
  return String.format(URL, checkerInfo.getCurrencyBase(), checkerInfo.getCurrencyCounter());
}
```


Note that currency names are always in uppercase; however, some APIs requires them to be in lowercase.

##### Example with lowercase currency parameters:
* API example: https://bter.com/api/1/ticker/btc_cny
* URL field: https://bter.com/api/1/ticker/%1$s_%2$s

```java
@Override
public String getUrl(int requestId, CheckerInfo checkerInfo) {
  return String.format(URL, checkerInfo.getCurrencyBaseLowerCase(), checkerInfo.getCurrencyCounterLowerCase());
}
```

#### 3a. Providing other parameters in URL (advanced):
Sometimes there is a need to include some kind of pair ID instead of just currency names. Please see [Cryptsy](dataModule/src/main/java/com/aneonex/bitcoinchecker/datamodule/model/market/Cryptsy.kt) as an example. There is a separate `CURRENCY_PAIRS_IDS` map that holds pair ids:

```java
[...]
CURRENCY_PAIRS_IDS.put("DMD_BTC", 72);
CURRENCY_PAIRS_IDS.put("DOGE_BTC", 132);
CURRENCY_PAIRS_IDS.put("DOGE_LTC", 135);
CURRENCY_PAIRS_IDS.put("DVC_BTC", 40);
[...]
```

While providing the URL, we need to obtain the proper ID that is associated with this pair:

##### Example for DOGE/BTC (id=132) on Cryptsy:
* API example: http://pubapi.cryptsy.com/api.php?method=singlemarketdata&marketid=132
* URL field: http://pubapi.cryptsy.com/api.php?method=singlemarketdata&marketid=%1$s

```java
@Override
public String getUrl(int requestId, CheckerInfo checkerInfo) {
  final String pairString = String.format("%1$s_%2$s", checkerInfo.getCurrencyBase(), checkerInfo.getCurrencyCounter());
  if(CURRENCY_PAIRS_IDS.containsKey(pairString))
    return String.format(URL, String.valueOf(CURRENCY_PAIRS_IDS.get(pairString)));
  return URL;
}
```

### 4. Parsing API response:
While parsing the response from the exchange you have to fill the fieds of [Ticker](dataModule/src/main/java/com/aneonex/bitcoinchecker/datamodule/model/Ticker.kt) object.
If the API response is just in plain JSON object, you can parse it in the parseTickerFromJsonObject method:

```java
@Override
protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerRecord) throws Exception {
  ticker.bid = jsonObject.getDouble("bid");
  ticker.ask = jsonObject.getDouble("ask");
  ticker.vol = jsonObject.getDouble("volume");
  ticker.high = jsonObject.getDouble("high");
  ticker.low = jsonObject.getDouble("low");
  ticker.last = jsonObject.getDouble("last");
  ticker.timestamp = jsonObject.getLong("timestamp");
}
```

__IMPORTANT:__ The ticker.last field is mandatory; the rest of the fields are optional.
__NOTE:__ Parsing the `timestamp` field (in millis) is not required. If omitted, Bitcoin Checker will fill it with `now` date. If you want to parse this information, please note that some exchanges provide time in different formats (like seconds or nanos) so you have to multiply or divide it to get the time in millis format. You can use `TimeUtils.NANOS_IN_MILLIS` or `TimeUtils.MILLIS_IN_SECOND` constants from [TimeUtils](dataModule/src/main/java/com/aneonex/bitcoinchecker/datamodule/util/TimeUtils.kt) for that.

#### 4a. Parsing non JSONObject responses (advanced):
Sometimes responses are more complicated than plain JSON, then you should use the `parseTicker` method. The default implementation try to parse received response as a `JSONObject`, but you can parse also other formats by overriding this method:

```java
protected void parseTicker(int requestId, String responseString, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
  parseTickerFromJsonObject(requestId, new JSONObject(responseString), ticker, checkerInfo);
}
```

Here you can find examples of usage:
* [Huobi](dataModule/src/main/java/com/aneonex/bitcoinchecker/datamodule/model/market/Huobi.kt): "almost" JSON object response, there is a need to trim some characters at the begining and at the end of the response
* [MintPal](dataModule/src/main/java/com/aneonex/bitcoinchecker/datamodule/model/market/MintPal.kt): JSON array response (instead of JSON object)
* [McxNOW](dataModule/src/main/java/com/aneonex/bitcoinchecker/datamodule/model/market/McxNOW.kt): XML based response

### 5. Parsing error (not required):
Sometimes an exchange is down but with some error message in their API (See [Crypto-Trade](dataModule/src/main/java/com/aneonex/bitcoinchecker/datamodule/model/market/CryptoTrade.kt) as an example). You can also handle this situation and display an error message directly from the exchange to the user. There are two methods related with it and they are designed in a similar way to parsing a normal response:

```java
protected String parseErrorFromJsonObject(int requestId, JSONObject jsonObject, CheckerInfo checkerInfo);
```
or if JSONObject is not suitable, you can override following method:
```java
protected String parseError(int requestId, String responseString, CheckerInfo checkerInfo);
```

### 6. Fetching currency pairs directly from exchange:
If there is any API (or other way) to obtain currency pairs directly from exchange (without need to update them manually) you should implement currency pairs fetching functionality instead of providing a static set of currency pairs.
See example on  [Basebit](dataModule/src/main/java/com/aneonex/bitcoinchecker/datamodule/model/market/Basebit.kt).
Because there are no static currency pairs defined - you should pass a `null` as a last argument in the constructor and do NOT initialize `CURRENCY_PAIRS` at all. You need to provide the url to fetch currency pairs instead:

```java
private final static String URL_CURRENCY_PAIRS = "http://pubapi.cryptsy.com/api.php?method=marketdatav2";

public SampleExchange() {
  super(NAME, TTS_NAME, null);  // <- null intead of CURRENCY_PAIRS map
}

[...]

@Override
public String getCurrencyPairsUrl(int requestId) {
  return URL_CURRENCY_PAIRS;
}
```
Then you need to do parsing in:

```java
protected void parseCurrencyPairsFromJsonObject(int requestId, JSONObject jsonObject, List<CurrencyPairInfo> pairs)
or
protected void parseCurrencyPairs(int requestId, String responseString, List<CurrencyPairInfo> pairs)
```

While parsing currency pairs you need to create [CurrencyPairInfo](dataModule/src/main/java/com/aneonex/bitcoinchecker/datamodule/model/CurrencyPairInfo.kt) and add it to `List<CurrencyPairInfo> pairs`. The last argument `pairId` is a unique pair id used on some exchanges. You can just pass `null` if there is no such thing on given exchange.

You can also use multiple requests to fetch currency pairs from exchange - it is described in section [Multiple requests while fetching currency pairs](# multiple-requests-while-fetching-currency-pairs).

### 7. Enabling exchange:
To enable a newly created exchange, you should add the corresponding line at the bottom of `MarketsConfig` file:

```java
static {
  [...]
  addMarket(new MyNewExchangeClass());
}
```

## Advanced things
### Multiple requests per exchange:
Some exchanges do not provide a nice ticker api with the all important information (bid, ask, vol, high, low, last), so there is a need to perform a few requests (for example 2) to acquire as much information as possible.
These requests will be performed in a sequense and a new price notification will appear when all of these requests are finished.
See the [Poloniex](dataModule/src/main/java/com/aneonex/bitcoinchecker/datamodule/model/market/Poloniex.kt) exchange as a good example. In order to perform 2 requests you have to override `getNumOfRequests` method:

```java
@Override
public int getNumOfRequests(CheckerInfo checkerRecord) {
  return 2;
}
```

Then make use of requestId variable passed to `getUrl` and `parseTickerFromJsonObject` methods.
`requestId` variable is incremented from `0` to `numOfRequests-1` for every new request made.
From the first request, we are able to obtain only the `last` price. We want to obtain also the `bid` and `ask` values, so we do another request for the orders list:

```java
@Override
public String getUrl(int requestId, CheckerInfo checkerInfo) {
  if(requestId==0)
    return URL;
  else
    return String.format(URL_ORDERS, checkerInfo.getCurrencyCounter(), checkerInfo.getCurrencyBase()); // Reversed currencies
}

@Override
protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
  if(requestId==0) {
    ticker.last = jsonObject.getDouble(checkerInfo.getCurrencyCounter()+"_"+checkerInfo.getCurrencyBase());  // Reversed currencies
  } else {
    ticker.bid = getFirstPriceFromOrder(jsonObject, "bids");
    ticker.ask = getFirstPriceFromOrder(jsonObject, "asks");
  }
}
```

### Multiple requests while fetching currency pairs
You can also use multiple requests support for fetching currency pairs from exchange. The implementation is almost identical - just override following method:

```java
@Override
public int getCurrencyPairsNumOfRequests() {
  return 2;
}
```
Then use the `requestId` argument in the same way as in previous section.

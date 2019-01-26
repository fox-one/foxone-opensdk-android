# Exchange

Exchange SDK 提供交易钱包功能，交易功能，币对行情功能，用户收藏功能，实时数据流订阅功能等

## 接入

* 依赖

```
implementation 'com.fox.one:ex:0.1.8'
```

## 功能

* **[ExAssetAPI](src/main/java/com/fox/one/ex/core/ExAssetAPI.kt)** 交易钱包

```
//获取钱包所有资产
getAssets

//获取某个资产详情
getAsset(assetId)

//获取账单列表
getSnapshots(..)

//获取某个账单详情
getSnapshotDetail(..)

//提现
withdraw

//转账
transfer
```

* **[ExchangeAPI](src/main/java/com/fox/one/ex/core/ExchangeAPI.kt)** 币对行情&交易功能

```
//获取所有交易对
getPairs

//获取该交易所支持的币
getAssets()

//获取K线数据
getKLine

//获取交易历史
getTradeHistory

//获取深度信息
getDepth

//获取24小时币对数据
get24Ticker

//获取所有币对的24小时数据
get24AllTicker

//下单
placeOrder

//撤单
cancelOrder

//获取订单
getOrders

//获取某个订单的详情
getOrderInfo

//获取某个订单的交易数据
getTradeInfoOfOrder

```

* **[ExFavAPI](src/main/java/com/fox/one/ex/core/ExFavAPI.kt)** 用户收藏功能

```
//获取收藏的币对
getFavPairs

//收藏
like

//取消收藏
dislike
```

* **[StreamDataManager](src/main/java/com/fox/one/ex/core/stream/StreamDataManager.kt)** 实时数据流

```
//定义K线数据
subscribe(observer: KLineStreamObserver)
//取消订阅K线数据
unsubscribe(klineRequest: KLineStreamReqBody) 

//订阅Ticker数据
subscribe(observer: TickerStreamObserver)
//取消订阅Ticker数据
unsubscribe(tickerRequest: TickerStreamReqBody)

//订阅所有币对Ticker数据
subscribe(observer: AllTickerStreamObserver)
//取消订阅所有Ticker数据
unsubscribe(allTickerRequest: AllTickerStreamReqBody)

//订阅深度数据
subscribe(observer: DepthStreamObserver)
//取消订阅深度数据
unsubscribe(depthRequest: DepthStreamReqBody)

//订阅深度数据
subscribe(observer: DepthLevelStreamObserver)
//取消订阅深度数据
unsubscribe(depthLevelRequest: DepthLevelStreamReqBody)

//订阅交易数据
subscribe(observer: TradeStreamObserver)
//取消订阅交易数据
unsubscribe(tradeRequest: TradeStreamReqBody)

//订阅订单数据
subscribe(observer: OrderStreamObserver)
//取消订阅订单数据
unsubscribe(orderRequest: OrderStreamReqBody)
```

* **[TradeSide](src/main/java/com/fox/one/ex/core/TradeSide.kt)** 交易方向，BID：买方，ASK：卖方
* **[TradeState](src/main/java/com/fox/one/ex/core/TradeState.kt)** 交易状态，PENDING：挂单中，DONE：交易完成
* **[TradeType](src/main/java/com/fox/one/ex/core/TradeType.kt)** 交易类型，LIMIT：限价交易，MARKET：市价交易

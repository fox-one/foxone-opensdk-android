# payment

payment SDK 提供数字资产的基本钱包功能，包括但不限于转账，提现，转账记录等

## 接入

* 依赖

```
implementation 'com.fox.one:pay:0.1.8'
```
查看[最新版本](http://jcenter.bintray.com/com/fox/one/ex-ui)

## 功能

* **[PayAPI](src/main/java/com/fox/one/pay/core/PayAPI.kt)**

```
//获取用户数字资产列表
getAssets

//获取某个数字资产详情
getAsset(assetId)

//获取账单列表
getSnapshots(..)

//获取账单详情
getSnapshotDetail(snapshotId)

//提现
withdraw(..)

//转账
transfer(..)
```

* **[CurrencyRateManager](src/main/java/com/fox/one/pay/core/rate/CurrencyRateManager.kt)** 汇率接口，注意所有法币和数字货币的汇率都是以人民币(CNY)为基准进行计算得出的

```
//获取对应symbol的汇率
getRateBaseCNY(symbol)

//美元汇率
usdRate

//USDT汇率
usdtRate

//usd价格换成人民币价格
usd2cny

//人民币价格换成美元价格
cny2usd

//USDT价格换成人民币价格
usdt2cny

//人民币换成USDT价格
cny2usdt

// xxx币换成人民币价格
toCNYValue

//人民币价格换成xxx币的价格
fromCNYValue

//获取法币的symbol
getLegalSymbol

//获取法币的字符图标
getLegalSign

```
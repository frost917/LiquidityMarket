package com.frost917.mcserver.market.market.itemManager

import com.frost917.mcserver.market.ItemData

object ItemValueManager {
    /*
     * 아이템 가격 책정 공식 ( DB 라이브러리의 문제로 Unsigned는 사용불가 )
     * marketValue = nowValue * totalQuantity
     * nowValue = marketValue / totalQuantity ( 소수 아래는 버림 )
     */
    fun calcItemValue(itemData: ItemData): SaleData {
        val marketValue = itemData.marketValue
        val totalQuantity = itemData.totalQuantity

        val nowValue = (marketValue / totalQuantity).toInt()

        val stackValue = if(totalQuantity < 64) {
            totalQuantity
        } else {
            64
        }

        return SaleData(itemData.material, nowValue, stackValue)
    }

    /*
     * 아이템 거래시 거래된 개수에 맞추어 ItemData 반환
     * 아이템 개수가 음수로 내려가는 문제는 MarketTrade 클래스에서 사전에 검열해야함
     */
    fun tradeItem(itemData: ItemData, tradeQuantity: Int): ItemData {
        return ItemData(itemData.material, itemData.marketValue, itemData.totalQuantity - tradeQuantity)
    }

    /*
     * 새로운 marketValue에 맞춰 totalQuantity를 변동하는 함수
     * nowValue를 고정한 상태로 newMarketValue에 맞춰 totalQuantity를 조정
     */
    fun revalueItem(itemData: ItemData, newMarketValue: Long): ItemData {
        val material = itemData.material
        val nowValue = calcItemValue(itemData).nowValue

        val totalQuantity = (newMarketValue / nowValue).toInt()

        return ItemData(material, newMarketValue, totalQuantity)
    }
}
package com.frost917.mcserver.market.market.inventory

import com.frost917.mcserver.market.ItemData
import com.frost917.mcserver.market.market.itemManager.ItemValueManager
import com.frost917.mcserver.market.market.itemManager.SaleData

object MarketListManager {
    // 아이템 리스트를 36개씩 쪼갬
    fun splitMarketList(itemList: List<ItemData>, pageNumber: Int): List<ItemData> {
        var newItemList = mutableListOf<ItemData>()
        var relIndex = 0

        for (index in 1..36) {
            // for 문에 사용되는 상대적 index를 실제 index로 변경
             relIndex = (index * pageNumber) - 1

            // 인덱스 값이 리스트 범위를 초과하는 경우 컷
            if(relIndex < itemList.count()) {
                break
            }
            newItemList.add(itemList[relIndex])
        }

        return newItemList
    }

    // List<ItemData>를 List<SaleData>로 변환
    fun convertItemToSale(itemList: List<ItemData>): List<SaleData> {
        var saleList = mutableListOf<SaleData>()
        itemList.forEach {
            saleList.add(ItemValueManager.calcItemValue(it))
        }

        return saleList
    }

    /*
    fun sortItemList(marketList: List<ItemSaleData>, sortType: MarketSortType) {
        // Default Sort Type
        if (sortType == MarketSortType.UPBYNAME) {
            var materialList = mutableListOf<String>()
            for(item in marketList.iterator()) {
                materialList.add(item.material.toString())
            }


        }
    }
     */
}
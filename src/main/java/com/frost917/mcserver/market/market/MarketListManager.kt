package com.frost917.mcserver.market.market

import com.frost917.mcserver.market.ItemData
import com.frost917.mcserver.market.Main
import com.frost917.mcserver.market.market.itemManager.ItemValueManager
import com.frost917.mcserver.market.market.itemManager.SaleData

object MarketListManager {
    private val stroage = Main.MainPlugin.getStorage()
    // TODO) 모든 마켓 인벤토리는 이 오브젝트에서 데이터 불러올 것
    // TODO) 거래 이벤트 발생시 마켓 리스트 갱신
    // 마켓 페이지 : 판매중인 아이템
    // 각 페이지 별로 48개씩 아이템 보관
    private val marketList = mutableMapOf<Int, List<SaleData>>()

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

    fun refreshMarketList() {

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
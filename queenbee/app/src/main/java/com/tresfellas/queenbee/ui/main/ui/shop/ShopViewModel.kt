package com.tresfellas.queenbee.ui.main.ui.shop

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tresfellas.queenbee.api.methods.RoyalJellyApi
import com.tresfellas.queenbee.data.model.*
import com.tresfellas.queenbee.utils.RxUtil

class ShopViewModel: ViewModel()  {

    val royalJellyApi = RoyalJellyApi()

    var isProcessBar = MutableLiveData<Boolean>()
    var royalJellyPurchaseHistoryDTO = MutableLiveData<RoyalJellyPurchaseHistoryDTO>()
    var errorStatus = MutableLiveData<Int>()
    var royalJellyPurchaseDTO = MutableLiveData<RoyalJellyPurchaseDTO>()

    var royalJellyPromotionsDTO = MutableLiveData<RoyalJellyPromotionsDTO>()

    fun getRoyalJellyPurchaseHistory(limit: Int, page : Int){
        isProcessBar.value = true
        royalJellyApi.getRoyalJellyPurchaseHistory(limit , page)
            .compose(RxUtil.applySingleSchedulers())
            .subscribe(
                {
                    println("@@@@@@@@@@@@@@@@@@$it")
                    royalJellyPurchaseHistoryDTO.value = it
                    isProcessBar.value = false
                },
                {
                    errorStatus.value = HttpErrorCode.fromThrowable(it).message
                    isProcessBar.value = false
                }
            )
    }

    fun purchaseRoyalJelly(amount : Int, cost : Int, recordType: String){
        isProcessBar.value = true
        val amountDTO = RoyalJellyPurchase(amount = amount, cost = cost, recordType = recordType)
        royalJellyApi.purchaseRoyalJelly(amountDTO)
            .compose(RxUtil.applySingleSchedulers())
            .subscribe(
                {
                    updateStateToComPlete(it)
                    royalJellyPurchaseDTO.value = it
                    isProcessBar.value = false
                },
                {
                    println("@@@@@@@@@@@@@@@@@@${it.localizedMessage}")
                    errorStatus.value = HttpErrorCode.fromThrowable(it).message
                    isProcessBar.value = false
                }
            )

    }

    fun updateStateToComPlete(royalJellyPurchaseDTO: RoyalJellyPurchaseDTO){
        when(royalJellyPurchaseDTO.newPurchase.recordType){
            "dailyLogin" -> royalJellyPromotionsDTO.value!!.promotions.dailyLogin.state = "completed"
            "dailyMoment" -> royalJellyPromotionsDTO.value!!.promotions.dailyMoment.state = "completed"
            "boldProfile" -> royalJellyPromotionsDTO.value!!.promotions.boldProfile.state = "completed"
            "firstMoment" -> royalJellyPromotionsDTO.value!!.promotions.firstMoment.state = "completed"
            "std" -> royalJellyPromotionsDTO.value!!.promotions.std.state = "completed"
            "hpv" -> royalJellyPromotionsDTO.value!!.promotions.hpv.state = "completed"
            "covid" -> royalJellyPromotionsDTO.value!!.promotions.covid.state = "completed"
        }

    }


    fun getMyRoyalJellyPromotions(){
        isProcessBar.value = true
        royalJellyApi.getMyRoyalJellyPromotions()
            .compose(RxUtil.applySingleSchedulers())
            .subscribe(
                {
                    println("@@@@@@@@@@RoyalJellyPromotionos$it")
                    royalJellyPromotionsDTO.value = it
                    isProcessBar.value = false
                },{
                    errorStatus.value = HttpErrorCode.fromThrowable(it).message
                    isProcessBar.value = false
                }
            )
    }

    private val _shopHistoryDTOList = MutableLiveData<List<ShopHistoryDTO>>().apply {
        value = listOf(ShopHistoryDTO("2021.09.21","로얄젤리 1,000ml", "₩1,000"),
            ShopHistoryDTO("2021.09.21","로얄젤리 1,000ml", "₩1,000"),
            ShopHistoryDTO("2021.09.21","로얄젤리 1,000ml", "₩1,000"),
            ShopHistoryDTO("2021.09.21","로얄젤리 1,000ml", "₩1,000"),
            ShopHistoryDTO("2021.09.21","로얄젤리 1,000ml", "₩1,000"),
            ShopHistoryDTO("2021.09.21","로얄젤리 1,000ml", "₩1,000"),
            ShopHistoryDTO("2021.09.21","로얄젤리 1,000ml", "₩1,000"),
            ShopHistoryDTO("2021.09.21","로얄젤리 1,000ml", "₩1,000"),
            ShopHistoryDTO("2021.09.21","로얄젤리 1,000ml", "₩1,000"),
            ShopHistoryDTO("2021.09.21","로얄젤리 1,000ml", "₩1,000"),
            ShopHistoryDTO("2021.09.21","로얄젤리 1,000ml", "₩1,000"),
            ShopHistoryDTO("2021.09.21","로얄젤리 1,000ml", "₩1,000"),
            ShopHistoryDTO("2021.09.21","로얄젤리 1,000ml", "₩1,000"),
            ShopHistoryDTO("2021.09.21","로얄젤리 1,000ml", "₩1,000"))

    }
    val list: LiveData<List<ShopHistoryDTO>> = _shopHistoryDTOList

}
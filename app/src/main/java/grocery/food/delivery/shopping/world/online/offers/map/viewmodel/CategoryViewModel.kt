package grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.all
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.data.DataFactory
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.data.DataService
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.model.AllAppsModel

class CategoryViewModel : ViewModel() {
    var foodallAppsLiveData: MutableLiveData<AllAppsModel> = MutableLiveData()
    var foodcarouselImagesLiveData: MutableLiveData<AllAppsModel> = MutableLiveData()
    var foodtrendingLiveData: MutableLiveData<AllAppsModel> = MutableLiveData()


    private var context: Context? = null
    var compositeDisposable: CompositeDisposable? = null

    fun loadData(){
        Log.d("TAG", "loadData: ")
        compositeDisposable = CompositeDisposable()
        fetchCarouselImagesfood()
        fetchTrendingDatafood()
        fetchAllAppsfood()
    }

    private fun fetchAllAppsfood(){
        Log.d("TAG", "fetchAllApps: ")
        val all: all? = all.get()
        val dataService: DataService? = all!!.getDataService()

        val disposable: Disposable?
        disposable = dataService?.fetchAllApps(DataFactory().URL_ALL_APPS_FOOD,DataFactory().KEY)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.doOnError(Consumer { t ->
                Log.d("fetchAll", "fetchAllApps Error ${t.localizedMessage}")
            })
            ?.subscribe(Consumer { t ->
                Log.d("fetchAll", "fetchAllApps Response ${t.getValues()}")
                changeAllAppsDataSet(t)
            })

        if (disposable != null) {
            compositeDisposable?.add(disposable)
        }
    }

    private fun fetchCarouselImagesfood(){
        Log.d("TAG", "fetchCarouselImages: ")

        val dataService by lazy {
            DataFactory.create()
        }

        val disposable: Disposable?
        disposable = dataService?.fetchAllApps(DataFactory().URL_CAROUSEL_FOOD,DataFactory().KEY)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.doOnError(Consumer { t ->
                Log.d("TAG", "fetchCarouselImages Error ${t.localizedMessage}")
            })
            ?.subscribe(Consumer { t ->
                Log.d("TAG", "fetchCarouselImages Response ${t.getValues()}")
                changeCarouselDataSet(t)
            })

        if (disposable != null) {
            compositeDisposable?.add(disposable)
        }
    }

    private fun fetchTrendingDatafood(){
        Log.d("TAG", "fetchTrendingData: ")

        val dataService by lazy {
            DataFactory.create()
        }

        val disposable: Disposable?
        disposable = dataService?.fetchAllApps(DataFactory().URL_Trending_FOOD,DataFactory().KEY)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.doOnError(Consumer { t ->
                Log.d("TAG", "fetchTrendingData Error ${t.localizedMessage}")
            })
            ?.subscribe(Consumer { t ->
                Log.d("TAG", "fetchTrendingData Response ${t.getValues()}")
                changeTrendingDataSet(t)
            })

        if (disposable != null) {
            compositeDisposable?.add(disposable)
        }
    }


    fun changeAllAppsDataSet(allAppsList: AllAppsModel){
        foodallAppsLiveData.value = allAppsList
    }

    fun changeCarouselDataSet(carouselList: AllAppsModel){
        foodcarouselImagesLiveData.value = carouselList
    }

    fun changeTrendingDataSet(trendingList: AllAppsModel){
        foodtrendingLiveData.value = trendingList
    }


    private fun unSubscribeFromObservable() {
        if (compositeDisposable != null && !compositeDisposable!!.isDisposed) {
            compositeDisposable!!.dispose()
        }
    }

    fun reset() {
        unSubscribeFromObservable()
        compositeDisposable = null
        context = null
    }

}
//    var superMartLiveData: MutableLiveData<List<List<String>>?> = MutableLiveData()
//    var groceriesLiveData: MutableLiveData<List<List<String>>?> = MutableLiveData()
//    var medicinesLiveData: MutableLiveData<List<List<String>>?> = MutableLiveData()
//    var supplementsLiveData: MutableLiveData<List<List<String>>?> = MutableLiveData()
//    var electronicsLiveData: MutableLiveData<List<List<String>>?> = MutableLiveData()
//    var beautyLiveData: MutableLiveData<List<List<String>>?> = MutableLiveData()
//    var jewelleryLiveData: MutableLiveData<List<List<String>>?> = MutableLiveData()
//    var kitchenAppliancesLiveData: MutableLiveData<List<List<String>>?> = MutableLiveData()
//    var kidsLifestyleLiveData: MutableLiveData<List<List<String>>?> = MutableLiveData()
//    var babyToysLiveData: MutableLiveData<List<List<String>>?> = MutableLiveData()
//    var lingerieLiveData: MutableLiveData<List<List<String>>?> = MutableLiveData()
//    var menInnerWearLiveData: MutableLiveData<List<List<String>>?> = MutableLiveData()
//    var booksLiveData: MutableLiveData<List<List<String>>?> = MutableLiveData()
//    var footwearLiveData: MutableLiveData<List<List<String>>?> = MutableLiveData()
//    private var context: Context? = null
//    var compositeDisposable: CompositeDisposable? = null
//
//    fun loadData(){
//        Log.d("TAG", "loadData: ")
//        compositeDisposable = CompositeDisposable()
//        fetchSuperMart()
//        fetchGroceries()
//        fetchMedicines()
//        fetchSupplements()
//        fetchElectronics()
//        fetchBeauty()
//        fetchJewellery()
//        fetchKitchenAppliances()
//        fetchKidsLifestyle()
//        fetchBabyToys()
//        fetchLingerie()
//        fetchMenInnerWear()
//        fetchBooks()
//        fetchFootwear()
//    }
//
//    private fun fetchSuperMart(){
//        Log.d("TAG", "fetchSuperMart: ")
//        val singleton: Singleton? = Singleton.get()
//        val dataService: DataService? = singleton!!.getDataService()
//
//
//        val disposable: Disposable?
//        disposable = dataService?.fetchAllApps(DataFactory().URL_SUPER_MART, DataFactory().KEY)
//            ?.subscribeOn(Schedulers.io())
//            ?.observeOn(AndroidSchedulers.mainThread())
//            ?.doOnError(Consumer { t ->
//                Log.d("TAG", "fetchSuperMart Error ${t.localizedMessage}")
//            })
//            ?.subscribe(Consumer { t ->
//                Log.d("TAG", "fetchSuperMart Response ${t.getValues()}")
//                changeSuperMartDataSet(t.getValues())
//            })
//
//        if (disposable != null) {
//            compositeDisposable?.add(disposable)
//        }
//    }
//
//    private fun fetchGroceries(){
//        Log.d("TAG", "fetchSuperMart: ")
//
//        val singleton: Singleton? = Singleton.get()
//        val dataService: DataService? = singleton!!.getDataService()
//
//        val disposable: Disposable?
//        disposable = dataService?.fetchAllApps(DataFactory().URL_GROCERIES, DataFactory().KEY)
//            ?.subscribeOn(Schedulers.io())
//            ?.observeOn(AndroidSchedulers.mainThread())
//            ?.doOnError(Consumer { t ->
//                Log.d("TAG", "fetchGroceries Error ${t.localizedMessage}")
//            })
//            ?.subscribe(Consumer { t ->
//                Log.d("TAG", "fetchGroceries Response ${t.getValues()}")
//                changeGroceriesDataSet(t.getValues())
//            })
//
//        if (disposable != null) {
//            compositeDisposable?.add(disposable)
//        }
//    }
//
//    private fun fetchMedicines(){
//        Log.d("TAG", "fetchMedicines: ")
//
//        val singleton: Singleton? = Singleton.get()
//        val dataService: DataService? = singleton!!.getDataService()
//
//        val disposable: Disposable?
//        disposable = dataService?.fetchAllApps(DataFactory().URL_MEDICINES, DataFactory().KEY)
//            ?.subscribeOn(Schedulers.io())
//            ?.observeOn(AndroidSchedulers.mainThread())
//            ?.doOnError(Consumer { t ->
//                Log.d("TAG", "fetchMedicines Error ${t.localizedMessage}")
//            })
//            ?.subscribe(Consumer { t ->
//                Log.d("TAG", "fetchMedicines Response ${t.getValues()}")
//                changeMedicinesDataSet(t.getValues())
//            })
//
//        if (disposable != null) {
//            compositeDisposable?.add(disposable)
//        }
//    }
//
//    private fun fetchSupplements(){
//        Log.d("TAG", "fetchSupplements: ")
//
//        val singleton: Singleton? = Singleton.get()
//        val dataService: DataService? = singleton!!.getDataService()
//
//        val disposable: Disposable?
//        disposable = dataService?.fetchAllApps(DataFactory().URL_SUPPLEMENTS, DataFactory().KEY)
//            ?.subscribeOn(Schedulers.io())
//            ?.observeOn(AndroidSchedulers.mainThread())
//            ?.doOnError(Consumer { t ->
//                Log.d("TAG", "fetchSupplements Error ${t.localizedMessage}")
//            })
//            ?.subscribe(Consumer { t ->
//                Log.d("TAG", "fetchSupplements Response ${t.getValues()}")
//                changeSupplementsDataSet(t.getValues())
//            })
//
//        if (disposable != null) {
//            compositeDisposable?.add(disposable)
//        }
//    }
//
//    private fun fetchElectronics(){
//        Log.d("TAG", "fetchElectronics: ")
//
//        val singleton: Singleton? = Singleton.get()
//        val dataService: DataService? = singleton!!.getDataService()
//
//        val disposable: Disposable?
//        disposable = dataService?.fetchAllApps(DataFactory().URL_ELECTRONICS, DataFactory().KEY)
//            ?.subscribeOn(Schedulers.io())
//            ?.observeOn(AndroidSchedulers.mainThread())
//            ?.doOnError(Consumer { t ->
//                Log.d("TAG", "fetchElectronics Error ${t.localizedMessage}")
//            })
//            ?.subscribe(Consumer { t ->
//                Log.d("TAG", "fetchElectronics Response ${t.getValues()}")
//                changeElectronicsDataSet(t.getValues())
//            })
//
//        if (disposable != null) {
//            compositeDisposable?.add(disposable)
//        }
//    }
//
//    private fun fetchBeauty(){
//        Log.d("TAG", "fetchBeauty: ")
//
//        val singleton: Singleton? = Singleton.get()
//        val dataService: DataService? = singleton!!.getDataService()
//
//        val disposable: Disposable?
//        disposable = dataService?.fetchAllApps(DataFactory().URL_BEAUTY, DataFactory().KEY)
//            ?.subscribeOn(Schedulers.io())
//            ?.observeOn(AndroidSchedulers.mainThread())
//            ?.doOnError(Consumer { t ->
//                Log.d("TAG", "fetchBeauty Error ${t.localizedMessage}")
//            })
//            ?.subscribe(Consumer { t ->
//                Log.d("TAG", "fetchBeauty Response ${t.getValues()}")
//                changeBeautyDataSet(t.getValues())
//            })
//
//        if (disposable != null) {
//            compositeDisposable?.add(disposable)
//        }
//    }
//
//    private fun fetchJewellery(){
//        Log.d("TAG", "fetchJewellery: ")
//
//        val singleton: Singleton? = Singleton.get()
//        val dataService: DataService? = singleton!!.getDataService()
//
//        val disposable: Disposable?
//        disposable = dataService?.fetchAllApps(DataFactory().URL_JEWELLERY, DataFactory().KEY)
//            ?.subscribeOn(Schedulers.io())
//            ?.observeOn(AndroidSchedulers.mainThread())
//            ?.doOnError(Consumer { t ->
//                Log.d("TAG", "fetchJewellery Error ${t.localizedMessage}")
//            })
//            ?.subscribe(Consumer { t ->
//                Log.d("TAG", "fetchJewellery Response ${t.getValues()}")
//                changeJewelleryDataSet(t.getValues())
//            })
//
//        if (disposable != null) {
//            compositeDisposable?.add(disposable)
//        }
//    }
//
//    private fun fetchKitchenAppliances(){
//        Log.d("TAG", "fetchKitchenAppliances: ")
//
//        val singleton: Singleton? = Singleton.get()
//        val dataService: DataService? = singleton!!.getDataService()
//
//        val disposable: Disposable?
//        disposable = dataService?.fetchAllApps(
//            DataFactory().URL_KITCHEN_APPLIANCES,
//            DataFactory().KEY
//        )
//            ?.subscribeOn(Schedulers.io())
//            ?.observeOn(AndroidSchedulers.mainThread())
//            ?.doOnError(Consumer { t ->
//                Log.d("TAG", "fetchKitchenAppliances Error ${t.localizedMessage}")
//            })
//            ?.subscribe(Consumer { t ->
//                Log.d("TAG", "fetchKitchenAppliances Response ${t.getValues()}")
//                changeKitchenAppliancesDataSet(t.getValues())
//            })
//
//        if (disposable != null) {
//            compositeDisposable?.add(disposable)
//        }
//    }
//
//    private fun fetchKidsLifestyle(){
//        Log.d("TAG", "fetchKidsLifestyle: ")
//
//        val singleton: Singleton? = Singleton.get()
//        val dataService: DataService? = singleton!!.getDataService()
//
//        val disposable: Disposable?
//        disposable = dataService?.fetchAllApps(DataFactory().URL_KIDS_LIFESTYLE, DataFactory().KEY)
//            ?.subscribeOn(Schedulers.io())
//            ?.observeOn(AndroidSchedulers.mainThread())
//            ?.doOnError(Consumer { t ->
//                Log.d("TAG", "fetchKidsLifestyle Error ${t.localizedMessage}")
//            })
//            ?.subscribe(Consumer { t ->
//                Log.d("TAG", "fetchKidsLifestyle Response ${t.getValues()}")
//                changeKidsLifestyleDataSet(t.getValues())
//            })
//
//        if (disposable != null) {
//            compositeDisposable?.add(disposable)
//        }
//    }
//
//    private fun fetchBabyToys(){
//        Log.d("TAG", "fetchBabyToys: ")
//
//        val singleton: Singleton? = Singleton.get()
//        val dataService: DataService? = singleton!!.getDataService()
//
//        val disposable: Disposable?
//        disposable = dataService?.fetchAllApps(DataFactory().URL_BABY_TOYS, DataFactory().KEY)
//            ?.subscribeOn(Schedulers.io())
//            ?.observeOn(AndroidSchedulers.mainThread())
//            ?.doOnError(Consumer { t ->
//                Log.d("TAG", "fetchBabyToys Error ${t.localizedMessage}")
//            })
//            ?.subscribe(Consumer { t ->
//                Log.d("TAG", "fetchBabyToys Response ${t.getValues()}")
//                changeBabyToysDataSet(t.getValues())
//            })
//
//        if (disposable != null) {
//            compositeDisposable?.add(disposable)
//        }
//    }
//
//    private fun fetchLingerie(){
//        Log.d("TAG", "fetchLingerie: ")
//
//        val singleton: Singleton? = Singleton.get()
//        val dataService: DataService? = singleton!!.getDataService()
//
//        val disposable: Disposable?
//        disposable = dataService?.fetchAllApps(DataFactory().URL_LINGERIE, DataFactory().KEY)
//            ?.subscribeOn(Schedulers.io())
//            ?.observeOn(AndroidSchedulers.mainThread())
//            ?.doOnError(Consumer { t ->
//                Log.d("TAG", "fetchLingerie Error ${t.localizedMessage}")
//            })
//            ?.subscribe(Consumer { t ->
//                Log.d("TAG", "fetchLingerie Response ${t.getValues()}")
//                changeLingerieDataSet(t.getValues())
//            })
//
//        if (disposable != null) {
//            compositeDisposable?.add(disposable)
//        }
//    }
//
//    private fun fetchMenInnerWear(){
//        Log.d("TAG", "fetchMenInnerWear: ")
//
//        val singleton: Singleton? = Singleton.get()
//        val dataService: DataService? = singleton!!.getDataService()
//
//        val disposable: Disposable?
//        disposable = dataService?.fetchAllApps(DataFactory().URL_MEN_INNER_WEAR, DataFactory().KEY)
//            ?.subscribeOn(Schedulers.io())
//            ?.observeOn(AndroidSchedulers.mainThread())
//            ?.doOnError(Consumer { t ->
//                Log.d("TAG", "fetchMenInnerWear Error ${t.localizedMessage}")
//            })
//            ?.subscribe(Consumer { t ->
//                Log.d("TAG", "fetchMenInnerWear Response ${t.getValues()}")
//                changeMenInnerWearDataSet(t.getValues())
//            })
//
//        if (disposable != null) {
//            compositeDisposable?.add(disposable)
//        }
//    }
//
//    private fun fetchBooks(){
//        Log.d("TAG", "fetchBooks: ")
//
//        val singleton: Singleton? = Singleton.get()
//        val dataService: DataService? = singleton!!.getDataService()
//
//        val disposable: Disposable?
//        disposable = dataService?.fetchAllApps(DataFactory().URL_BOOKS, DataFactory().KEY)
//            ?.subscribeOn(Schedulers.io())
//            ?.observeOn(AndroidSchedulers.mainThread())
//            ?.doOnError(Consumer { t ->
//                Log.d("TAG", "fetchBooks Error ${t.localizedMessage}")
//            })
//            ?.subscribe(Consumer { t ->
//                Log.d("TAG", "fetchBooks Response ${t.getValues()}")
//                changeBooksDataSet(t.getValues())
//            })
//
//        if (disposable != null) {
//            compositeDisposable?.add(disposable)
//        }
//    }
//
//    private fun fetchFootwear(){
//        Log.d("TAG", "fetchFootwear: ")
//
//        val singleton: Singleton? = Singleton.get()
//        val dataService: DataService? = singleton!!.getDataService()
//
//        val disposable: Disposable?
//        disposable = dataService?.fetchAllApps(DataFactory().URL_FOOTWEAR, DataFactory().KEY)
//            ?.subscribeOn(Schedulers.io())
//            ?.observeOn(AndroidSchedulers.mainThread())
//            ?.doOnError(Consumer { t ->
//                Log.d("TAG", "fetchFootwear Error ${t.localizedMessage}")
//            })
//            ?.subscribe(Consumer { t ->
//                Log.d("TAG", "fetchFootwear Response ${t.getValues()}")
//                changeFootwearDataSet(t.getValues())
//            })
//
//        if (disposable != null) {
//            compositeDisposable?.add(disposable)
//        }
//    }
//
//
//
//
//    fun changeSuperMartDataSet(allAppsList: List<List<String>>?){
//        superMartLiveData.value = allAppsList
//    }
//
//    fun changeGroceriesDataSet(allAppsList: List<List<String>>?){
//        groceriesLiveData.value = allAppsList
//    }
//
//    fun changeMedicinesDataSet(allAppsList: List<List<String>>?){
//        medicinesLiveData.value = allAppsList
//    }
//
//    fun changeSupplementsDataSet(allAppsList: List<List<String>>?){
//        supplementsLiveData.value = allAppsList
//    }
//
//    fun changeElectronicsDataSet(allAppsList: List<List<String>>?){
//        electronicsLiveData.value = allAppsList
//    }
//
//    fun changeBeautyDataSet(allAppsList: List<List<String>>?){
//        beautyLiveData.value = allAppsList
//    }
//
//    fun changeJewelleryDataSet(allAppsList: List<List<String>>?){
//        jewelleryLiveData.value = allAppsList
//    }
//
//    fun changeKitchenAppliancesDataSet(allAppsList: List<List<String>>?){
//        kitchenAppliancesLiveData.value = allAppsList
//    }
//
//    fun changeKidsLifestyleDataSet(allAppsList: List<List<String>>?){
//        kidsLifestyleLiveData.value = allAppsList
//    }
//
//    fun changeBabyToysDataSet(allAppsList: List<List<String>>?){
//        babyToysLiveData.value = allAppsList
//    }
//
//    fun changeLingerieDataSet(allAppsList: List<List<String>>?){
//        lingerieLiveData.value = allAppsList
//    }
//    fun changeMenInnerWearDataSet(allAppsList: List<List<String>>?){
//        menInnerWearLiveData.value = allAppsList
//    }
//
//    fun changeBooksDataSet(allAppsList: List<List<String>>?){
//        booksLiveData.value = allAppsList
//    }
//
//    fun changeFootwearDataSet(allAppsList: List<List<String>>?){
//        footwearLiveData.value = allAppsList
//    }
//
//
//
//    private fun unSubscribeFromObservable() {
//        if (compositeDisposable != null && !compositeDisposable!!.isDisposed) {
//            compositeDisposable!!.dispose()
//        }
//    }
//
//    fun reset() {
//        unSubscribeFromObservable()
//        compositeDisposable = null
//        context = null
//    }
//
//

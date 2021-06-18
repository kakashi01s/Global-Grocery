package grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.all
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.data.DataFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.data.DataService
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.model.AllAppsModel

class DealsViewModel : ViewModel() {
    var shopallAppsLiveData: MutableLiveData<AllAppsModel> = MutableLiveData()
    var shopcarouselImagesLiveData: MutableLiveData<AllAppsModel> = MutableLiveData()
    var shoptrendingLiveData: MutableLiveData<AllAppsModel> = MutableLiveData()


    private var context: Context? = null
    var compositeDisposable: CompositeDisposable? = null

    fun loadData() {
        Log.d("TAG", "loadData: Deals ")
        compositeDisposable = CompositeDisposable()
        fetchCarouselImagesshop()
        fetchTrendingDatashop()
        fetchAllAppsshop()
    }

    private fun fetchAllAppsshop() {
        Log.d("TAG", "fetchAllApps: ")
        val all: all? = all.get()
        val dataService: DataService? = all!!.getDataService()

        val disposable: Disposable?
        disposable = dataService?.fetchAllApps(DataFactory().URL_ALL_APPS_SHOP, DataFactory().KEY)
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

    private fun fetchCarouselImagesshop() {
        Log.d("TAG", "fetchCarouselImages: ")

        val dataService by lazy {
            DataFactory.create()
        }

        val disposable: Disposable?
        disposable =
            dataService?.fetchAllApps(DataFactory().URL_CAROUSEL_SHOP, DataFactory().KEY)
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

    private fun fetchTrendingDatashop() {
        Log.d("TAG", "fetchTrendingData: ")

        val dataService by lazy {
            DataFactory.create()
        }

        val disposable: Disposable?
        disposable =
            dataService?.fetchAllApps(DataFactory().URL_Trending_SHOP, DataFactory().KEY)
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


    fun changeAllAppsDataSet(allAppsList: AllAppsModel) {
        shopallAppsLiveData.value = allAppsList
    }

    fun changeCarouselDataSet(carouselList: AllAppsModel) {
        shopcarouselImagesLiveData.value = carouselList
    }

    fun changeTrendingDataSet(trendingList: AllAppsModel) {
        shoptrendingLiveData.value = trendingList
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
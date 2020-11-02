package shopping.grocery.medicine.online.deals.coupons.compare.buy.data

import shopping.grocery.medicine.online.deals.coupons.compare.buy.model.AllAppsModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface DataService {
    @GET
    fun fetchAllApps(@Url url: String, @Query("key") key: String): Observable<AllAppsModel>

}
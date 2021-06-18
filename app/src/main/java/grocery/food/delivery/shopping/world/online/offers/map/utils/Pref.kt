package grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class Pref(private val context: Context?) {
    var preferences: SharedPreferences

    //    var mainBannerAds: Long
//        get() = preferences.getLong(MAIN_BANNER_ADS, 0)
//        set(mainBannerAds) {
//            preferences.edit().putLong(MAIN_BANNER_ADS, mainBannerAds)
//                .apply()
//        }
//
    var bookmarksData: String?
        get() {
            return preferences.getString(BOOKMARK_DATA, null)
        }
        set(interval) {
            preferences.edit().putString(BOOKMARK_DATA, interval)
                .apply()
        }

    var dataChangedDate: String?
        get() {
            return preferences.getString(DATA_CHANGED_DATE, "0")
        }
        set(interval) {
            preferences.edit().putString(DATA_CHANGED_DATE, interval)
                .apply()
        }

    var dataChanged: Boolean?
        get() {
            return preferences.getBoolean(DATA_CHANGED, false)
        }
        set(interval) {
            preferences.edit().putBoolean(DATA_CHANGED, interval!!)
                .apply()
        }

    //
//    var lastFetchedDate: Long
//        get() {
//            return preferences.getLong(
//                LAST_FETCHED_DATE,
//                System.currentTimeMillis()
//            )
//        }
//        set(interval) {
//            preferences.edit().putLong(LAST_FETCHED_DATE, interval)
//                .apply()
//        }
//
//    var apiStatus: String?
//        get() {
//            return preferences.getString(API_STATUS, null)
//        }
//        set(status) {
//            preferences.edit().putString(API_STATUS, status)
//                .apply()
//        }
//
//    var webBannerAds: Long
//        get() {
//            return preferences.getLong(WEB_BANNER_ADS, 0)
//        }
//        set(webBannerAds) {
//            preferences.edit().putLong(WEB_BANNER_ADS, webBannerAds)
//                .apply()
//        }
//
//    var webBannerShow: Boolean
//        get() {
//            return preferences.getBoolean(IS_WEB_BANNER_SHOW, true)
//        }
//        set(isWebBannerShow) {
//            preferences.edit().putBoolean(IS_WEB_BANNER_SHOW, isWebBannerShow)
//                .apply()
//        }
//
//    fun getViewPager(): Boolean {
//        return preferences.getBoolean(viewPager, true)
//    }
//
//    fun setViewPager(view_Pager: Boolean) {
//        preferences.edit().putBoolean(viewPager, view_Pager)
//            .apply()
//    }
//
//    var viewPager: String = "view"
    fun clearPref() {
        preferences.edit().clear().apply()
    }

    companion object {
        private var sInstance: Pref? = null
        private val BOOKMARK_DATA: String = "bookmark_data"
        private val DATA_CHANGED: String = "data_changed"
        private val DATA_CHANGED_DATE: String = "data_changed_date"

        @Synchronized
        fun initializeInstance(context: Context?) {
            if (sInstance == null) {
                sInstance = Pref(context)
            }
        }

        @get:Synchronized
        val instance: Pref?
            get() {
                if (sInstance == null) {
                    throw IllegalStateException(
                        Pref::class.java.simpleName +
                                " is not initialized, call initializeInstance(..) method first."
                    )
                }
                return sInstance
            }
    }

    init {
        preferences = PreferenceManager.getDefaultSharedPreferences(context)
    }
}

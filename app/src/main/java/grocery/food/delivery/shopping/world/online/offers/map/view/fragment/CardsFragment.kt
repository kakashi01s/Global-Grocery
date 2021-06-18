package grocery.food.delivery.shopping.world.online.offers.map.view.fragment

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.ads.*
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.R
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.base.BaseFragment
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.utils.Constants
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.view.MainActivity
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.view.WebActivity
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.view.adapter.CategoryStoresAdapter
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.view.listener.CategoryStoresItemClickListener
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.viewmodel.CategoryViewModel
import grocery.food.delivery.shopping.world.online.offers.map.viewmodel.CardViewModel
import kotlinx.android.synthetic.main.fragment_cards.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CardsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CardsFragment : BaseFragment(), CategoryStoresItemClickListener<List<String>> {
    // TODO: Rename and change types of parameters
    private var param1: Int? = null
    private var param2: String? = null


    var rvCategoryStores: RecyclerView? = null
    var categoryStoresAdapter: CategoryStoresAdapter? = null
    var cardViewModel: CardViewModel? = null

    var firebaseRemoteConfig: FirebaseRemoteConfig? = null
    var firebaseAnalytics: FirebaseAnalytics? = null

    private var nativeAdFB1: NativeAd? = null
    private var nativeAdFB2: NativeAd? = null
    private var nativeAdFB4: NativeAd? = null
    private var nativeAdLayout: NativeAdLayout? = null
    private var adView: LinearLayout? = null



    var superMartList: ArrayList<List<String>>? = ArrayList()
    var groceriesList: ArrayList<List<String>>? = ArrayList()
    var medicinesList: ArrayList<List<String>>? = ArrayList()
    var supplementsList: ArrayList<List<String>>? = ArrayList()
    var electronicsList: ArrayList<List<String>>? = ArrayList()
    var beautyList: ArrayList<List<String>>? = ArrayList()
    var jewelleryList: ArrayList<List<String>>? = ArrayList()
    var kitchenAppliancesList: ArrayList<List<String>>? = ArrayList()
    var kidsLifestyleList: ArrayList<List<String>>? = ArrayList()
    var babyToysList: ArrayList<List<String>>? = ArrayList()
    var lingerieList: ArrayList<List<String>>? = ArrayList()
    var menInnerWearList: ArrayList<List<String>>? = ArrayList()
    var booksList: ArrayList<List<String>>? = ArrayList()
    var footwearList: ArrayList<List<String>>? = ArrayList()


    var dialog: Dialog? = null
//
//    var nativeAdCat1: UnifiedNativeAd? = null
//    var nativeAdCat2: UnifiedNativeAd? = null

    override val bindingVariable: Int
        get() = 0
    override val layoutId: Int
        get() = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)

        if(firebaseRemoteConfig == null){
            firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        }

        dialog = Dialog(requireContext())
        dialog!!.setContentView(R.layout.dialog_show_stores)
        dialog!!.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT)

        cardViewModel = ViewModelProvider(requireActivity()).get(CardViewModel::class.java)
        cardViewModel?.loadData()

        cardViewModel!!.superMartLiveData.observe(viewLifecycleOwner, Observer { t ->
            Log.d("TAG", "onViewCreated: superMartLiveData $t")
            superMartList!!.addAll(t!!)
        })

        cardViewModel!!.groceriesLiveData.observe(viewLifecycleOwner, Observer { t ->
            Log.d("TAG", "onViewCreated: groceriesLiveData $t")
            groceriesList!!.addAll(t!!)
        })
        cardViewModel!!.medicinesLiveData.observe(viewLifecycleOwner, Observer { t ->
            Log.d("TAG", "onViewCreated: medicinesLiveData $t")
            medicinesList!!.addAll(t!!)
        })

        cardViewModel!!.supplementsLiveData.observe(viewLifecycleOwner, Observer { t ->
            Log.d("TAG", "onViewCreated: supplementsLiveData $t")
            supplementsList!!.addAll(t!!)
        })
        cardViewModel!!.electronicsLiveData.observe(viewLifecycleOwner, Observer { t ->
            Log.d("TAG", "onViewCreated: electronicsLiveData $t")
            electronicsList!!.addAll(t!!)
        })

        cardViewModel!!.beautyLiveData.observe(viewLifecycleOwner, Observer { t ->
            Log.d("TAG", "onViewCreated: beautyLiveData $t")
            beautyList!!.addAll(t!!)
        })
        cardViewModel!!.jewelleryLiveData.observe(viewLifecycleOwner, Observer { t ->
            Log.d("TAG", "onViewCreated: jewelleryLiveData $t")
            jewelleryList!!.addAll(t!!)
        })

        cardViewModel!!.kitchenAppliancesLiveData.observe(viewLifecycleOwner, Observer { t ->
                Log.d("TAG", "onViewCreated: kitchenAppliancesLiveData $t")
                kitchenAppliancesList!!.addAll(t!!)
            })
        cardViewModel!!.kidsLifestyleLiveData.observe(viewLifecycleOwner, Observer { t ->
            Log.d("TAG", "onViewCreated: kidsLifestyleLiveData $t")
            kidsLifestyleList!!.addAll(t!!)
        })

        cardViewModel!!.babyToysLiveData.observe(viewLifecycleOwner, Observer { t ->
            Log.d("TAG", "onViewCreated: babyToysLiveData $t")
            babyToysList!!.addAll(t!!)
        })
        cardViewModel!!.lingerieLiveData.observe(viewLifecycleOwner, Observer { t ->
            Log.d("TAG", "onViewCreated: lingerieLiveData $t")
            lingerieList!!.addAll(t!!)
        })

        cardViewModel!!.menInnerWearLiveData.observe(viewLifecycleOwner, Observer { t ->
            Log.d("TAG", "onViewCreated: menInnerWearLiveData $t")
            menInnerWearList!!.addAll(t!!)
        })
        cardViewModel!!.booksLiveData.observe(viewLifecycleOwner, Observer { t ->
            Log.d("TAG", "onViewCreated: booksLiveData $t")
            booksList!!.addAll(t!!)
        })

        cardViewModel!!.footwearLiveData.observe(viewLifecycleOwner, Observer { t ->
            Log.d("TAG", "onViewCreated: footwearLiveData $t")
            footwearList!!.addAll(t!!)
        })


        llSuperMarts.setOnClickListener {
            onShowStores(superMartList!!,view)
        }
        llGroceries.setOnClickListener {
            onShowStores(groceriesList!!,view)
        }
        llMedicines.setOnClickListener {
            onShowStores(medicinesList!!,view)
        }
        llSupplements.setOnClickListener {
            onShowStores(supplementsList!!,view)
        }
        llElectronics.setOnClickListener {
            onShowStores(electronicsList!!,view)
        }
        llBeauty.setOnClickListener {
            onShowStores(beautyList!!,view)
        }
        llJewellery.setOnClickListener {
            onShowStores(jewelleryList!!,view)
        }
        llKitchenAppliances.setOnClickListener {
            onShowStores(kitchenAppliancesList!!,view)
        }
        llKidsLifestyle.setOnClickListener {
            onShowStores(kidsLifestyleList!!,view)
        }
        llBabyToys.setOnClickListener {
            onShowStores(babyToysList!!,view)
        }
        llLingerie.setOnClickListener {
            onShowStores(lingerieList!!,view)
        }
        llMenInnerWear.setOnClickListener {
            onShowStores(menInnerWearList!!,view)
        }
        llBooks.setOnClickListener {
            onShowStores(booksList!!,view)
        }
        llFootwear.setOnClickListener {
            onShowStores(footwearList!!,view)
        }

    }
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if(isVisibleToUser){
            if(firebaseRemoteConfig == null){
                firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
            }
            if (firebaseRemoteConfig!!.getBoolean(Constants().SHOW_ADS)) {
                if(nativeAdFB1 == null){
                    onLoadFBNativeAd1(requireView(), requireContext())
                }
                if(nativeAdFB2 == null){
                    onLoadFBNativeAd2(requireView(), requireContext())
                }
                if(nativeAdFB4 == null){
                    onLoadFBNativeAdCatDailog(requireContext(), dialog!!)
                }
            }
        }
    }


    fun initViews(view: View){
        firebaseAnalytics = FirebaseAnalytics.getInstance(requireActivity())

    }

    fun setRecyclerView(){
        categoryStoresAdapter = CategoryStoresAdapter(context)
        categoryStoresAdapter!!.setListener(this)
        rvCategoryStores.apply {
            rvCategoryStores?.layoutManager = GridLayoutManager(activity, 3)
            rvCategoryStores?.adapter = categoryStoresAdapter
        }
    }

    fun onShowStores(list: ArrayList<List<String>>, view: View){
        rvCategoryStores = dialog!!.findViewById(R.id.rvCategoryStores)

        setRecyclerView()

        categoryStoresAdapter!!.setItems(list)

        dialog!!.show()

        dialog!!.setOnDismissListener {
            onLoadFBNativeAdCatDailog(requireContext(), dialog!!)
        }

    }
    fun onLoadFBNativeAd1(view: View, context: Context) {
        nativeAdFB1 = NativeAd(context, Constants().getFbNativeCat1())
        val nativeAdListener: NativeAdListener = object : NativeAdListener {
            override fun onError(p0: Ad?, p1: AdError?) {
                Log.d("TAG", "onError: onLoadFBNativeAd1 " + p1!!.errorMessage)
            }

            override fun onAdLoaded(ad: Ad?) {

                // Race condition, load() called again before last ad was displayed
                if (nativeAdFB1 == null || nativeAdFB1 !== ad) {
                    return
                }
                // Inflate Native Ad into Container

                // Add the Ad view into the ad container.
                nativeAdLayout = view.findViewById(R.id.native_ad_container_cat_1)
                val inflater = LayoutInflater.from(context)
                // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
                adView =
                    inflater.inflate(
                        R.layout.native_ad_layout,
                        nativeAdLayout,
                        false
                    ) as LinearLayout
                nativeAdLayout!!.addView(adView)

                inflateAd(nativeAdFB1!!, adView!!)

                val adChoicesContainer: LinearLayout = view.findViewById(R.id.ad_choices_container)
                val adOptionsView = AdOptionsView(context, nativeAdFB1, nativeAdLayout)
                adChoicesContainer.removeAllViews()
                adChoicesContainer.addView(adOptionsView, 0)
            }

            override fun onAdClicked(p0: Ad?) {
                Log.d("TAG", "onAdClicked: onLoadFBNativeAd1")
            }

            override fun onLoggingImpression(p0: Ad?) {
                Log.d("TAG", "onLoggingImpression: onLoadFBNativeAd1")
            }

            override fun onMediaDownloaded(p0: Ad?) {
                Log.d("TAG", "onMediaDownloaded: onLoadFBNativeAd1")
            }
        }

        nativeAdFB1!!.loadAd(
            nativeAdFB1!!.buildLoadAdConfig()
                .withAdListener(nativeAdListener)
                .build()
        );
    }
    fun onLoadFBNativeAd2(view: View, context: Context) {
        nativeAdFB2 = NativeAd(context, Constants().getFbNativeCat2())
        val nativeAdListener: NativeAdListener = object : NativeAdListener {
            override fun onError(p0: Ad?, p1: AdError?) {
                Log.d("TAG", "onError: onLoadFBNativeAd1 " + p1!!.errorMessage)
            }

            override fun onAdLoaded(ad: Ad?) {

                // Race condition, load() called again before last ad was displayed
                if (nativeAdFB2 == null || nativeAdFB2 !== ad) {
                    return
                }
                // Inflate Native Ad into Container

                // Add the Ad view into the ad container.
                nativeAdLayout = view.findViewById(R.id.native_ad_container_cat_2)
                val inflater = LayoutInflater.from(context)
                // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
                adView =
                    inflater.inflate(
                        R.layout.native_ad_layout,
                        nativeAdLayout,
                        false
                    ) as LinearLayout
                nativeAdLayout!!.addView(adView)

                inflateAd(nativeAdFB2!!, adView!!)

                val adChoicesContainer: LinearLayout = view.findViewById(R.id.ad_choices_container)
                val adOptionsView = AdOptionsView(context, nativeAdFB2, nativeAdLayout)
                adChoicesContainer.removeAllViews()
                adChoicesContainer.addView(adOptionsView, 0)
            }

            override fun onAdClicked(p0: Ad?) {
                Log.d("TAG", "onAdClicked: onLoadFBNativeAd1")
            }

            override fun onLoggingImpression(p0: Ad?) {
                Log.d("TAG", "onLoggingImpression: onLoadFBNativeAd1")
            }

            override fun onMediaDownloaded(p0: Ad?) {
                Log.d("TAG", "onMediaDownloaded: onLoadFBNativeAd1")
            }
        }

        nativeAdFB2!!.loadAd(
            nativeAdFB2!!.buildLoadAdConfig()
                .withAdListener(nativeAdListener)
                .build()
        )
    }

    fun onLoadFBNativeAdCatDailog(context: Context, dialog: Dialog) {
        nativeAdFB4 = NativeAd(context, Constants().getFbNativeCatDailog())
        val nativeAdListener: NativeAdListener = object : NativeAdListener {
            override fun onError(p0: Ad?, p1: AdError?) {
                Log.d("TAG", "onError: onLoadFBNativeAdCAT " + p1!!.errorMessage)
            }

            override fun onAdLoaded(ad: Ad?) {

                Log.d("TAG", "onAdLoaded: onLoadFBNativeAdCAT")

                // Race condition, load() called again before last ad was displayed
                if (nativeAdFB4 == null || nativeAdFB4 !== ad) {
                    return
                }
                // Inflate Native Ad into Container

                // Add the Ad view into the ad container.
                nativeAdLayout = dialog.findViewById(R.id.native_ad_container_dailog)
                val inflater = LayoutInflater.from(context)
                // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
                adView =
                    inflater.inflate(
                        R.layout.native_ad_layout,
                        nativeAdLayout,
                        false
                    ) as LinearLayout
                nativeAdLayout!!.addView(adView)

                inflateAd(nativeAdFB4!!, adView!!)

                val adChoicesContainer: LinearLayout = dialog.findViewById(R.id.ad_choices_container)
                val adOptionsView = AdOptionsView(context, nativeAdFB4, nativeAdLayout)
                adChoicesContainer.removeAllViews()
                adChoicesContainer.addView(adOptionsView, 0)
            }

            override fun onAdClicked(p0: Ad?) {
                Log.d("TAG", "onAdClicked: onLoadFBNativeAdCAT")
            }

            override fun onLoggingImpression(p0: Ad?) {
                Log.d("TAG", "onLoggingImpression: onLoadFBNativeAdCAT")
            }

            override fun onMediaDownloaded(p0: Ad?) {
                Log.d("TAG", "onMediaDownloaded: onLoadFBNativeAdCAT")
            }
        }

        nativeAdFB4!!.loadAd(
            nativeAdFB4!!.buildLoadAdConfig()
                .withAdListener(nativeAdListener)
                .build()
        )
    }


    private fun inflateAd(nativeAd: NativeAd, adView: LinearLayout) {
        nativeAd.unregisterView()

        // Add the AdOptionsView

        // Create native UI using the ad metadata.
        val nativeAdIcon: com.facebook.ads.MediaView = adView.findViewById(R.id.native_ad_icon)
        val nativeAdTitle: TextView = adView.findViewById(R.id.native_ad_title)
        val nativeAdMedia: com.facebook.ads.MediaView = adView.findViewById(R.id.native_ad_media)
        val nativeAdSocialContext: TextView = adView.findViewById(R.id.native_ad_social_context)
        val nativeAdBody: TextView = adView.findViewById(R.id.native_ad_body)
        val sponsoredLabel: TextView = adView.findViewById(R.id.native_ad_sponsored_label)
        val nativeAdCallToAction: Button = adView.findViewById(R.id.native_ad_call_to_action)

        // Set the Text.
        nativeAdTitle.text = nativeAd.advertiserName
        nativeAdBody.text = nativeAd.adBodyText
        nativeAdSocialContext.text = nativeAd.adSocialContext
        nativeAdCallToAction.visibility =
            if (nativeAd.hasCallToAction()) View.VISIBLE else View.INVISIBLE
        nativeAdCallToAction.text = nativeAd.adCallToAction
        sponsoredLabel.text = nativeAd.sponsoredTranslation

        // Create a list of clickable views
        val clickableViews: ArrayList<View> = ArrayList()
        clickableViews.add(nativeAdTitle)
        clickableViews.add(nativeAdCallToAction)

        // Register the Title and CTA button to listen for clicks.
        nativeAd.registerViewForInteraction(
            adView, nativeAdMedia, nativeAdIcon, clickableViews
        )
    }



    override fun onDestroy() {
        cardViewModel?.reset()
        super.onDestroy()
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CategoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Int, param2: String) =
            CardsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun CategoryStoresCardClick(item: List<String>) {
        Log.d("TAG", "onAllBrokersCardClick: " + item.get(1))

        val bundle = Bundle()
        bundle.putString("title", item.get(1))
        bundle.putString("url", item.get(2))
        (activity as MainActivity?)!!.onUpdateLogEvent(bundle,"brokers_visited",true)

        val intent: Intent? = Intent(activity, WebActivity::class.java)
        intent?.putExtra("title", item.get(1))
        intent?.putExtra("url", item.get(2))
        startActivity(intent)
    }
}
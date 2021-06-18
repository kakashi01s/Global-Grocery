package grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.view.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.facebook.ads.*
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.gson.Gson
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageClickListener
import com.synnapps.carouselview.ImageListener
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.R
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.base.BaseFragment
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.model.AllAppsModel
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.utils.Constants
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.utils.Pref
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.view.MainActivity
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.view.WebActivity
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.view.adapter.home.AllAppsAdapter
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.view.adapter.home.TrendingAdapter
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.view.listener.AllAppsItemClickListener
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.view.listener.home.TrendingItemClickListener
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.viewmodel.HomeViewModel
import java.io.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentHome.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentHome : BaseFragment(), AllAppsItemClickListener<List<String>>,
    TrendingItemClickListener<List<String>> {
    // TODO: Rename and change types of parameters
    private var param1: Int? = null
    private var param2: String? = null

    var carouselView: CarouselView? = null

    var rvAllApps: RecyclerView? = null
    var allAppsAdapter: AllAppsAdapter? = null
    var homeViewModel: HomeViewModel? = null

    var rvTrending: RecyclerView? = null
    var trendingAdapter: TrendingAdapter? = null

    var firebaseRemoteConfig: FirebaseRemoteConfig? = null
    var firebaseAnalytics: FirebaseAnalytics? = null

    private var nativeAdFB1: NativeAd? = null
    private var nativeAdFB2: NativeAd? = null
    private var nativeAdLayout: NativeAdLayout? = null
    private var adView: LinearLayout? = null

    lateinit var search: ImageView

    var carouselImagesList: ArrayList<List<String>>? = ArrayList()
//
//    var nativeAdHome1: UnifiedNativeAd? = null
//    var nativeAdHome2: UnifiedNativeAd? = null

    override val bindingVariable: Int
        get() = 0
    override val layoutId: Int
        get() = 0
    var bool: Boolean = false
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
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)

        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

        setRecyclerView()

        Pref.initializeInstance(context)

        homeViewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

        if (!Pref.instance!!.dataChanged!!) {


            val isAllAppsFilePresent: Boolean =
                isFilePresent(requireContext(), Constants().ALL_APPS_STORAGE_FILE_NAME)
            Log.d("Karan", isAllAppsFilePresent.toString())
            if (isAllAppsFilePresent) {
                Log.d("TAG", "onChanged: isAllAppsFilePresent isFilePresent $isAllAppsFilePresent")
                val allAppsModel: AllAppsModel =
                    read(requireContext(), Constants().ALL_APPS_STORAGE_FILE_NAME)!!
                Log.d(
                    "TAG",
                    "onChanged: isAllAppsFilePresent jsonData " + Gson().toJson(allAppsModel)
                )
                allAppsAdapter?.setItems(allAppsModel.getValues())
                Log.d("Karan", allAppsModel.getValues().toString())
            }

            val isCarouselFilePresent: Boolean =
                isFilePresent(requireContext(), Constants().CAROUSEL_VIEW_STORAGE_FILE_NAME)
            if (isCarouselFilePresent) {
                Log.d(
                    "TAG",
                    "onChanged: isCarouselFilePresent isFilePresent $isCarouselFilePresent"
                )
                val allAppsModel: AllAppsModel =
                    read(requireContext(), Constants().CAROUSEL_VIEW_STORAGE_FILE_NAME)!!
                Log.d(
                    "TAG",
                    "onChanged: isCarouselFilePresent jsonData " + Gson().toJson(allAppsModel)
                )
                carouselImagesList!!.addAll(allAppsModel.getValues()!!)
                onLoadCarouselImages()
            }

            val isTrendingFilePresent: Boolean =
                isFilePresent(requireContext(), Constants().TRENDING_STORAGE_FILE_NAME)
            if (isTrendingFilePresent) {
                Log.d(
                    "TAG",
                    "onChanged: isTrendingFilePresent isFilePresent $isTrendingFilePresent"
                )
                val allAppsModel: AllAppsModel =
                    read(requireContext(), Constants().TRENDING_STORAGE_FILE_NAME)!!
                Log.d(
                    "TAG",
                    "onChanged: isTrendingFilePresent jsonData " + Gson().toJson(allAppsModel)
                )
                trendingAdapter?.setItems(allAppsModel.getValues())
            }
        }

        homeViewModel?.loadData()

        homeViewModel!!.allAppsLiveData.observe(viewLifecycleOwner, Observer { t ->
            Log.d("TAG", "HomeFragment Live allAppsLiveData$t")
            if (Pref.instance!!.dataChanged!!) {
                updateAllAppsJsonFile(t)
                allAppsAdapter?.setItems(t.getValues())
            }
        })

        homeViewModel!!.carouselImagesLiveData.observe(viewLifecycleOwner, Observer { t ->
            Log.d("TAG", "HomeFragment Live carousel $t")
            if (Pref.instance!!.dataChanged!!) {
                updateCarouselJsonFile(t)
                carouselImagesList!!.addAll(t.getValues()!!)
                onLoadCarouselImages()
            }
        })

        homeViewModel!!.trendingLiveData.observe(viewLifecycleOwner, Observer { t ->
            Log.d("TAG", "HomeFragment Live trendingLiveData $t")
            if (Pref.instance!!.dataChanged!!) {
                updateTrendingJsonFile(t)
                trendingAdapter!!.setItems(t.getValues())
            }
        })


        if (firebaseRemoteConfig!!.getBoolean(Constants().SHOW_ADS)) {
            onLoadFBNativeAd1(view, requireContext())
        }
    }

    fun initViews(view: View) {
        firebaseAnalytics = FirebaseAnalytics.getInstance(requireActivity())
        carouselView = view.findViewById(R.id.cvHome)
        rvAllApps = view.findViewById(R.id.rvAllApps)
        rvTrending = view.findViewById(R.id.rvTrending)
    }

    fun setRecyclerView() {
        allAppsAdapter = AllAppsAdapter(context)
        allAppsAdapter!!.setListener(this)
        rvAllApps.apply {
            rvAllApps?.layoutManager = GridLayoutManager(activity, 4)
            rvAllApps?.adapter = allAppsAdapter
        }

        trendingAdapter = TrendingAdapter(context)
        trendingAdapter!!.setListener(this)
        rvTrending.apply {
            rvTrending?.layoutManager = LinearLayoutManager(
                activity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            rvTrending?.adapter = trendingAdapter
        }

    }

    fun onLoadCarouselImages() {
        Log.d("TAG", "onLoadCarouselImages: " + carouselImagesList!!.size)

        carouselView!!.setImageListener(imageListener)

        carouselView!!.setImageClickListener(imageClickListener)

        carouselView!!.pageCount = carouselImagesList!!.size

    }

    var imageClickListener: ImageClickListener = ImageClickListener { position ->
        val intent: Intent = Intent(activity, WebActivity::class.java)
        intent.putExtra("title", carouselImagesList!![position][1])
        intent.putExtra("url", carouselImagesList!![position][2])
        intent.putExtra("app_icon", carouselImagesList!![position][4])
        intent.putExtra("color", "#666666")

        val bundle = Bundle()
        bundle.putString("title", carouselImagesList!![position][1])
        bundle.putString("url", carouselImagesList!![position][2])

        (activity as MainActivity?)!!.onUpdateLogEvent(bundle, "carousel_images_visited", true)


        startActivity(intent)
    }


    var imageListener: ImageListener = ImageListener { position, imageView ->
        imageView.scaleType = ImageView.ScaleType.FIT_XY
        Log.d(
            "TAG",
            "onLoadCarouselImages: position " + position + " data " + carouselImagesList!![position][3]
        )
        Glide.with(imageView.context)
            .load(carouselImagesList!![position][3])
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }

    fun onLoadFBNativeAd1(view: View, context: Context) {
        nativeAdFB1 = NativeAd(context, Constants().getFbNativeHome1())
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
                nativeAdLayout = view.findViewById(R.id.native_ad_container_home_1)
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
        homeViewModel?.reset()
        Log.d("TAG", "onDestroy: ")
        super.onDestroy()
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentHome.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Int, param2: String) =
            FragmentHome().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onAllCardClick(item: List<String>) {
        Log.d("TAG", "onAllCardClick: " + item.get(1))
        val intent: Intent? = Intent(activity, WebActivity::class.java)
        intent?.putExtra("title", item.get(1))
        intent?.putExtra("url", item.get(2))
        intent?.putExtra("app_icon", item.get(3))

        val bundle = Bundle()
        bundle.putString("title", item.get(1))
        bundle.putString("url", item.get(2))

        (activity as MainActivity?)!!.onUpdateLogEvent(bundle, "all_apps_visited", true)


        startActivity(intent)
    }

    override fun onTrendingClickListener(item: List<String>) {
        val intent: Intent? = Intent(activity, WebActivity::class.java)
        intent?.putExtra("title", item.get(1))
        intent?.putExtra("url", item.get(2))
        intent?.putExtra("app_icon", item.get(4))

        val bundle = Bundle()
        bundle.putString("title", item.get(1))
        bundle.putString("url", item.get(2))

        (activity as MainActivity?)!!.onUpdateLogEvent(bundle, "trending_visited", true)


        startActivity(intent)
    }

    fun isFilePresent(context: Context, fileName: String): Boolean {
        val path = context.filesDir.absolutePath + "/" + fileName
        val file = File(path)
        return file.exists()
    }

    fun read(context: Context, fileName: String?): AllAppsModel? {
        var allAppsModel: AllAppsModel? = null
        try {
            val fileInputStream = context.openFileInput(fileName)
            val objectInputStream = ObjectInputStream(fileInputStream)
            allAppsModel = objectInputStream.readObject() as AllAppsModel
            Log.d("TAG", "read: " + Gson().toJson(allAppsModel))
            objectInputStream.close()
            fileInputStream.close()
        } catch (fileNotFoundException: IOException) {
            Log.d("TAG", "read: exception " + fileNotFoundException.localizedMessage)
            fileNotFoundException.printStackTrace()
        } catch (fileNotFoundException: ClassNotFoundException) {
            Log.d("TAG", "read: exception " + fileNotFoundException.localizedMessage)
            fileNotFoundException.printStackTrace()
        }
        return allAppsModel
    }

    fun updateAllAppsJsonFile(allAppsModel: AllAppsModel?) {
        var fileOutputStream: FileOutputStream? = null
        try {
            fileOutputStream =
                requireContext().openFileOutput(
                    Constants().ALL_APPS_STORAGE_FILE_NAME,
                    Context.MODE_PRIVATE
                )
            val objectOutputStream = ObjectOutputStream(fileOutputStream)
            objectOutputStream.flush()
            objectOutputStream.writeObject(allAppsModel)
            objectOutputStream.close()
            fileOutputStream.close()
        } catch (fileNotFoundException: IOException) {
            Log.d("TAG", "accept: allApps exception " + fileNotFoundException.message)
            fileNotFoundException.printStackTrace()
        }
    }

    fun updateCarouselJsonFile(allAppsModel: AllAppsModel?) {
        var fileOutputStream: FileOutputStream? = null
        try {
            fileOutputStream =
                requireContext().openFileOutput(
                    Constants().CAROUSEL_VIEW_STORAGE_FILE_NAME,
                    Context.MODE_PRIVATE
                )
            val objectOutputStream = ObjectOutputStream(fileOutputStream)
            objectOutputStream.flush()
            objectOutputStream.writeObject(allAppsModel)
            objectOutputStream.close()
            fileOutputStream.close()
        } catch (fileNotFoundException: IOException) {
            Log.d(
                "TAG",
                "accept: updateCarouselJsonFile exception " + fileNotFoundException.message
            )
            fileNotFoundException.printStackTrace()
        }
    }

    fun updateTrendingJsonFile(allAppsModel: AllAppsModel?) {
        var fileOutputStream: FileOutputStream? = null
        try {
            fileOutputStream =
                requireContext().openFileOutput(
                    Constants().TRENDING_STORAGE_FILE_NAME,
                    Context.MODE_PRIVATE
                )
            val objectOutputStream = ObjectOutputStream(fileOutputStream)
            objectOutputStream.flush()
            objectOutputStream.writeObject(allAppsModel)
            objectOutputStream.close()
            fileOutputStream.close()
        } catch (fileNotFoundException: IOException) {
            Log.d(
                "TAG",
                "accept: updateTrendingJsonFile exception " + fileNotFoundException.message
            )
            fileNotFoundException.printStackTrace()
        }
    }


}

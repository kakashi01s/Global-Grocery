package grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.view.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.R
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.model.bookmark.Bookmarks
import grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.view.fragment.BookmarkFragment

class BookmarkAdapter(
    var context: Context,
    var list: ArrayList<Bookmarks>,
    var frag: BookmarkFragment
) : RecyclerView.Adapter<BookmarkAdapter.SwipeViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SwipeViewHolder {
        val m = LayoutInflater.from(parent.context).inflate(R.layout.bookmark_layout, parent, false)
        return SwipeViewHolder(m)
    }

    override fun onBindViewHolder(holder: SwipeViewHolder, position: Int) {

        var bookmarks: Bookmarks = list[position]

        holder.text.text = bookmarks.bookmarkTitle
        holder.url.text = bookmarks.bookmarkUrl

        Glide.with(context).load(bookmarks.bookmarkLogo).into(holder.image)

    }

    override fun getItemCount(): Int {
        Log.d("TAG", "getItemCount: bookmarks" + list.size)
        return list.size
    }


    class SwipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val image: ImageView = itemView.findViewById(R.id.bkmark_image)
        val text: TextView = itemView.findViewById(R.id.bkmark_title)
        val url: TextView = itemView.findViewById(R.id.bkmark_url)
        val left: LinearLayout = itemView.findViewById(R.id.rowBG)

    }

    @JvmName("setList1")
    fun setList(list: ArrayList<Bookmarks>) {
        if (this.list.isNotEmpty()) {
            this.list.clear()
        }
        this.list.addAll(list)
    }

}
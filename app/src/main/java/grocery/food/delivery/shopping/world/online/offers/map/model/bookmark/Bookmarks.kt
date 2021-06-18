package grocery.food.delivery.shopping.world.online.offers.buy.deals.medicine.model.bookmark

class Bookmarks {
     var bookmarkTitle: String? = null
     var bookmarkUrl: String? = null

    var webSplash: String? = null

    @JvmName("isDelete1")
    fun isDelete(): Boolean {
        return isDelete
    }

    @JvmName("setDelete1")
    fun setDelete(delete: Boolean) {
        isDelete = delete
    }

    var isDelete = false

    @JvmName("getBookmarkUrlWithoutAffiliate1")
    fun getBookmarkUrlWithoutAffiliate(): String? {
        return bookmarkUrlWithoutAffiliate
    }

    @JvmName("setBookmarkUrlWithoutAffiliate1")
    fun setBookmarkUrlWithoutAffiliate(bookmarkUrlWithoutAffiliate: String?) {
        this.bookmarkUrlWithoutAffiliate = bookmarkUrlWithoutAffiliate
    }

    var bookmarkUrlWithoutAffiliate: String? = null

    @JvmName("getBookmarkLogo1")
    fun getBookmarkLogo(): String? {
        return bookmarkLogo
    }

    @JvmName("setBookmarkLogo1")
    fun setBookmarkLogo(bookmarkLogo: String?) {
        this.bookmarkLogo = bookmarkLogo
    }

    var bookmarkLogo: String? = null

    @JvmName("getBookmarkTitle1")
    fun getBookmarkTitle(): String? {
        return bookmarkTitle
    }

    @JvmName("setBookmarkTitle1")
    fun setBookmarkTitle(bookmarkTitle: String?) {
        this.bookmarkTitle = bookmarkTitle
    }

    @JvmName("getBookmarkUrl1")
    fun getBookmarkUrl(): String? {
        return bookmarkUrl
    }

    @JvmName("setBookmarkUrl1")
    fun setBookmarkUrl(bookmarkUrl: String?) {
        this.bookmarkUrl = bookmarkUrl
    }

    @JvmName("getBookmarkStoreTitle1")
    fun getBookmarkStoreTitle(): String? {
        return bookmarkStoreTitle
    }

    @JvmName("setBookmarkStoreTitle1")
    fun setBookmarkStoreTitle(bookmarkStoreTitle: String?) {
        this.bookmarkStoreTitle = bookmarkStoreTitle
    }

    var bookmarkStoreTitle: String? = null
}
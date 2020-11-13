package id.toriqwah.project.model

import com.google.firebase.database.Exclude
import com.google.gson.annotations.SerializedName

data class Order (
    @SerializedName("uid")
    var uid: String = "",
    @SerializedName("id")
    var id: Long? = null,
    @SerializedName("id_tenant")
    var id_tenant: Long? = null,
    @SerializedName("payment_method")
    var payment_method: String = "",
    @SerializedName("order_type")
    var order_type: String = "",
    @SerializedName("location")
    var location: String = "",
    @SerializedName("by")
    var by: String = "",
    @SerializedName("date")
    var date: String = "",
    @SerializedName("status")
    var status: String = "",
    @SerializedName("total_price")
    var total_price: Long? = null,
    @SerializedName("menu")
    var menu: ArrayList<List>? = null
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "id" to id,
            "id_tenant" to id_tenant,
            "payment_method" to payment_method,
            "order_type" to order_type,
            "location" to location,
            "by" to by,
            "date" to date,
            "status" to status,
            "total_price" to total_price,
            "menu" to menu
        )
    }
}

data class List (
    @SerializedName("id")
    var id: Long? = null,
    @SerializedName("name")
    var name: String = "",
    @SerializedName("quantity")
    var quantity: Long? = null,
    @SerializedName("price")
    var price: Long? = null
)
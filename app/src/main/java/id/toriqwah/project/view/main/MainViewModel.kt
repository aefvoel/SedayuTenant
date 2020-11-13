package id.toriqwah.project.view.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import id.toriqwah.project.model.Menu
import id.toriqwah.project.model.Order
import id.toriqwah.project.model.Tenant
import id.toriqwah.project.util.SingleLiveEvent
import id.toriqwah.project.view.base.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel(){

    /**
     * Login
     */

    private val database = FirebaseDatabase.getInstance()
    val username = MutableLiveData<String>()
    val orderId = MutableLiveData<Long>()
    val orderSuccess = SingleLiveEvent<Unit>()

    val listTenant = MutableLiveData<ArrayList<Tenant>>()
    val listOrder = MutableLiveData<ArrayList<Order>>()

    val listMenu = MutableLiveData<ArrayList<Menu>>()
    val clickOrder = SingleLiveEvent<Unit>()
    val clickProceed = SingleLiveEvent<Unit>()


    fun onClickOrder(){
        clickOrder.call()
    }
    fun onClickProceed(){
        clickProceed.call()
    }

    fun getDataTenant(child: String) {
        isLoading.value = true
        viewModelScope.launch {
            val tenantListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    isLoading.value = false
                    val data = arrayListOf<Tenant>()
                    for (tenant in dataSnapshot.children){
                        data.add(tenant.getValue(Tenant::class.java)!!)
                        Log.d("tenant", tenant.toString())
                    }
                    listTenant.value = data
                    Log.d("tenant", listTenant.value.toString())
                }


                override fun onCancelled(error: DatabaseError) {
                    isLoading.value = false
                }

            }
            database.reference.child(child).addListenerForSingleValueEvent(tenantListener)
        }
    }

    fun getListOrder() {
        isLoading.value = true
        viewModelScope.launch {
            val tenantListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    isLoading.value = false
                    val data = arrayListOf<Order>()
                    for (tenant in dataSnapshot.children){
                        data.add(tenant.getValue(Order::class.java)!!)
                        Log.d("tenant", tenant.toString())
                    }
                    listOrder.value = data
                    Log.d("tenant", listTenant.value.toString())
                }


                override fun onCancelled(error: DatabaseError) {
                    isLoading.value = false
                }

            }
            database.reference.child("order").addListenerForSingleValueEvent(tenantListener)
        }
    }

    fun updateOrder(order: Order){
        isLoading.value = true
        viewModelScope.launch {
            val orderValues = order.toMap()

            val childUpdates = hashMapOf<String, Any>(
                "/order/${order.uid}" to orderValues,
            )
            database.reference.updateChildren(childUpdates)
                .addOnSuccessListener {
                    isLoading.value = false
                    orderSuccess.call()
                }
                .addOnFailureListener {
                    isLoading.value = false
                }
        }
    }

    fun pushOrder(order: Order){
        isLoading.value = true
        viewModelScope.launch {
            val key = database.reference.child("order").push().key
            if (key != null) {
                database.reference.child("order").child(key).setValue(order)
                    .addOnSuccessListener {
                        isLoading.value = false
                        orderSuccess.call()
                    }
                    .addOnFailureListener {
                        isLoading.value = false
                    }
            }
        }
    }

    fun getDataOrder(child: String) {
        viewModelScope.launch {
            val orderListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (order in dataSnapshot.children){
                        orderId.value = order.child("id").value as Long?
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            }
            database.reference.child(child).orderByKey().limitToLast(1).addListenerForSingleValueEvent(orderListener)
        }

    }
}
package id.toriqwah.project.view.main

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import id.toriqwah.project.R
import id.toriqwah.project.adapter.TenantAdapter
import id.toriqwah.project.databinding.ActivityMainBinding
import id.toriqwah.project.helper.UtilityHelper
import id.toriqwah.project.model.List
import id.toriqwah.project.model.Menu
import id.toriqwah.project.model.Order
import id.toriqwah.project.model.Tenant
import id.toriqwah.project.util.AppPreference
import id.toriqwah.project.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity(), TenantAdapter.Listener {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by inject<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.vm = viewModel
        binding.lifecycleOwner = this

        with(viewModel) {
            hideKeyBoard.observe(this@MainActivity, {
                UtilityHelper.hideSoftKeyboard(this@MainActivity)
            })
            snackbarMessage.observe(this@MainActivity, {
                when (it) {
                    is Int -> UtilityHelper.snackbarLong(view_parent, getString(it))
                    is String -> UtilityHelper.snackbarLong(view_parent, it)
                }
            })
            networkError.observe(this@MainActivity, {
                UtilityHelper.snackbarLong(view_parent, getString(R.string.error_network))
            })
            isLoading.observe(this@MainActivity, { bool ->
                bool.let { loading ->
                    if(loading){ showWaitingDialog() }
                    else { hideWaitingDialog() }
                }
            })
            listOrder.observe(this@MainActivity, {
                setListOrder(it)
            })
        }
        setView()
    }
    private fun setView(){
        viewModel.getListOrder()
        setToolbar("List Order")
        toolbar_back.visibility = View.GONE
    }
    private fun setListOrder(list: ArrayList<Order>) {
        rv_tenant.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = TenantAdapter(this@MainActivity, list, this@MainActivity)
        }
    }

    override fun onItemClicked(data: Order) {
        AppPreference.putOrder(data)
        Log.d("menu", data.toString())
        val intent = Intent(this, OrderActivity::class.java)
        startActivity(intent)
    }
}
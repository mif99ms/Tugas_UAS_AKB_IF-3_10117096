package com.Miftah.UAS_10117096.activity

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.Miftah.UAS_10117096.R
import com.Miftah.UAS_10117096.adapter.WisataAdapter
import com.Miftah.UAS_10117096.model.WisataModel
import com.Miftah.UAS_10117096.utils.FirestoreUtils
import com.Miftah.UAS_10117096.utils.Utils
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import java.util.*

//NIM : 10117096
//Nama : Miftah Muhammad Sidqi
//Kelas : IF-3
//Waktu Pengerjaam : 11/08/2020
class MainActivity : AppCompatActivity() {

    lateinit var pDialog : ProgressDialog
    private lateinit var listenerRegistration: ListenerRegistration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        wisataList = WisataData.list()
//        initRecyclerView()
        pDialog = ProgressDialog(this)

        pDialog.setMessage("Load data lokasi...")
        pDialog.setCancelable(false)
        pDialog.show()
        listenerRegistration = FirestoreUtils.getPlace{
            if (it.size > 0)
                updateRecyclerView(it)
            else
                if (pDialog.isShowing) pDialog.dismiss()
        }
    }

    private fun updateRecyclerView(list: ArrayList<WisataModel>) {
        Log.d(Utils.FIRE_LOG(),list.size.toString())

        val wistaAdapter = WisataAdapter(this, list)
        rv_wisata.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = wistaAdapter
        }
        if (pDialog.isShowing) pDialog.dismiss()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_info -> {
                startActivity<ContactActivity>()
                return true
            }
            R.id.action_add_place -> {
                startActivity<AddPlaceActivity>()
                return true
            }
            R.id.action_search -> {
                startActivity<SearchActivity>()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

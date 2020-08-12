package com.Miftah.UAS_10117096.activity

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.Miftah.UAS_10117096.R
import com.Miftah.UAS_10117096.adapter.WisataAdapter
import com.Miftah.UAS_10117096.model.WisataModel
import com.Miftah.UAS_10117096.utils.FirestoreUtils
import com.Miftah.UAS_10117096.utils.Utils
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast
import java.util.*


//NIM : 10117096
//Nama : Miftah Muhammad Sidqi
//Kelas : IF-3
//Waktu Pengerjaam : 11/08/2020
class SearchActivity : AppCompatActivity() {
    lateinit var pDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        title = "Cari lokasi"

        pDialog = ProgressDialog(this)

        btn_search.onClick {
            pDialog.setMessage("Sedang mencari...")
            pDialog.show()
            FirestoreUtils.searchLokasi(this@SearchActivity,et_search.text.toString()) {
//                toast(""+it.size)
                if (it.size > 0)
                    updateRecyclerView(it)
                else
                    toast("Tidak ada lokasi yang di temukan")
                pDialog.dismiss()
            }
        }
    }

    private fun updateRecyclerView(list: ArrayList<WisataModel>) {
        Log.d(Utils.FIRE_LOG(),list.size.toString())

        val wistaAdapter = WisataAdapter(this, list)
        rv_wisata.apply {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = wistaAdapter
        }
        if (pDialog.isShowing) pDialog.dismiss()
    }
}

package com.Miftah.UAS_10117096.utils

import android.content.Context
import android.util.Log
import com.Miftah.UAS_10117096.utils.Utils
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.Miftah.UAS_10117096.model.WisataFirestoreModel
import com.Miftah.UAS_10117096.model.WisataModel
import org.jetbrains.anko.toast


//NIM : 10117096
//Nama : Miftah Muhammad Sidqi
//Kelas : IF-3
//Waktu Pengerjaam : 11/08/2020

object FirestoreUtils {
    private val firestoreIntance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    private val mPlace = firestoreIntance.collection("place")

    fun getPlace(onComplete: (ArrayList<WisataModel>) -> Unit) : ListenerRegistration {
        return mPlace.addSnapshotListener{ querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null ) {
                Log.e(Utils.FIRE_LOG(),"Listener error")
                onComplete(ArrayList<WisataModel>())
                return@addSnapshotListener
            }

            val item = ArrayList<WisataModel>()

            if (querySnapshot!!.size() > 0) {
                querySnapshot!!.forEach{
                    item.add(WisataModel(it.id, it.toObject(WisataFirestoreModel::class.java)))
                    onComplete(item)
                }
            } else {
                onComplete(item)
            }
        }
    }

    fun storePlace(context: Context, res: WisataFirestoreModel, onComplete: (id: String) -> Unit){
        mPlace.add(res)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(Utils.FIRE_LOG(), "Add place success")
                        onComplete(task.result.id)
                    }
                }
                .addOnFailureListener {
                    context.toast("Gagal menambah lokasi : "+it.toString())
                    Log.d(Utils.FIRE_LOG(), "Add place Failed : "+it.toString())
                }
                .addOnCanceledListener {
                    context.toast("Batal menambah lokasi")
                    Log.d(Utils.FIRE_LOG(), "Add place Cancel")
                }
    }

    fun searchLokasi(context: Context, keyword: String, onComplete: (ArrayList<WisataModel>) -> Unit) {
        mPlace.get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val item = ArrayList<WisataModel>()
                        Log.d(Utils.FIRE_LOG(),"Keyword : "+keyword)

                        //SEARCH ON ARRAY
                        task.result.forEach {
                            val judul = it["nama"].toString().split(" ")
                            judul.forEach { j ->
                                val nama = j.toLowerCase().replace(",","")
                                Log.d(Utils.FIRE_LOG(),""+nama)

                                if (nama.equals(keyword)) {
                                    item.add(WisataModel(it.id, it.toObject(WisataFirestoreModel::class.java)))
                                }
                            }
                        }

                        onComplete(item)
                    }
                }
    }

    fun getLokasiDetail(id: String, onComplete: (task: DocumentSnapshot) -> Unit) {
        mPlace.document(id)
                .get()
                .addOnCompleteListener {task: Task<DocumentSnapshot> ->
                    val lokasi = WisataFirestoreModel()
                    if (task.isSuccessful) {
                        onComplete(task.result)
                    }
                }
    }
}
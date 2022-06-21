package com.example.uas_dpm_ilham_117

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ListView

class MainActivity : AppCompatActivity() {
    private lateinit var adpDosen: DataDosen
    private lateinit var DtPengampu: ArrayList<Lecturer>
    private lateinit var lvMataKuliah: ListView
    private lateinit var linTidakAda: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnTambah = findViewById<Button>(R.id.btnTambah)
        lvMataKuliah = findViewById(R.id.lvMataKuliah)
        linTidakAda = findViewById(R.id.linTidakAda)

        DtPengampu = ArrayList()
        adpDosen = DataDosen(this@MainActivity, DtPengampu)

        lvMataKuliah.adapter =adpDosen

        refresh()

        btnTambah.setOnClickListener {
            val i = Intent(this@MainActivity, EntriDataDosen::class.java)
            startActivity(i)
        }
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if(hasFocus) refresh()
    }

    private fun refresh(){
        val db = Campuss(this@MainActivity)
        val data = db.tampil()
        repeat(DtPengampu.size) { DtPengampu.removeFirst()}
        if(data.count > 0 ){
            while(data.moveToNext()){
                val matkul = Lecturer(
                    data.getString(0),
                    data.getString(1),
                    data.getString(2),
                    data.getString(3),
                    data.getString(4),
                    data.getString(5),
                    data.getString(6)
                )
                adpDosen.add(matkul)
                adpDosen.notifyDataSetChanged()
            }
            lvMataKuliah.visibility = View.VISIBLE
            linTidakAda.visibility  = View.GONE
        } else {
            lvMataKuliah.visibility = View.GONE
            linTidakAda.visibility = View.VISIBLE
        }
    }
}
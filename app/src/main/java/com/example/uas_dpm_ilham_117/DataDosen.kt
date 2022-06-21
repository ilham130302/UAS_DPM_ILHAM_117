package com.example.uas_dpm_ilham_117

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

class DataDosen  (
    private val getContext: Context,
    private val customListItem: ArrayList<Lecturer>
): ArrayAdapter<Lecturer>(getContext, 0, customListItem) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listLayout = convertView
        val holder: ViewHolder
        if (listLayout == null) {
            val infLateList = (getContext as Activity).layoutInflater
            listLayout = infLateList.inflate(R.layout.activity_item, parent, false)
            holder = ViewHolder()
            with(holder) {
                tvNmDosen = listLayout.findViewById(R.id.tvNmDosen)
                tvnidn = listLayout.findViewById(R.id.tvnidn)
                tvStudi = listLayout.findViewById(R.id.tvStudi)
                btnEdit = listLayout.findViewById(R.id.btnEdit)
                btnHapus = listLayout.findViewById(R.id.btnHapus)
            }
            listLayout.tag = holder
        }else
            holder = listLayout.tag as ViewHolder
        val listItem = customListItem[position]
        holder.tvNmDosen!!.setText(listItem.Nama_Dosen)
        holder.tvnidn!!.setText(listItem.NIDN)
        holder.tvStudi!!.setText(listItem.Program_Studi)

        holder.btnEdit!!.setOnClickListener{
            val i = Intent(context, EntriDataDosen::class.java)
            i.putExtra("nidn", listItem.NIDN)
            i.putExtra("nama", listItem.Nama_Dosen)
            i.putExtra("jabatan", listItem.Jabatan)
            i.putExtra("golpat", listItem.Golongan_Pangkat)
            i.putExtra("pendidikan", listItem.Pendidikan)
            i.putExtra("keahlian", listItem.Keahlian)
            i.putExtra("studi", listItem.Program_Studi)
            context.startActivity(i)
        }
        holder.btnHapus!!.setOnClickListener {
            val db = Campuss(context)
            val alb = AlertDialog.Builder(context)
            val nidn = holder.tvnidn!!.text
            val nama = holder.tvNmDosen!!.text
            val studi = holder.tvStudi!!.text

            with(alb){
                setTitle("Konfirmasi Penghapusan")
                setCancelable(false)
                setMessage("""
                        Apakah Anda yakin akan menghapus ini?
                                
                                $nama[$nidn][$studi]
                                """.trimIndent())
                setPositiveButton("Ya"){ _, _ ->
                    if(db.hapus("$nidn"))
                        Toast.makeText(
                            context,
                            "Data mata kuliah berhasil dihapus",
                            Toast.LENGTH_SHORT
                        ).show()
                    else
                        Toast.makeText(
                            context,
                            "Data mata kuliah gagal dihapus",
                            Toast.LENGTH_SHORT
                        ).show()
                }
                setNegativeButton("Tidak", null)
                create().show()
            }
        }

        return listLayout!!
    }

    class ViewHolder {
        internal var tvNmDosen: TextView? = null
        internal var tvnidn: TextView? = null
        internal var tvStudi:TextView? = null
        internal var btnEdit: ImageButton? = null
        internal var btnHapus: ImageButton? = null

    }
}
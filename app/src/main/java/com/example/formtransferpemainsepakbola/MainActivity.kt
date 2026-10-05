package com.example.formtransferpemainsepakbola

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ListView
import android.widget.RadioButton
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val daftarRekap = ArrayList<String>()
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Hubungkan variabel dengan widget lewat id
        val etNamaKlub = findViewById<EditText>(R.id.etNamaKlub)
        val spPemain = findViewById<Spinner>(R.id.spPemain)
        val rbPinjaman = findViewById<RadioButton>(R.id.rbPinjaman)
        val cbBonusGol = findViewById<CheckBox>(R.id.cbBonusGol)
        val cbAgen = findViewById<CheckBox>(R.id.cbAgen)
        val btnBeli = findViewById<Button>(R.id.btnBeli)
        val lvRekap = findViewById<ListView>(R.id.lvRekap)

        // Ambil data dari strings.xml
        val namaPemain = resources.getStringArray(R.array.pemain_array)
        val hargaPemain = resources.getIntArray(R.array.harga_array)

        // ListView butuh Adapter karena isinya berubah-ubah
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, daftarRekap)
        lvRekap.adapter = adapter

        btnBeli.setOnClickListener {
            val klub = etNamaKlub.text.toString().trim()

            // Validasi input
            if (klub.isEmpty()) {
                etNamaKlub.error = "Nama klub wajib diisi"
                return@setOnClickListener
            }

            val posisi = spPemain.selectedItemPosition
            var total = hargaPemain[posisi].toDouble()

            // Jenis transfer
            val jenis = if (rbPinjaman.isChecked) {
                total *= 0.2
                "Pinjaman (20% harga)"
            } else {
                "Permanen"
            }

            // Klausul tambahan
            val klausul = ArrayList<String>()
            if (cbBonusGol.isChecked) {
                total += 2.0
                klausul.add("Bonus Gol")
            }
            if (cbAgen.isChecked) {
                total += 1.5
                klausul.add("Biaya Agen")
            }
            val teksKlausul = if (klausul.isEmpty()) "-" else klausul.joinToString(", ")

            val rekap = "Klub: $klub\n" +
                    "Pemain: ${namaPemain[posisi]}\n" +
                    "Transfer: $jenis\n" +
                    "Klausul: $teksKlausul\n" +
                    "Total: €$total juta"

            // Tambah di paling atas, lalu refresh ListView
            daftarRekap.add(0, rekap)
            adapter.notifyDataSetChanged()

            Toast.makeText(this, "${namaPemain[posisi]} berhasil dibeli!", Toast.LENGTH_SHORT).show()
            etNamaKlub.text.clear()
        }
    }
}
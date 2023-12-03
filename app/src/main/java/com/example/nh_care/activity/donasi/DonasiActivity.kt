package com.example.nh_care.activity.donasi

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.lifecycleScope
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.nh_care.activity.MainActivity
import com.example.nh_care.database.DbContract
import com.example.nh_care.databinding.ActivityDonasiBinding
import com.midtrans.sdk.corekit.core.PaymentMethod
import com.midtrans.sdk.uikit.api.model.SnapTransactionDetail
import com.midtrans.sdk.uikit.api.model.TransactionResult
import com.midtrans.sdk.uikit.external.UiKitApi
import com.midtrans.sdk.uikit.internal.util.UiKitConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.HashMap
import java.util.UUID

class DonasiActivity : AppCompatActivity() {

    private lateinit var mbinding: ActivityDonasiBinding
    private var random = "NH-${UUID.randomUUID()}".substring(0, 8)

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result?.resultCode == RESULT_OK) {
                result.data?.let {
                    val transactionResult =
                        it.getParcelableExtra<TransactionResult>(UiKitConstants.KEY_TRANSACTION_RESULT)
//                    Toast.makeText(this, "${transactionResult?.transactionId}", Toast.LENGTH_LONG)
//                        .show()
//                    Toast.makeText(this, "Order ID: ${initTransactionDetails().orderId}", Toast.LENGTH_LONG)
//                        .show()
                    checkOrderStatus(initTransactionDetails().orderId)
                }
            }
        }

    private fun initTransactionDetails(): SnapTransactionDetail {
        val amountText = mbinding.layNominal.text.toString()
        val grossAmount = amountText.toDoubleOrNull() ?: 0.0

        return SnapTransactionDetail(
            orderId = random,
            grossAmount = grossAmount
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding = ActivityDonasiBinding.inflate(layoutInflater)
        setContentView(mbinding.root)
        buildUiKit()


        val sharedPreferences = getSharedPreferences("donatur_prefs", Context.MODE_PRIVATE)
        val namaDonatur: String? = sharedPreferences.getString("nama", "")
        val emailDonatur: String? = sharedPreferences.getString("email", "")
        val noHpDonatur: String? = sharedPreferences.getString("no_hp", "")

        mbinding.layNama.setText(namaDonatur)

        setNominal()

        mbinding.btnDonasi.setOnClickListener {
            // Check if all required fields are filled
            if (isFormValid()) {
                // Proceed with payment
                val itemDetails = listOf(
                    com.midtrans.sdk.uikit.api.model.ItemDetails(
                        "DNS-${UUID.randomUUID()}",
                        initTransactionDetails().grossAmount,
                        1,
                        "DONASI"
                    )
                )

                UiKitApi.getDefaultInstance().startPaymentUiFlow(
                    activity = this@DonasiActivity,
                    launcher = launcher,
                    transactionDetails = initTransactionDetails(),
                    customerDetails = com.midtrans.sdk.uikit.api.model.CustomerDetails(
                        firstName = mbinding.layNama.text.toString(),
                        customerIdentifier = emailDonatur ?: "",
                        email = emailDonatur ?: "",
                        phone = noHpDonatur ?: ""
                    ),
                    itemDetails = itemDetails,
                    paymentMethod = PaymentMethod.BANK_TRANSFER
                )
            } else {
                // Display notification for incomplete form
                Toast.makeText(this@DonasiActivity, "Harap isi semua kolom", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isFormValid(): Boolean {
        // Check if all required fields are filled
        val namaDon = mbinding.layNama.text.toString()
        val keterangan = mbinding.layKeterangan.text.toString()
        val doa = mbinding.layDoa.text.toString()
        val nominal = mbinding.layNominal.text.toString()

        if (nominal.isEmpty() || nominal.toDoubleOrNull() ?: 0.0 < 5000) {
            Toast.makeText(this@DonasiActivity, "Masukkan nominal minimal 5000", Toast.LENGTH_SHORT).show()
            return false
        }
        return namaDon.isNotEmpty() && keterangan.isNotEmpty() && doa.isNotEmpty()
    }


    private fun buildUiKit() {
        UiKitApi.Builder().apply {
            withContext(this@DonasiActivity.applicationContext)
            withMerchantUrl("https://nhcare.tifc.myhost.id/nhcare/api/midtrans.php/")
            withMerchantClientKey("SB-Mid-client-Cus_lO_5JXzHSIcU")
            enableLog(true)
            withColorTheme(
                com.midtrans.sdk.uikit.api.model.CustomColorTheme(
                    "#FFE51255",
                    "#B61548",
                    "#FFE51255"
                )
            )
            build()
        }
        setLocaleNew("id")
        uiKitCustomSetting()
    }

    private fun setLocaleNew(languageCode: String?) {
        val locales = LocaleListCompat.forLanguageTags(languageCode)
        AppCompatDelegate.setApplicationLocales(locales)
    }

    private fun setNominal() {
        mbinding.btn25.setOnClickListener { updateEditText(25000) }
        mbinding.btn50.setOnClickListener { updateEditText(50000) }
        mbinding.btn75.setOnClickListener { updateEditText(75000) }
        mbinding.btn100.setOnClickListener { updateEditText(100000) }
    }

    private fun updateEditText(amount: Int) {
        mbinding.layNominal.setText(amount.toString())
    }

    private fun uiKitCustomSetting() {
        val uIKitCustomSetting = UiKitApi.getDefaultInstance().uiKitSetting
        uIKitCustomSetting.saveCardChecked = true
    }

    private fun checkOrderStatus(orderId: String) {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://api.sandbox.midtrans.com/v2/$orderId/status")
            .get()
            .addHeader("accept", "application/json")
            .addHeader(
                "authorization",
                "Basic U0ItTWlkLXNlcnZlci1TMUdwX0o3b2Z0aWJ0dXNPWTdqUjhKalA6"
            )
            .build()

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = client.newCall(request).execute()

                response.use {
                    if (response.isSuccessful) {
                        val responseBody = response.body?.string()

                        runOnUiThread {
                            Log.d("PaymentsMidtrans", "Response: $responseBody")

                            try {
                                val jsonObject = JSONObject(responseBody)
//                                Log.d("PaymentsMidtrans", "JSON Object: $jsonObject")
                                val transactionId = jsonObject.getString("transaction_id")
                                val grossAmount = jsonObject.getDouble("gross_amount")
                                val orderId = jsonObject.getString("order_id")
                                val settlementTime = jsonObject.getString("settlement_time")

                                Log.d(
                                    "PaymentsMidtrans",
                                    "transaction_id: $transactionId, gross_amount: $grossAmount, order_id: $orderId, settlement_time: $settlementTime"
                                )

                                val transactionStatus = jsonObject.getString("transaction_status")

                                if (transactionStatus == "settlement") {
                                    Toast.makeText(this@DonasiActivity, "Donasi Berhasil", Toast.LENGTH_LONG).show()
                                    val intent = Intent(this@DonasiActivity, AfterDonasiActivity::class.java)
                                    startActivity(intent)
                                } else {
                                    Toast.makeText(this@DonasiActivity, "Donasi Gagal", Toast.LENGTH_LONG).show()
                                }

                                sendDataToServer(jsonObject)

                            } catch (e: JSONException) {
                                e.printStackTrace()
                                Log.e("PaymentsMidtrans", "Error parsing JSON")
                            }
                        }
                    } else {
                        runOnUiThread {
                            val errorMessage = "Error: ${response.code} ${response.message}"
                            Log.e("PaymentsMidtrans", errorMessage)

                            Toast.makeText(this@DonasiActivity, "Donasi Gagal", Toast.LENGTH_SHORT).show()
                            //pindah fragment
                            val intent = Intent (this@DonasiActivity, MainActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
                runOnUiThread {
                    val errorMessage = "Error: ${e.message}"
                    Toast.makeText(this@DonasiActivity, "Donasi Gagal", Toast.LENGTH_SHORT).show()

                }
            }
        }
    }

    private fun sendDataToServer(jsonObject: JSONObject) {
        val url = "https://nhcare.tifc.myhost.id/nhcare/api/api-Nhcare.php?function=insertDonasi"

        val sharedPreferences = getSharedPreferences("donatur_prefs", Context.MODE_PRIVATE)
//        val idDonatur = sharedPreferences.getString("id_donatur", "")
        val idDonatur = sharedPreferences.getString("id_donatur", "")
        val namaDon = mbinding.layNama.text.toString()
        val keterangan = mbinding.layKeterangan.text.toString()
        val doa = mbinding.layDoa.text.toString()

        val stringRequest = object : StringRequest(
            com.android.volley.Request.Method.POST,
            url,
            { response ->
                runOnUiThread {
                    if (response.contains("Data berhasil disimpan")) {
                        Log.d("PaymentsMidtrans", "Data berhasil disimpan di server")
                        // Handle success case
                    } else {
                        Log.e("PaymentsMidtrans", "Server response: $response")
                        // Handle unexpected response
                    }
                }
            },
            { error ->
                runOnUiThread {
                    Log.e("PaymentsMidtrans", "Error sending data to server: ${error.message}")
                    // Handle error, log, or show an error message
                }
            }
        ) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["transaction_id"] = jsonObject.getString("transaction_id")
                params["gross_amount"] = jsonObject.getDouble("gross_amount").toString()
                params["order_id"] = jsonObject.getString("order_id")
                params["settlement_time"] = jsonObject.getString("settlement_time")
                params["id_donatur"] = idDonatur ?: ""
                params["nama_donatur"] = namaDon
                params["keterangan"] = keterangan
                params["doa"] = doa
                return params
            }
        }

        requestQueue.add(stringRequest)
    }
    private val requestQueue by lazy {
        Volley.newRequestQueue(applicationContext)
    }
}
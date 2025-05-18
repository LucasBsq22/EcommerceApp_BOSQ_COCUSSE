package com.example.ecommerceapp.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.example.ecommerceapp.ui.ProductDetailsActivity

class QRScanActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Démarre le scan
        IntentIntegrator(this).apply {
            setPrompt("Scannez le QR Code d'un produit")
            setBeepEnabled(true)
            setOrientationLocked(false)
            setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            initiateScan()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result: IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents != null) {
                val productId = result.contents.toIntOrNull()
                if (productId != null) {
                    val intent = Intent(this, ProductDetailsActivity::class.java)
                    intent.putExtra("product_id", productId)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "QR invalide : pas un ID numérique", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Scan annulé", Toast.LENGTH_SHORT).show()
            }
            finish()
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}

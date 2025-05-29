package com.example.ecommerceapp.ui


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.ecommerceapp.network.RetrofitInstance
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import kotlinx.coroutines.launch

class QRScanActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Démarre le scan
        IntentIntegrator(this).apply {
            setPrompt("Scannez le QR Code d'un produit")
            setBeepEnabled(true)
            setOrientationLocked(false)
            setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            setCaptureActivity(CaptureActivityPortrait::class.java)
            initiateScan()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result: IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result.contents != null) {
            val productId = result.contents.toIntOrNull()
            if (productId != null) {
                lifecycleScope.launch {
                    try {
                        val product = RetrofitInstance.api.getProductById(productId)
                        val intent = Intent(this@QRScanActivity, ProductDetailsActivity::class.java)
                        intent.putExtra("productId", product.id)
                        startActivity(intent)
                        finish()
                    } catch (e: Exception) {
                        Toast.makeText(this@QRScanActivity, "Produit non trouvé. Retour à l’accueil.", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@QRScanActivity, ListProductsActivity::class.java))
                        finish()
                    }
                }
            } else {
                Toast.makeText(this, "QR invalide : pas un ID numérique", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, ListProductsActivity::class.java))
                finish()
            }
        } else {
            Toast.makeText(this, "Scan annulé", Toast.LENGTH_SHORT).show()
            finish()
        }

    }
}


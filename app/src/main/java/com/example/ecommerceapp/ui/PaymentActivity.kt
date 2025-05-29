package com.example.ecommerceapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceapp.R
import com.example.ecommerceapp.cart.CartManager

class PaymentActivity : AppCompatActivity() {

    private lateinit var tvOrderSummary: TextView
    private lateinit var etCardName: EditText
    private lateinit var etCardNumber: EditText
    private lateinit var etCardExpiry: EditText
    private lateinit var etCardCVV: EditText
    private lateinit var btnFakePay: Button
    private lateinit var tvTotalAmount: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        tvOrderSummary = findViewById(R.id.tvOrderSummary)
        etCardName = findViewById(R.id.etCardName)
        etCardNumber = findViewById(R.id.etCardNumber)
        etCardExpiry = findViewById(R.id.etCardExpiry)
        etCardCVV = findViewById(R.id.etCardCVV)
        btnFakePay = findViewById(R.id.btnFakePay)



        val cartItems = CartManager.getCartItems().toList()
        val adapter = PaymentProductAdapter(cartItems)
        val recyclerView = findViewById<RecyclerView>(R.id.rvPaymentItems)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        tvTotalAmount = findViewById(R.id.tvTotalAmount)
        val total = CartManager.getTotalPrice()
        tvTotalAmount.text = "Total à payer : %.2f €".format(total)



        btnFakePay.setOnClickListener {
            val name = etCardName.text.toString()
            val number = etCardNumber.text.toString()
            val expiry = etCardExpiry.text.toString()
            val cvv = etCardCVV.text.toString()

            if (name.isBlank() || number.isBlank() || expiry.isBlank() || cvv.isBlank()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Paiement réussi. Merci pour votre achat !", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, ListProductsActivity::class.java))
                CartManager.clearCart()
                finish()
            }
        }
    }

}
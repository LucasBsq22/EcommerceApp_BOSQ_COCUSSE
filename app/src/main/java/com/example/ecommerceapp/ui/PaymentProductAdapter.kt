package com.example.ecommerceapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerceapp.databinding.PaymentProductViewBinding
import com.example.ecommerceapp.model.Product

class PaymentProductAdapter(
    private val items: List<Pair<Product, Int>>
) : RecyclerView.Adapter<PaymentProductAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: PaymentProductViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PaymentProductViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (product, quantity) = items[position]
        val b = holder.binding

        b.tvProductTitle.text = "${product.title}"
        b.tvProductDetails.text = "Quantité : $quantity    Prix unitaire : %.2f €".format(product.price)
        b.tvProductSubtotal.text = "Sous-total : %.2f €".format(product.price * quantity)

        Glide.with(b.ivProductImage.context)
            .load(product.image)
            .into(b.ivProductImage)
    }
}

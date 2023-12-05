package com.example.restaurantapi.framework.pantallamain

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantapi.R
import com.example.restaurantapi.databinding.ViewCustomerBinding
import com.example.restaurantapi.domain.modelo.Customer
import com.example.restaurantapi.framework.ConstantsFramework
import com.example.restaurantapi.framework.pantallarorders.OrdersActivity

class CustomerAdapter(
    val context: Context,
    val actions: CustomerActions
) :
    ListAdapter<Customer, CustomerAdapter.ItemViewholder>(DiffCallback()) {

    interface CustomerActions {
        fun onDelete(customer: Customer)
        fun onStartSelectMode(customer: Customer)
        fun itemHasClicked(customer: Customer)

    }
    private var selectedMode: Boolean = false
    private var selectedCustomers = mutableSetOf<Customer>()

    fun startSelectMode() {
        selectedMode = true
        notifyDataSetChanged()
    }


    fun resetSelectMode() {
        selectedMode = false
        selectedCustomers.clear()
        notifyDataSetChanged()
    }

    fun setSelectedItems(selectedCustomers: List<Customer>){
        this.selectedCustomers.clear()
        this.selectedCustomers.addAll(selectedCustomers)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {

        return ItemViewholder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_customer, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewholder, position: Int) = with(holder) {

        val item = getItem(position)
        bind(item)
        holder.itemView.setOnClickListener{
            if (!selectedMode) {
            val context = it.context
            val intent = Intent(context, OrdersActivity::class.java)
            intent.putExtra(ConstantsFramework.EXTRA_CUSTOMER_ID, item.id)
            context.startActivity(intent)
        } }
    }



    inner class ItemViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ViewCustomerBinding.bind(itemView)

        fun bind(item: Customer) {
            itemView.setOnLongClickListener {
                handleLongClick(item)
                true
            }

            with(binding) {
                selected.setOnClickListener {
                    handleClick(item)
                }

                setupViews(item)
            }
        }

        private fun handleLongClick(item: Customer) {
            if (!selectedMode) {
                actions.onStartSelectMode(item)
            }
        }

        private fun handleClick(item: Customer) {
            if (selectedMode) {
                handleSelectedModeClick(item)
            }
        }

        private fun handleSelectedModeClick(item: Customer) {
            with(binding) {
                if (selected.isChecked) {
                    item.isSelected = true
                    itemView.setBackgroundColor(Color.GREEN)
                    selectedCustomers.add(item)
                } else {
                    item.isSelected = false
                    itemView.setBackgroundColor(Color.WHITE)
                    selectedCustomers.remove(item)
                }

                actions.itemHasClicked(item)
            }
        }

        private fun setupViews(item: Customer) {
            with(binding) {
                tvNombre.text = item.name
                tvId.text = item.id.toString()

                selected.visibility = if (selectedMode) View.VISIBLE else View.GONE

                if (selectedCustomers.contains(item)) {
                    itemView.setBackgroundColor(Color.GREEN)
                    selected.isChecked = true
                } else {
                    itemView.setBackgroundColor(Color.WHITE)
                    selected.isChecked = false
                }
            }
        }

    }

    class DiffCallback : DiffUtil.ItemCallback<Customer>() {
        override fun areItemsTheSame(oldItem: Customer, newItem: Customer): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Customer, newItem: Customer): Boolean {
            return oldItem == newItem
        }
    }

    val swipeGesture = object : SwipeGesture(context) {

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            when (direction) {
                ItemTouchHelper.LEFT -> {
                    val position = viewHolder.bindingAdapterPosition
                    selectedCustomers.remove(currentList[position])
                    actions.onDelete(currentList[position])
                    if (selectedMode)
                        actions.itemHasClicked(currentList[position])
                }
            }
        }

    }

}



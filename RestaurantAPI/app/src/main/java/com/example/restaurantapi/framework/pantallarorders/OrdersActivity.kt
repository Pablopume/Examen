package com.example.restaurantapi.framework.pantallarorders

import android.os.Bundle

import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

import androidx.appcompat.view.ActionMode
import androidx.recyclerview.widget.ItemTouchHelper

import com.example.restaurantapi.databinding.ActivityOrderBinding


import com.example.restaurantapi.domain.modelo.Order
import com.example.restaurantapi.framework.ConstantsFramework
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate



@AndroidEntryPoint
class OrdersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderBinding
    private var primeraVez: Boolean = false

    private lateinit var customAdapter: OrderAdapter
    private val viewModel: OrdersViewModel by viewModels()

    private var actionMode: ActionMode? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        val intent = intent

        val id = intent.getIntExtra(ConstantsFramework.EXTRA_CUSTOMER_ID, 0)
        super.onCreate(savedInstanceState)
        initBinding()
        initRecyclerView()
        initViewModel()
        handleIntent(id)
        getCustomer(id)
        add(id)
    }


    private fun initBinding() {
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initRecyclerView() {
        customAdapter = OrderAdapter(this, orderActions())
        binding.rvCustomers.adapter = customAdapter
        val touchHelper = ItemTouchHelper(customAdapter.swipeGesture)
        touchHelper.attachToRecyclerView(binding.rvCustomers)
    }

    private fun orderActions(): (Order) -> Unit {
        return { order ->
            viewModel.handleEvent(OrderEvent.DeleteOrder(order))
        }
    }
        private fun initViewModel() {
            viewModel.uiState.observe(this) { state ->
                handleOrderState(state)
                handleViewState(state)
                handleCustomerActual(state)
            }
        }

        private fun handleCustomerActual(state: OrdersState) {
            state.actualCustomer.let {
                if (it != null) {
                    (ConstantsFramework.customersname + it.name).also { binding.textView.text = it }
                }
            }
        }

        private fun handleOrderState(state: OrdersState) {
            state.orders.let {
                if (it.isNotEmpty()) {
                    customAdapter.submitList(it)
                }
            }
            state.selectedOrders.let {
                if (it.isNotEmpty()) {
                    customAdapter.setSelectedItems(it)
                } else {
                    customAdapter.resetSelectMode()
                    primeraVez = true
                    actionMode?.finish()
                }
            }
        }
        private fun handleViewState(state: OrdersState) {
            state.error?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }

        private fun handleIntent(id: Int) {

            viewModel.handleEvent(OrderEvent.GetOrders(id))

        }

        private fun getCustomer(id: Int) {
            viewModel.handleEvent(OrderEvent.GetCustomer(id))
        }

        private fun add(id: Int) {
            with(binding) {
                button.setOnClickListener() {
                    val tableid = textName.text.toString().toInt()
                    val order = Order(0, id, LocalDate.now(), tableid)
                    viewModel.handleEvent(OrderEvent.AddOrder(order))
                }
            }
        }
        }



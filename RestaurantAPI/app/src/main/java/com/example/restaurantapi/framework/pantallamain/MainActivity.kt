package com.example.restaurantapi.framework.pantallamain

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.view.ActionMode


import androidx.recyclerview.widget.ItemTouchHelper
import com.example.restaurantapi.R
import com.example.restaurantapi.databinding.ActivityMainBinding
import com.example.restaurantapi.domain.modelo.Customer

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var primeraVez: Boolean = false
    private lateinit var customAdapter: CustomerAdapter
    private val viewModel: MainViewModel by viewModels()
    private val callback by lazy {
        configContextBar()
    }
    private var actionMode: ActionMode? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initRecyclerView()
        initViewModel()

    }

    private fun initBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initRecyclerView() {
        customAdapter = CustomerAdapter(this, createCustomerActions())
        binding.rvCustomers.adapter = customAdapter
        val touchHelper = ItemTouchHelper(customAdapter.swipeGesture)
        touchHelper.attachToRecyclerView(binding.rvCustomers)
    }

    private fun createCustomerActions(): CustomerAdapter.CustomerActions {
        return object : CustomerAdapter.CustomerActions {
            override fun onDelete(customer: Customer) =
                viewModel.handleEvent(MainEvent.DeleteCustomer(customer))

            override fun onStartSelectMode(customer: Customer) {
                viewModel.handleEvent(MainEvent.StartSelectMode)
                viewModel.handleEvent(MainEvent.SelectCustomer(customer))
            }

            override fun itemHasClicked(customer: Customer) =
                viewModel.handleEvent(MainEvent.SelectCustomer(customer))
        }
    }

    private fun initViewModel() {
        viewModel.uiState.observe(this) { state ->
            handleCustomerState(state)
            handleSelectModeState(state)
            handleViewState(state)
        }
    }

    private fun handleCustomerState(state: MainState) {
        state.customers.let {
            if (it.isNotEmpty()) {
                customAdapter.submitList(it)
            }
        }

        state.selectedCustomers.let {
            if (it.isNotEmpty()) {
                customAdapter.setSelectedItems(it)
            } else {
                customAdapter.resetSelectMode()
                primeraVez = true
                actionMode?.finish()
            }
        }
    }

    private fun handleSelectModeState(state: MainState) {
        state.selectMode.let { seleccionado ->
            if (seleccionado) {
                handleSelectModeTrue()
            } else {
                handleSelectModeFalse()
            }
        }
    }

    private fun handleSelectModeTrue() {
        if (primeraVez) {
            customAdapter.startSelectMode()
            startSupportActionMode(callback)?.let {
                actionMode = it
            }
            primeraVez = false
        } else {
            customAdapter.startSelectMode()
        }
    }

    private fun handleSelectModeFalse() {
        customAdapter.resetSelectMode()
        primeraVez = true
        actionMode?.finish()
    }

    private fun handleViewState(state: MainState) {
        state.error?.let {

            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun configContextBar() = object : ActionMode.Callback {

        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            menuInflater.inflate(R.menu.context_bar, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            return when (item?.itemId) {
                R.id.delete -> {
                    viewModel.handleEvent(MainEvent.DeleteCustomers())
                    true
                }

                else -> false
            }
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            viewModel.handleEvent(MainEvent.ResetSelectMode)
        }
    }
}
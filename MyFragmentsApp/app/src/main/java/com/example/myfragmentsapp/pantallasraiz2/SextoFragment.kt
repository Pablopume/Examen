package com.example.myfragmentsapp.pantallasraiz2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import androidx.navigation.fragment.findNavController
import com.example.myfragmentsapp.Constantes
import com.example.myfragmentsapp.databinding.FragmentSextoBinding

class SextoFragment: Fragment() {
    private var _binding: FragmentSextoBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSextoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

            irCuarto.setOnClickListener {
                val action = SextoFragmentDirections.actionSextoFragmentToCuartoFragment(Constantes.sexto)
                findNavController().navigate(action)
            }
            irQuinto.setOnClickListener{
                val action = SextoFragmentDirections.actionSextoFragmentToQuintoFragment(Constantes.sexto)
                findNavController().navigate(action)
            }
        }
    }
}

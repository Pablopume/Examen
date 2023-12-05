package com.example.myfragmentsapp.pantallasraiz3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import androidx.navigation.fragment.findNavController
import com.example.myfragmentsapp.Constantes

import com.example.myfragmentsapp.databinding.FragmentNovenoBinding

class NovenoFragment: Fragment() {
    private var _binding: FragmentNovenoBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNovenoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

            irSeptimo.setOnClickListener {
                val action = NovenoFragmentDirections.actionNovenoFragmentToSeptimoFragment(
                    Constantes.DESDE_EL_NOVENO
                )
                findNavController().navigate(action)
            }
            irOctavo.setOnClickListener{
                val action = NovenoFragmentDirections.actionNovenoFragmentToOctavoFragment(
                    Constantes.DESDE_EL_NOVENO
                )
                findNavController().navigate(action)
            }
        }
    }
}

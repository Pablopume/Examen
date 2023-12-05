package com.example.myfragmentsapp.pantallasraiz3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import androidx.navigation.fragment.findNavController
import com.example.myfragmentsapp.Constantes
import com.example.myfragmentsapp.databinding.FragmentOctavoBinding

class OctavoFragment: Fragment() {
    private var _binding: FragmentOctavoBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOctavoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

            irSeptimo.setOnClickListener {
                val action = OctavoFragmentDirections.actionOctavoFragmentToSeptimoFragment(
                    Constantes.DESDE_EL_OCTAVO
                )
                findNavController().navigate(action)
            }
            irNoveno.setOnClickListener{
                val action = OctavoFragmentDirections.actionOctavoFragmentToNovenoFragment(
                    Constantes.DESDE_EL_OCTAVO
                )
                findNavController().navigate(action)
            }
        }
    }
}

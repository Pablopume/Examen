package com.example.myfragmentsapp.pantallasraiz3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myfragmentsapp.Constantes
import com.example.myfragmentsapp.databinding.FragmentSeptimoBinding

class SeptimoFragment: Fragment() {
    private var _binding: FragmentSeptimoBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSeptimoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

            irOctavo.setOnClickListener {
                val action = SeptimoFragmentDirections.actionSeptimoFragmentToOctavoFragment(
                    Constantes.DESDE_EL_SEPTIMO
                )
                findNavController().navigate(action)
            }
            irNoveno.setOnClickListener{
                val action = SeptimoFragmentDirections.actionSeptimoFragmentToNovenoFragment(
                    Constantes.DESDE_EL_SEPTIMO
                )
                findNavController().navigate(action)
            }
        }
    }
}

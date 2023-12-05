package com.example.myfragmentsapp.pantallasraiz1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myfragmentsapp.Constantes
import com.example.myfragmentsapp.R

import com.example.myfragmentsapp.databinding.FragmentSegundoBinding

class SegundoFragment : Fragment(), MenuProvider {
    val args: SegundoFragmentArgs by navArgs()
    private var _binding: FragmentSegundoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        _binding = FragmentSegundoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            texto2.text = args.mensaje
            irPrimero.setOnClickListener {
                val action = SegundoFragmentDirections.actionSegundoFragmentToPrimerFragment(
                    Constantes.DESDE_EL_SEGUNDO
                )
                findNavController().navigate(action)
            }
            irTercero.setOnClickListener{
                val action = SegundoFragmentDirections.actionSegundoFragmentToTercerFragment(
                    Constantes.DESDE_EL_SEGUNDO
                )
                findNavController().navigate(action)
            }

        }
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_nav, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.navigation_fragment5-> {
                val action = SegundoFragmentDirections.actionSegundoFragmentToQuintoFragment(
                    Constantes.DESDE_EL_SEGUNDO
                )
                findNavController().navigate(action)
                true
            }
            R.id.navigation_fragment8-> {
                val action = SegundoFragmentDirections.actionSegundoFragmentToOctavoFragment(
                    Constantes.DESDE_EL_SEGUNDO
                )
                findNavController().navigate(action)
                true
            }
            else -> false
        }
    }

}


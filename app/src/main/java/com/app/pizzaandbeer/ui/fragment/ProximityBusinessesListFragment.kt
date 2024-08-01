package com.app.pizzaandbeer.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.app.pizzaandbeer.R
import com.app.pizzaandbeer.core.AppTheme
import com.app.pizzaandbeer.core.location.LocationViewModel
import com.app.pizzaandbeer.ui.view.ProximityBusiness
import com.app.pizzaandbeer.ui.viewmodel.ProximityBusinessesViewModel

class ProximityBusinessesListFragment : Fragment() {
    private val proximityBusinessViewModel: ProximityBusinessesViewModel by activityViewModels()
    private val locationViewModel: LocationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme {
                    ProximityBusiness(
                        businessViewModel = proximityBusinessViewModel,
                        locationViewModel = locationViewModel,
                    ) {
                        findNavController().navigate(R.id.list_to_detail_fragment)
                    }
                }
            }
        }
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
    }
}

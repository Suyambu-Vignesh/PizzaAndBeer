package com.app.pizzaandbeer.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.app.pizzaandbeer.ui.view.ProximityBusinessDetailView
import com.app.pizzaandbeer.ui.viewmodel.ProximityBusinessesViewModel

class ProximityBusinessesDetailFragment : Fragment() {
    private val proximityBusinessViewModel: ProximityBusinessesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                ProximityBusinessDetailView(
                    viewModel = proximityBusinessViewModel,
                    onItemClick = {},
                    askLocationPermission = {},
                )
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

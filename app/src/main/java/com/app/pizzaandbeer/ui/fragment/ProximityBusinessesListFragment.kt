package com.app.pizzaandbeer.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.app.pizzaandbeer.core.location.LocationLifecycleObserver
import com.app.pizzaandbeer.core.permission.AppPermission
import com.app.pizzaandbeer.core.permission.PermissionHandler
import com.app.pizzaandbeer.databinding.FragmentProximityBusinessesBinding
import com.app.pizzaandbeer.ui.view.ProximityBusiness
import com.app.pizzaandbeer.ui.viewmodel.ProximityBusinessesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProximityBusinessesListFragment : Fragment() {
    private lateinit var binding: FragmentProximityBusinessesBinding
    private lateinit var activityResultLauncher: ActivityResultLauncher<Array<String>>

    private val proximityBusinessViewModel: ProximityBusinessesViewModel by viewModels()
    private val locationLifecycleObserver: LocationLifecycleObserver by lazy {
        LocationLifecycleObserver(
            this.requireContext(),
            proximityBusinessViewModel,
        )
    }

    init {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                proximityBusinessViewModel.locationStateFlow.collectLatest {
                    if (it != null) {
                        proximityBusinessViewModel.refresh()
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        activityResultLauncher =
            requireActivity().registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions(),
            ) {
                observeLocationOrFetchBusinessSuggestion()
            }
        return ComposeView(requireContext()).apply {
            setContent {
                ProximityBusiness(
                    viewModel = proximityBusinessViewModel,
                    activityResultLauncher = activityResultLauncher,
                ) {
                    observeLocationOrFetchBusinessSuggestion()
                }
            }
        }
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        observeLocationOrFetchBusinessSuggestion()
    }

    private fun observeLocationOrFetchBusinessSuggestion() {
        if (PermissionHandler.hasPermission(
                requireActivity(),
                AppPermission.PERMISSION_ACCESS_FINE_LOCATION,
            ) ||
            PermissionHandler.hasPermission(
                requireActivity(),
                AppPermission.PERMISSION_ACCESS_COARSE_LOCATION,
            )
        ) {
            lifecycle.addObserver(locationLifecycleObserver)
        } else {
            // proximityBusinessViewModel.fetchBusinessNearBy()
        }
    }
}

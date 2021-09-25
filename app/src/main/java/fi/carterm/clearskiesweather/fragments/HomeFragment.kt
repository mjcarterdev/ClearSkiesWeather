package fi.carterm.clearskiesweather.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import fi.carterm.clearskiesweather.R
import fi.carterm.clearskiesweather.databinding.FragmentHomeBinding

class HomeFragment: Fragment(R.layout.fragment_home) {
    lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
    }
}
package com.example.practica_fundamentos_android.main.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.practica_fundamentos_android.R
import com.example.practica_fundamentos_android.databinding.FragmentFightBinding
import com.example.practica_fundamentos_android.main.MainActivityViewModel
import com.example.practica_fundamentos_android.model.Heroe
import com.squareup.picasso.Picasso


class FragmentFight(val heroe: Heroe) : Fragment() {

    private lateinit var binding: FragmentFightBinding
    private val viewModel : MainActivityViewModel by viewModels()

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        // This callback will only be called when MyFragment is at least Started.
//        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
//            // Handle the back button event
//        }
//
//        // The callback can be enabled or disabled here or in the lambda
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFightBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Picasso.get().load(heroe.photo).placeholder(R.drawable.baseline_person_24).into(binding.image);
        binding.heroeName.text = heroe.name
        binding.progressBarLife.max = heroe.vidaTotal
        binding.progressBarLife.progress = heroe.vidaRestante
        binding.lifeText.text = "Vida ${heroe.vidaRestante} / ${heroe.vidaTotal}"


        binding.healBtn.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack() }

    }





}
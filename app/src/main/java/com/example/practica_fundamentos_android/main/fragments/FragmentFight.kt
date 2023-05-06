package com.example.practica_fundamentos_android.main.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import com.example.practica_fundamentos_android.R
import com.example.practica_fundamentos_android.databinding.FragmentFightBinding
import com.example.practica_fundamentos_android.main.MainActivity
import com.example.practica_fundamentos_android.main.MainActivityViewModel
import com.example.practica_fundamentos_android.model.Heroe
import com.squareup.picasso.Picasso


class FragmentFight(private val viewModel: MainActivityViewModel, private val pos: Int) : Fragment() {

    private lateinit var binding: FragmentFightBinding
    private lateinit var heroe: Heroe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        heroe = viewModel.heroes[pos]

        (requireActivity() as MainActivity).binding.restartBtn.visibility = View.GONE
        (requireActivity() as MainActivity).binding.toolbar.title = heroe.name

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            goBack()
        }
    }

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
        updateUI()

        binding.healBtn.setOnClickListener {
            viewModel.healHeroe(pos)
            updateUI()
        }

        binding.damageBtn.setOnClickListener {
            viewModel.damageHeroe(pos)
            updateUI()
            if(heroe.vidaRestante == 0) goBack()
        }

    }


    private fun updateUI() {
        binding.progressBarLife.progress = heroe.vidaRestante
        binding.lifeText.text = "Vida ${heroe.vidaRestante} / ${heroe.vidaTotal}"
    }

    private fun goBack(){
        requireActivity().supportFragmentManager.popBackStack()
        (requireActivity() as MainActivity).binding.restartBtn.visibility = View.VISIBLE
        (requireActivity() as MainActivity).binding.toolbar.title = "Heroes"

    }



}
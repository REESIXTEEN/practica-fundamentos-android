package com.example.practica_fundamentos_android.main.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import com.example.practica_fundamentos_android.R
import com.example.practica_fundamentos_android.databinding.FragmentFightBinding
import com.example.practica_fundamentos_android.main.MainActivity
import com.example.practica_fundamentos_android.main.MainActivityViewModel
import com.example.practica_fundamentos_android.model.Heroe
import com.squareup.picasso.Picasso
import kotlin.random.Random

const val HEAL = 20
const val MIN_DAMAGE = 10
const val MAX_DAMAGE = 60

class FragmentFight(private val viewModel: MainActivityViewModel, private val pos: Int) : Fragment() {

    private lateinit var binding: FragmentFightBinding
    private lateinit var heroe: Heroe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        heroe = viewModel.heroes[pos]

        (requireActivity() as MainActivity).binding.restartBtn.visibility = View.GONE
        (requireActivity() as MainActivity).binding.toolbar.title = heroe.name

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            updateHeroeData()
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
            healHeroe()
            updateUI()
        }

        binding.damageBtn.setOnClickListener {
            damageHeroe()
            updateUI()
        }

    }

    private fun updateHeroeData() {
        viewModel.heroes[pos].vidaRestante = heroe.vidaRestante
    }

    private fun updateUI() {
        binding.progressBarLife.progress = heroe.vidaRestante
        binding.lifeText.text = "Vida ${heroe.vidaRestante} / ${heroe.vidaTotal}"
    }

    private fun healHeroe(){
        heroe.vidaRestante = heroe.vidaRestante + HEAL
        if(heroe.vidaRestante > heroe.vidaTotal){
            heroe.vidaRestante = heroe.vidaTotal
        }
    }

    private fun damageHeroe(){
        val damage = Random.nextInt(MIN_DAMAGE, MAX_DAMAGE)
        heroe.vidaRestante = heroe.vidaRestante - damage
        if(heroe.vidaRestante <= 0){
            heroe.vidaRestante = 0
            updateHeroeData()
            goBack()
        }
    }

    private fun goBack(){
        requireActivity().supportFragmentManager.popBackStack()
        (requireActivity() as MainActivity).binding.restartBtn.visibility = View.VISIBLE
        (requireActivity() as MainActivity).binding.toolbar.title = "Heroes"

    }



}
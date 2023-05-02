package com.example.practica_fundamentos_android.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practica_fundamentos_android.databinding.FragmentTableBinding
import com.example.practica_fundamentos_android.main.MainActivityAdapter
import com.example.practica_fundamentos_android.main.MainActivityViewModel


class FragmentTable : Fragment() {

    private lateinit var binding: FragmentTableBinding
    private val viewModel : MainActivityViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTableBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val heroes = viewModel.heroes
        val adapter = MainActivityAdapter(heroes)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

    }

}
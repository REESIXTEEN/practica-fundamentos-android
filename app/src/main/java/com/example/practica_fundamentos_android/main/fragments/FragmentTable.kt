package com.example.practica_fundamentos_android.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practica_fundamentos_android.R
import com.example.practica_fundamentos_android.databinding.FragmentTableBinding
import com.example.practica_fundamentos_android.main.MainActivity
import com.example.practica_fundamentos_android.main.MainActivityViewModel
import com.example.practica_fundamentos_android.model.Heroe
import com.example.practica_fundamentos_android.network.LIFE
import kotlinx.coroutines.launch


class FragmentTable(private val viewModel: MainActivityViewModel) : Fragment(), HeroeClicked {

    private lateinit var binding: FragmentTableBinding
    private val adapter = FragmentTableAdapter(viewModel.heroes,this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getToken(requireContext())
        viewModel.getHeroes()

    }

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

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mainStatus.collect {
                    when (it) {
                        is MainActivityViewModel.MainStatus.Loading -> {
                            binding.loading.visibility = View.VISIBLE
                        }

                        is MainActivityViewModel.MainStatus.Error -> {
                            binding.loading.visibility = View.GONE
                            Toast.makeText(requireContext(), it.error, Toast.LENGTH_LONG).show()
                        }

                        is MainActivityViewModel.MainStatus.Success -> {
                            binding.loading.visibility = View.GONE
                            adapter.listHeroes = viewModel.heroes
                            adapter.notifyDataSetChanged()
                        }
                    }
                }
            }
        }

        (requireActivity() as MainActivity).binding.restartBtn.setOnClickListener {
            for (heroe in viewModel.heroes){
                heroe.vidaRestante = LIFE
            }
            adapter.notifyDataSetChanged()
        }


    }

    override fun clicked(pos: Int) {
        if(viewModel.heroes[pos].vidaRestante > 0){
            val fragmentManager = requireActivity().supportFragmentManager
            fragmentManager.beginTransaction()
//                .setCustomAnimations(androidx.appcompat.R.anim.abc_slide_in_top,androidx.appcompat.R.anim.abc_slide_out_top)
                .replace(R.id.fragmentContainerView, FragmentFight(viewModel, pos))
                .addToBackStack(null)
                .commit()
        }
    }

}
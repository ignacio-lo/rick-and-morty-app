package com.example.rootstrapinterview.ui.characters

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rootstrapinterview.R
import com.example.rootstrapinterview.data.DataSourceImpl
import com.example.rootstrapinterview.data.api.model.character.Character
import com.example.rootstrapinterview.databinding.FragmentCharactersBinding
import com.example.rootstrapinterview.domain.RepoImpl
import com.example.rootstrapinterview.ui.adapter.CharacterAdapter
import com.example.rootstrapinterview.ui.characters.viewmodel.CharactersViewModel
import com.example.rootstrapinterview.ui.detailcharacter.DetailActivity
import com.example.rootstrapinterview.utils.Resource
import com.example.rootstrapinterview.utils.VMFactory
import kotlinx.coroutines.delay

class CharactersFragment : Fragment(), CharacterAdapter.CharacterActions {

    private val viewModel by viewModels<CharactersViewModel> {
        VMFactory(
            RepoImpl(DataSourceImpl())
        )
    }

    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding!!
    private var lastInformedCount = 0
    private var nextPage = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCharactersBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerViewOnScrollListener()

        loadCharacters()

        viewModel.characterList.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE

                    if (nextPage) {
                        binding.progressBar.updateLayoutParams<ConstraintLayout.LayoutParams> {
                            topToTop = ConstraintLayout.LayoutParams.UNSET
                        }
                    }
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE

                    if (result.data.isNullOrEmpty()) {
                        binding.rvCharacters.visibility = View.GONE
                    } else {
                        binding.rvCharacters.visibility = View.VISIBLE

                        if (!nextPage) {
                            setAdapter(result.data)
                        } else {
                            addCharacters(result.data)
                        }
                    }
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE

                    Toast.makeText(requireContext(), "Error getting characters", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun loadCharacters() {
        nextPage = false
        viewModel.getCharacters(nextPage)
    }

    private fun loadNextPage() {
        nextPage = true
        viewModel.getCharacters(nextPage)
    }

    private fun setAdapter(list: ArrayList<Character>) {
        binding.rvCharacters.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.rvCharacters.setHasFixedSize(true)
        binding.rvCharacters.adapter = CharacterAdapter(requireContext(), list, this)
    }

    private fun addCharacters(list: ArrayList<Character>) {
        (binding.rvCharacters.adapter as CharacterAdapter).addCharacters(list)
    }

    private fun setRecyclerViewOnScrollListener() {
        binding.rvCharacters.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition: Int = (binding.rvCharacters.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

                val totalItemCount = recyclerView.layoutManager!!.itemCount

                if (lastInformedCount != totalItemCount && totalItemCount == lastVisibleItemPosition + 1) {
                    loadNextPage()
                    lastInformedCount = recyclerView.layoutManager!!.itemCount
                }
            }
        })
    }

    override fun onCharacterClicked(image: String, name: String, species: String, status: String) {
        startActivity(DetailActivity.createIntent(requireContext(), image, name, species, status))
    }
}
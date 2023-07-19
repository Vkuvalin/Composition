package com.example.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.composition.R
import com.example.composition.databinding.FragmentChooseLevelBinding
import com.example.composition.domain.entity.Level


class ChooseLevelFragment : Fragment() {

    //region До Navigation
    /*
    companion object {
        const val NAME = "ChooseLevelFragment"
        fun newInstance(): ChooseLevelFragment {
            return ChooseLevelFragment()
        }
    }
    */
    //endregion

    /* ####################################### ПЕРЕМЕННЫЕ ####################################### */
    private var _binding: FragmentChooseLevelBinding? = null
    private val binding: FragmentChooseLevelBinding
        get() = _binding ?: throw RuntimeException("FragmentChooseLevelBinding == null")


    /* ################################### ФУНКЦИИ (основные) ################################### */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseLevelBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.testButton.setOnClickListener {
            launchGameFragment(Level.TEST)
        }

        binding.easyButton.setOnClickListener {
            launchGameFragment(Level.EASY)
        }

        binding.normalButton.setOnClickListener {
            launchGameFragment(Level.NORMAL)
        }

        binding.hardButton.setOnClickListener {
            launchGameFragment(Level.HARD)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    /* ############################### ФУНКЦИИ (вспомогательные) ################################ */
    //region До использования JetPack Navigation
    /*
    private fun launchGameFragment(level: Level){
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameFragment.newInstance(level))
            .addToBackStack(null)
            .commit()
    }
    */
    //endregion
    private fun launchGameFragment(level: Level) {
        //region До использования
        /*
        val args = Bundle().apply {
            putParcelable(GameFragment.KEY_LEVEL, level)
        }
        findNavController().navigate(R.id.action_chooseLevelFragment_to_gameFragment, args)
        */
        //endregion
        findNavController().navigate(
            ChooseLevelFragmentDirections.actionChooseLevelFragmentToGameFragment(level)
        )
    }


    /* ####################################### ИНТЕРФЕЙСЫ ####################################### */


}
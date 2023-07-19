package com.example.composition.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.composition.databinding.FragmentGameBinding
import com.example.composition.domain.entity.GameResult

class GameFragment : Fragment() {

    //region До Navigation
    /*
    companion object {
        const val NAME = "GameFragment"
        const val KEY_LEVEL = "level"
        fun newInstance(level: Level): GameFragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_LEVEL, level)
                }
            }
        }
    }
    */
    //endregion

    /* ####################################### ПЕРЕМЕННЫЕ ####################################### */
    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")


    // 2 варианта передачи аргументов:
    private val args by navArgs<GameFragmentArgs>() // Вариант_1
    private val viewModelFactory by lazy {
        // val args = GameFragmentArgs.fromBundle(requireArguments()) Вариант_2
        GameViewModelFactory(requireActivity().application, args.level)
    }

    //region Пример "ленивой инициализации" + первый вариант реализации
    /*
        Когда мы применяем паттерн "by lazy", то это означает:
            когда мы впервые обратимся к данной переменной где бы то ни было в коде, то она будет
            проинициализирована значением в скобках.

            Первый вариант реализации:

    private val viewModel: GameViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[GameViewModel::class.java]
    }
    */
    //endregion
    private val viewModel: GameViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[GameViewModel::class.java]
    }

    //region Коммент
    /*
    Если мы инициализируем tvOptions сразу или обратимся к ней до onViewCreated, то приложение упадет.
    */
    //endregion
    private val tvOptions by lazy {
        mutableListOf<TextView>().apply {
            add(binding.tvOptions1)
            add(binding.tvOptions2)
            add(binding.tvOptions3)
            add(binding.tvOptions4)
            add(binding.tvOptions5)
            add(binding.tvOptions6)
        }
    }


    /* ################################### ФУНКЦИИ (основные) ################################### */


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setClickListenersToOptions()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    /* ############################### ФУНКЦИИ (вспомогательные) ################################ */
    //region До использования JetPack Navigation
    /*
    private fun launchGameFinishedFragment(gameResult: GameResult) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameFinishedFragment.newInstance(gameResult))
            .addToBackStack(null)
            .commit()
    }
    */
    //endregion
    private fun launchGameFinishedFragment(gameResult: GameResult) {
        //region До использования
        /*
        val args = Bundle().apply {
            putParcelable(GameFinishedFragment.KEY_GAME_RESULT, gameResult)
        }
        findNavController().navigate(R.id.action_gameFragment_to_gameFinishedFragment, args)
        */
        //endregion
        findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameFinishedFragment(gameResult))
    }



    //region До использования JatpackNav
    /*private fun parseArgs() {
        requireArguments().getParcelable<Level>(KEY_LEVEL)?.let {
            level = it
        }
    }*/
    //endregion

    private fun observeViewModel() {
        viewModel.question.observe(viewLifecycleOwner) {
            binding.tvSum.text = it.sum.toString()
            binding.tvLeftNumber.text = it.visibleNumber.toString()
            for (i in 0 until tvOptions.size) {
                tvOptions[i].text = it.options[i].toString()
            }
        }

        viewModel.percentOfRightQuestions.observe(viewLifecycleOwner) {
            binding.progressBar.setProgress(it, true) // Анимация (2‑й парам) после api 24
        }

        viewModel.enoughCount.observe(viewLifecycleOwner) {
            binding.tvAnswersProgress.setTextColor(getColorByState(it))
        }

        viewModel.enoughPercent.observe(viewLifecycleOwner) {
            val color = getColorByState(it)
            binding.progressBar.progressTintList = ColorStateList.valueOf(color)
        }

        viewModel.formattedTime.observe(viewLifecycleOwner) {
            binding.tvTimer.text = it
        }

        viewModel.minPercent.observe(viewLifecycleOwner) {
            binding.progressBar.secondaryProgress = it
        }

        viewModel.gameResult.observe(viewLifecycleOwner) {
            launchGameFinishedFragment(it)
        }

        viewModel.progressAnswers.observe(viewLifecycleOwner) {
            binding.tvAnswersProgress.text = it
        }
    }

    private fun setClickListenersToOptions() {
        for (tvOption in tvOptions) {
            tvOption.setOnClickListener {
                viewModel.chooseAnswer(tvOption.text.toString().toInt())
            }
        }
    }

    private fun getColorByState(goodState: Boolean): Int {
        val colorResId = if (goodState) {
            android.R.color.holo_green_light
        } else {
            android.R.color.holo_red_light
        }
        return ContextCompat.getColor(requireContext(), colorResId) //region requireContext()
        // Тут нужен, кажись, контекст именно активити
        // endregion
    }
}
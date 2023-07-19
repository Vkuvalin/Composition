package com.example.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.composition.R
import com.example.composition.databinding.FragmentGameFinishedBinding
import com.example.composition.domain.entity.GameResult
import com.example.composition.domain.entity.GameSettings

class GameFinishedFragment: Fragment() {

    //region До Navigation
    /*
    companion object {
        const val KEY_GAME_RESULT = "game_result"
        fun newInstance(gameResult: GameResult): GameFinishedFragment {
            return GameFinishedFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_GAME_RESULT, gameResult)
                }
            }
        }
    }
    */
    //endregion

    /* ####################################### ПЕРЕМЕННЫЕ ####################################### */
    private var _binging: FragmentGameFinishedBinding? = null
    private val binging: FragmentGameFinishedBinding
    get() = _binging ?: throw RuntimeException("FragmentGameFinishedBinding == null")

    private val args by navArgs<GameFinishedFragmentArgs>()


    /* ################################### ФУНКЦИИ (основные) ################################### */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binging = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binging.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()
        bindViews()
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binging = null
    }


    /* ############################### ФУНКЦИИ (вспомогательные) ################################ */

    //region До Navigation
    /*private fun parseArgs() {
        requireArguments().getParcelable<GameResult>(KEY_GAME_RESULT)?.let {
            gameResult = it
        }
    }*/
    //endregion

    private fun retryGame() {
        //region До использования JetPack Navigation
        /*
        requireActivity().supportFragmentManager.popBackStack(ChooseLevelFragment.NAME, 0)
        */
        //endregion
        findNavController().popBackStack()
    }

    private fun getSmileResId(): Int{
        if (args.gameResult.winner){
            return R.drawable.ic_smile
        } else {
            return R.drawable.ic_sad
        }
    }

    private fun bindViews() {

        binging.emojiResult.setImageResource(getSmileResId())

        binging.tvRequiredAnswers.text = String.format(
            getString(R.string.text_required_answers),
            args.gameResult.gameSettings.minCountOfRightAnswers
        )

        binging.tvRequiredPercentage.text = String.format(
            getString(R.string.your_scope),
            args.gameResult.countOfRightAnswers
        )

        binging.tvScopeAnswers.text = String.format(
                getString(R.string.good_answers),
        args.gameResult.gameSettings.minPercentOfRightAnswers
        )

        binging.tvScopePercentage.text = String.format(
            getString(R.string.need_good_answers),
            getPercentOfRightAnswers()
        )

    }

    private fun setupClickListeners() {
        //region Пояснение
        /*
        https://stepik.org/lesson/709313/step/1?unit=709876 - 18:00
        */
        //endregion
        //region До использования JetPack Navigation
        /*
        private fun setupClickListeners() {
            requireActivity().onBackPressedDispatcher.addCallback(
                viewLifecycleOwner,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        retryGame()
                    }
                })
            binging.buttonRetry.setOnClickListener {
                retryGame()
            }
        }
        */
        //endregion
        binging.buttonRetry.setOnClickListener {
            retryGame()
        }
    }


    private fun getPercentOfRightAnswers() = if (args.gameResult.countOfQuestions == 0){
        0
    }else {
        (args.gameResult.countOfRightAnswers / args.gameResult.countOfQuestions.toDouble() * 100).toInt()
    }
}

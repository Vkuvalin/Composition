package com.example.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.composition.R
import com.example.composition.databinding.FragmentWelcomeBinding


// Просто для теста пишу. Потом удали.

class WelcomeFragment : Fragment() {

    /* ####################################### ПЕРЕМЕННЫЕ ####################################### */
    //region Архитектура binging для fragment's
    /*
    Т.к. к binging можно обратиться после уничтожения view, например, в методе onDestroy(), то есть
    вот такой подход:
    1. private var binging: FragmentWelcomeBinding? = null - создаем нулабельную переменную
    2. Дальше присваиваем значение ей в onCreateView, возвращая её
    3. В onDestroyView снова присваиваем есть null

    Все это делается, чтобы синхронизироваться с жизненным циклом фрагмента. Т.к. тогда нам постоянно
    придется ставить ?., то мы делаем так:
    1.
    private var _binging: FragmentWelcomeBinding? = null
    private val binging: FragmentWelcomeBinding
        get() = _binging ?: throw RuntimeException("FragmentWelcomeBinding == null")

    2.
    _binging = FragmentWelcomeBinding.inflate(inflater, container, false)
    return binging.root

    3.
    override fun onDestroyView() {
        super.onDestroyView()
        _binging = null
    }
    */
    //endregion
    private var _binging: FragmentWelcomeBinding? = null
    private val binging: FragmentWelcomeBinding
        get() = _binging ?: throw RuntimeException("FragmentWelcomeBinding == null")



    /* ################################### ФУНКЦИИ (основные) ################################### */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //region Предыдущий вариант
        // return inflater.inflate(R.layout.fragment_welcome, container, false)
        //endregion
        _binging = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binging.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binging.buttonUnderstand.setOnClickListener {
            launchChooseLevelFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binging = null
    }


    /* ############################### ФУНКЦИИ (вспомогательные) ################################ */

    //region До использования JetPack Navigation
    /*
    private fun launchChooseLevelFragment(){
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, ChooseLevelFragment.newInstance())
            .addToBackStack(ChooseLevelFragment.NAME)
            .commit()
    }
    */
    //endregion
    private fun launchChooseLevelFragment(){
        findNavController().navigate(R.id.action_welcomeFragment_to_chooseLevelFragment)
    }

    /* ####################################### ИНТЕРФЕЙСЫ ####################################### */

}
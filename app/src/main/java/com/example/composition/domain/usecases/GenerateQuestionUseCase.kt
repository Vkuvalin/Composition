package com.example.composition.domain.usecases

import com.example.composition.domain.entity.Question
import com.example.composition.domain.repository.GameRepository

class GenerateQuestionUseCase(
    private val repository: GameRepository
) {

    private companion object{
        private const val COUNT_OF_OPTIONS = 6
    }

    //region Что за invoke?
    /*
    Данное делается, чтобы useCase можно было вызывать как метод.
    Когда мы присвоим:
        generateQuestionUseCase = GenerateQuestionUseCase()
    то дальше сможем обращаться к ней так:
        generateQuestionUseCase()
    */
    //endregion
    operator fun invoke(maxSumValue: Int): Question {
        return repository.generateQuestion(maxSumValue, COUNT_OF_OPTIONS)
    }
}
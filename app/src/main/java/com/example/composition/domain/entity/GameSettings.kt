package com.example.composition.domain.entity

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

//region Что нужно запомнить про сериализацию?
/*
1. Процесс сериализации происходит быстрее, если реализовывать интерфейс Parcelable вместо Serializable.
2. Serializable - интерфейс-маркер (интерфейс без абстрактных методов), ничего переопределять не нужно.
3. В андроид никто не пишет эту хуйню вручную, а пользуются аннотациями, которые приезжают из
плагина:
id 'kotlin-parcelize'
*/
//endregion

@Parcelize  // kotlinx.parcelize
data class GameSettings(
    val maxSumValue: Int,
    val minCountOfRightAnswers: Int,
    val minPercentOfRightAnswers: Int,
    val gameTimeInSeconds: Int
) : Parcelable


//region Описание сериализации при использовании Parcelable
/*
data class GameSettings(
    val maxSumValue: Int,
    val minCountOfRightAnswers: Int,
    val minPercentOfRightAnswers: Int,
    val gameTimeInSeconds: Int
) : Parcelable {  // Данное было сгенерировано автоматически.

    //region Описание constructor
    /*
    Тут просто считываем поля, которые записали в Parcel. В том же порядке, в котором записывали.
    */
    //endregion
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    //region Описание writeToParcel
    /*
    Тут мы в ручную записываем все поля объекта в Parcel, причем обязательно в том порядке, в кот.
    они записаны в конструкторе.
    В зависимости от типа поля, будут вызваны различные методы writeInt или writeString и тд
    */
    //endregion
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(maxSumValue)
        parcel.writeInt(minCountOfRightAnswers)
        parcel.writeInt(minPercentOfRightAnswers)
        parcel.writeInt(gameTimeInSeconds)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GameSettings> {
        override fun createFromParcel(parcel: Parcel): GameSettings {
            return GameSettings(parcel)
        }

        override fun newArray(size: Int): Array<GameSettings?> {
            return arrayOfNulls(size)
        }
    }
}
*/
//endregion
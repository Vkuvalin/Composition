package com.example.composition.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


//region Комментарий
/*
Enum классы неявно используют enum class Level: java.io.Serializable, поэтому указывать ненужно.
Эта хрень превращат объект этого класса в набор байтов.
Но т.к. эта хуйня медленная, то чаще в андроид используется Parcelable
*/
//endregion

@Parcelize
enum class Level: Parcelable {
    TEST, EASY, NORMAL, HARD
}



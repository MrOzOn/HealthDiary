package com.mrozon.feature_person.presentation

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.mrozon.core_api.entity.Gender
import com.mrozon.core_api.entity.Person
import com.mrozon.feature_person.R
import java.time.Period
import java.util.*

@BindingAdapter("gender")
fun ImageView.setGender(item: Person) {
    if (item.gender==Gender.MALE) {
        setImageResource(R.drawable.ic_male_avatar)
    }
    else
    {
        setImageResource(R.drawable.ic_female_avatar)
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("name_with_age")
fun TextView.setNameAge(item: Person) {
    val age = getAge(item.born)
    var yo = context.getString(R.string.yo)
    when (age % 10) {
        1 -> yo = context.getString(R.string.yo1)
        2,3,4 -> yo = context.getString(R.string.yo234)
    }
    when (age % 100) {
        11,12,13,14 -> yo = context.getString(R.string.yo)
    }
    text = "${item.name} ($age $yo)"
}

private fun getAge(born: Date): Int {
    val dob: Calendar = Calendar.getInstance()
    val today: Calendar = Calendar.getInstance()
    dob.timeInMillis = born.time
    var age: Int = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR)
    if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
        age--
    }
    return age
}

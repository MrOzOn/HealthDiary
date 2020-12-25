package com.mrozon.feature_measure.presentation

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.mrozon.core_api.entity.Measure
import com.mrozon.core_api.entity.MeasureType
import com.mrozon.feature_measure.R
import com.mrozon.utils.extension.toDateString
import java.util.*

@BindingAdapter("time_of_day")
fun ImageView.setTimeOfDay(measure: Measure) {
    val date = measure.valueAdded
    val cal = Calendar.getInstance()
    cal.time = date
    setImageResource(when (cal.get(Calendar.HOUR_OF_DAY)) {
        in 0..5 -> R.drawable.ic_night
        in 6..10 -> R.drawable.ic_morning
        in 11..18 -> R.drawable.ic_day
        in 19..22 -> R.drawable.ic_evening
        in 23..24 -> R.drawable.ic_night
        else -> R.drawable.ic_day
    })
}

@BindingAdapter("added_date")
fun TextView.setDateAdded(measure: Measure) {
    text = measure.valueAdded.toDateString("EEE, d MMM HH:mm")
}

@SuppressLint("SetTextI18n")
@BindingAdapter(value = ["measure","measure_type"])
fun TextView.setMeasureValue(measure: Measure, measureType: MeasureType) {
    text = if (measure.value2.isEmpty()) {
        "${measure.value1} ${measureType.mark}"
    }
    else {
        "${measure.value1}/${measure.value2} ${measureType.mark}"
    }

}

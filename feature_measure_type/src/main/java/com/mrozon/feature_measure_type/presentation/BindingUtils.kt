package com.mrozon.feature_measure_type.presentation

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.decode.SvgDecoder
import coil.load
import com.mrozon.core_api.entity.MeasureType
import com.mrozon.core_api.network.HealthDiaryService
import com.mrozon.feature_measure_type.R

@BindingAdapter("load_logo")
fun ImageView.loadLogo(item: MeasureType) {

    load(HealthDiaryService.ENDPOINT + item.url) {
        decoder(SvgDecoder(context))
        crossfade(true)
        placeholder(R.drawable.ic_broken_image_24)
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("name_with_mark")
fun TextView.setNameWithMark(item: MeasureType) {
    text = "${item.name} (${item.mark})"
}


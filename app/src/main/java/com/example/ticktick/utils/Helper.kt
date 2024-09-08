package com.example.ticktick.utils

import android.annotation.SuppressLint
import io.realm.kotlin.types.RealmInstant
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@SuppressLint("NewApi")
class Helper {
    fun realmInstantToDateString(realmInstant: RealmInstant): String {
        val instant = Instant.ofEpochSecond(
            realmInstant.epochSeconds,
            realmInstant.nanosecondsOfSecond.toLong()
        )

        val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())

        val formatter = DateTimeFormatter.ofPattern("d MMM, HH:mm")
        return localDateTime.format(formatter)
    }

    fun dateStringToRealmInstant(dateString: String) : RealmInstant {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val localDate = LocalDate.parse(dateString, formatter)

        val localDateTime = LocalDateTime.of(localDate, java.time.LocalTime.MIDNIGHT)

        val instant = localDateTime.toInstant(ZoneOffset.UTC)
        return RealmInstant.from(instant.epochSecond, instant.nano)
    }

    fun formatDateTime(dateTimeAsString: String?): RealmInstant? {
        if (dateTimeAsString != null) {
            val ldt = LocalDateTime.parse(dateTimeAsString)
            return RealmInstant.from(
                ldt.toEpochSecond(
                    ZoneId.systemDefault().rules.getOffset(
                        Instant.now()
                    )
                ), ldt.nano
            )
        } else return null
    }
}
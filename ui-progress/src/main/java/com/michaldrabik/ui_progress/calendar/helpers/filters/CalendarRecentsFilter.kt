package com.michaldrabik.ui_progress.calendar.helpers.filters

import com.michaldrabik.common.extensions.toLocalZone
import com.michaldrabik.data_local.database.model.Episode
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit.DAYS
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CalendarRecentsFilter @Inject constructor() : CalendarFilter {

  override fun filter(
    now: ZonedDateTime,
    episode: Episode,
    onlyPremieres: Boolean,
  ): Boolean {
    val dateDays = episode.firstAired?.toLocalZone()?.truncatedTo(DAYS)
    val isHistory = dateDays?.isBefore(now.truncatedTo(DAYS)) == true
    val isLast3Months = dateDays?.isAfter(now.truncatedTo(DAYS).minusMonths(3)) == true
    val isPremiere = if (onlyPremieres) episode.episodeNumber == 1 else true

    return episode.seasonNumber != 0 && isPremiere && isHistory && isLast3Months
  }
}

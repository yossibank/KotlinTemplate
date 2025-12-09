package yossibank.shared.foundation.date

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char

enum class DateFullyFormat(val formatter: DateTimeFormat<LocalDateTime>) {
    Iso8601(
        LocalDateTime.Format {
            year()
            char('-')
            monthNumber()
            char('-')
            day()
            char('T')
            hour()
            char(':')
            minute()
            char(':')
            second()
        }
    ),
    Iso8601Minute(
        LocalDateTime.Format {
            year()
            char('-')
            monthNumber()
            char('-')
            day()
            char('T')
            hour()
            char(':')
            minute()
        }
    ),
    Slash(
        LocalDateTime.Format {
            year()
            char('/')
            monthNumber()
            char('/')
            day()
            char(' ')
            hour()
            char(':')
            minute()
            char(':')
            second()
        }
    ),
    SlashMinute(
        LocalDateTime.Format {
            year()
            char('/')
            monthNumber()
            char('/')
            day()
            char(' ')
            hour()
            char(':')
            minute()
        }
    ),
    Hyphen(
        LocalDateTime.Format {
            year()
            char('-')
            monthNumber()
            char('-')
            day()
            char(' ')
            hour()
            char(':')
            minute()
            char(':')
            second()
        }
    ),
    HyphenMinute(
        LocalDateTime.Format {
            year()
            char('-')
            monthNumber()
            char('-')
            day()
            char(' ')
            hour()
            char(':')
            minute()
        }
    ),
    Jp(
        LocalDateTime.Format {
            year()
            char('年')
            monthNumber()
            char('月')
            day()
            char('日')
            char(' ')
            hour()
            char('時')
            minute()
            char('分')
            second()
            char('秒')
        }
    ),
    JpMinute(
        LocalDateTime.Format {
            year()
            char('年')
            monthNumber()
            char('月')
            day()
            char('日')
            char(' ')
            hour()
            char('時')
            minute()
            char('分')
        }
    )
}

enum class DatePartiallyFormat(val formatter: DateTimeFormat<LocalDate>) {
    Slash(
        LocalDate.Format {
            year()
            char('/')
            monthNumber()
            char('/')
            day()
        }
    ),
    Hyphen(
        LocalDate.Format {
            year()
            char('-')
            monthNumber()
            char('-')
            day()
        }
    ),
    Jp(
        LocalDate.Format {
            year()
            char('年')
            monthNumber()
            char('月')
            day()
            char('日')
        }
    )
}

enum class DateFormat(val formatter: DateTimeFormat<LocalDateTime>) {
    HJp(
        LocalDateTime.Format {
            hour(Padding.NONE)
            chars("時")
        }
    ),
    HMJp(
        LocalDateTime.Format {
            hour(Padding.NONE)
            chars("時")
            minute(Padding.NONE)
            chars("分")
        }
    ),
    DJp(
        LocalDateTime.Format {
            day(Padding.NONE)
            chars("日")
        }
    ),
    MJp(
        LocalDateTime.Format {
            monthNumber(Padding.NONE)
            chars("月")
        }
    ),
    MDJp(
        LocalDateTime.Format {
            monthNumber(Padding.NONE)
            chars("月")
            day(Padding.NONE)
            chars("日")
        }
    ),
    YJp(
        LocalDateTime.Format {
            year(Padding.NONE)
            chars("年")
        }
    ),
    YMDJp(
        LocalDateTime.Format {
            year(Padding.NONE)
            chars("年")
            monthNumber(Padding.NONE)
            chars("月")
            day(Padding.NONE)
            chars("日")
        }
    ),
    MDEHmsJp(
        LocalDateTime.Format {
            monthNumber(Padding.NONE)
            chars("/")
            day(Padding.NONE)
            chars("")
            char('(')
            dayOfWeek(DayOfWeekNames("月", "火", "水", "木", "金", "土", "日"))
            char(')')
            char(' ')
            hour()
            char(':')
            minute()
            char(':')
            second()
        }
    )
}
package com.eld.besteld.utils

internal enum class DutyStatus(val rawValue: String) {
    ONDUTY(rawValue = "ONDUTY"),
    OFFDUTY(rawValue = "OFFDUTY"),
    SLEEPER(rawValue = "SLEEPER"),
    YARD(rawValue = "YARD"),
    DRIVING(rawValue = "DRIVING"),
    PERSONAL(rawValue = "PERSONAL");

    companion object {
        operator fun invoke(rawValue: String): DutyStatus? = values().firstOrNull { it.rawValue == rawValue }
    }

    // MARK: Internal
    val dutyIndex: Short
        get() {
            return when (this) {
                DutyStatus.ONDUTY -> 0
                DutyStatus.OFFDUTY -> 1
                DutyStatus.SLEEPER -> 2
                DutyStatus.YARD -> 3
                DutyStatus.DRIVING -> 4
                else -> 5
            }
        }
    val title: String
        get() {
            return when (this) {
                DutyStatus.ONDUTY -> "On Duty"
                DutyStatus.OFFDUTY -> "Off Duty"
                DutyStatus.SLEEPER -> "Sleeper"
                DutyStatus.YARD -> "Yard"
                DutyStatus.DRIVING -> "Driving"
                else -> "Personal"
            }
        }
    val shortTitle: String
        get() {
            return when (this) {
                DutyStatus.ONDUTY -> "ON"
                DutyStatus.OFFDUTY -> "OFF"
                DutyStatus.SLEEPER -> "SB"
                DutyStatus.YARD -> "Y"
                DutyStatus.DRIVING -> "D"
                else -> "P"
            }
        }
}

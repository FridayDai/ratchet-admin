package com.ratchethealth.admin

class Utility {
    static String getAutoArchiveStr (difference) {
        if (!difference) {
            return 'NA'
        }

        int differenceHours = difference / 3600000;
        int differenceDays = Math.floor(differenceHours / 24);
        int weeks = Math.floor(differenceDays / 7);
        int days = Math.floor(differenceDays % 7);

        return "${weeks}W ${days}D after surgery"
    }
}

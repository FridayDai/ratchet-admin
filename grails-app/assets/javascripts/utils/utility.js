'use strict';

module.exports = {
    replaceSlashInTitle: function (str) {
        return str.replace(/\//g, '_');
    },

    getTimeInterval: function (difference) {
        if (!_.isNumber(difference)) {
            return 0;
        }

        if (difference < 0) {
            difference = -difference;
        }

        var differenceMS = difference / 1000;
        differenceMS = differenceMS/60;
        var minutes = Math.floor(differenceMS % 60);
        differenceMS = differenceMS/60;
        var hours = Math.floor(differenceMS % 24);
        var differenceDays = Math.floor(differenceMS / 24);
        var weeks = Math.floor(differenceDays / 7);
        var days = Math.floor(differenceDays % 7);

        return {
            minutes: minutes,
            hours: hours,
            days: days,
            weeks: weeks,
            totalDays: differenceDays
        };
    }
};

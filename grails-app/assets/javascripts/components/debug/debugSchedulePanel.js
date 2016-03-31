var flight = require('flight');
var withPanel = require('../common/withPanel');

function debugSchedulePanel() {
    /* jshint validthis:true */

    this.attributes({
        debugDateSelector: '#debug-schedule',
        debugDateTimeSelector: "#debug-dataTime",
        debugRandomHourSelector: '#debug-random-hour',
        urls: {
            lastDebugTime: "/profile/debug-time",
            lastDebugDateTime: "/debug/set-time",
            lastRandomHour: "/debug/set-random-hour"
        }
    });

    this.getLastDebugTime = function () {
        var that = this;
        $.get(this.attr.urls.lastDebugTime)
            .done(function (data) {
                if (data) {
                    that.trigger('showDebugScheduleFormDialog', data);
                }
            });
    };

    this.getLastDebugDateTime = function () {
        var that = this;
        $.get(this.attr.urls.lastDebugDateTime)
            .done(function (data) {
                if (data) {
                    that.trigger('showDateTimeFormDialog', data);
                }
            });
    };

    this.getLastDebugRandomHour = function () {
        var that = this;
        $.get(this.attr.urls.lastRandomHour)
            .done(function (data) {
                if (data) {
                    that.trigger('showRandomHourFormDialog', data);
                }
            });
    };

    this.after('initialize', function () {
        this.on('click', {
            debugDateSelector: this.getLastDebugTime,
            debugDateTimeSelector: this.getLastDebugDateTime,
            debugRandomHourSelector:this.getLastDebugRandomHour
        });
    });
}

module.exports = flight.component(withPanel, debugSchedulePanel);


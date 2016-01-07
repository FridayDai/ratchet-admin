var flight = require('flight');
var withPanel = require('../common/withPanel');

function debugSchedulePanel() {
    /* jshint validthis:true */

    this.attributes({
        urls: {
            lastDebugTime: "/profile/debug-time"
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

    this.after('initialize', function () {
        this.on('click', this.getLastDebugTime);
    });
}

module.exports = flight.component(withPanel, debugSchedulePanel);


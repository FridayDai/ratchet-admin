var flight = require('flight');
var withServerError = require('../common/withServerError');

function triggerNotification() {
    /* jshint validthis:true */

    this.attributes({
        notificationBtnSelector : "#notification",
        loadingState: 'loading',
        resetState: 'reset',
        urls: {
            triggerNotification: "/profile/triggerNotification"
        }
    });

    this.triggerNotification = function () {
        var submitBtn = this.select('notificationBtnSelector');
        var that = this;

        submitBtn.button(this.attr.loadingState);

        $.post(this.attr.urls.triggerNotification)
            .done(function (data) {
                if(data.resp === true) {
                    $('.alert').show();
                    setTimeout(function () {
                        $('.alert').hide();
                    }, 2000);
                }
            })
            .fail(_.bind(that.serverErrorHandler, that))
            .always(function () {
                submitBtn.button(that.attr.resetState);
            });
    };

    this.after('initialize', function () {
        this.on('click', this.triggerNotification);
    });
}

module.exports = flight.component(withServerError, triggerNotification);


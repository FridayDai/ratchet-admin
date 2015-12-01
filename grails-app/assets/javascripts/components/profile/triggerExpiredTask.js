var flight = require('flight');
var withServerError = require('../common/withServerError');

function triggerExpiredTask() {
    /* jshint validthis:true */

    this.attributes({
        expiredTaskBtnSelector : "#expired-task",
        loadingState: 'loading',
        resetState: 'reset',
        urls: {
            triggerExpiredTask: "/profile/triggerExpired"
        }
    });

    this.triggerExpiredTask = function () {
        var submitBtn = this.select('expiredTaskBtnSelector');
        var that = this;

        submitBtn.button(this.attr.loadingState);

        $.post(this.attr.urls.triggerExpiredTask)
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
        this.on('click', this.triggerExpiredTask);
    });
}

module.exports = flight.component(withServerError, triggerExpiredTask);


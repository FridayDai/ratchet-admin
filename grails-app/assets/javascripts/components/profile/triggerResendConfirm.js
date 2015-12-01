var flight = require('flight');
var withServerError = require('../common/withServerError');

function triggerResendConfirm() {
    /* jshint validthis:true */

    this.attributes({
        resendConfirmBtnSelector : "#resend-confirmation",
        loadingState: 'loading',
        resetState: 'reset',
        urls: {
            triggerResendConfirm: "/profile/triggerResendConfirm"
        }
    });

    this.triggerResendConfirm = function () {
        var submitBtn = this.select('resendConfirmBtnSelector');
        var that = this;

        submitBtn.button(this.attr.loadingState);

        $.post(this.attr.urls.triggerResendConfirm)
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
        this.on('click', this.triggerResendConfirm);
    });
}

module.exports = flight.component(withServerError, triggerResendConfirm);


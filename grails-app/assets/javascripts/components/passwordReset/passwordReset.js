var flight = require('flight');

var WithElementValidation = require('../common/WithElementValidation');
var PasswordValidation = require('../share/validation/PasswordValidation');
var withPrimitiveForm = require('../common/withPrimitiveForm');

function passwordReset() {
    /* jshint validthis:true */

    this.attributes({
        submitBtnSelector: ".btn-reset",
        loadingState: 'Resetting password',
        originSelector: "#newPassword",
        confirmSelector: "#confirmPassword",
        errorMsgSelector: ".error-area",
        errorMsg: "Your passwords don't match. Please try again."
    });

    this.initPasswordValidation = function () {
        this.setElementValidation(
            this.select('originSelector'),
            PasswordValidation.rules
        );
    };

    this.after('initialize', function() {
        this.initPasswordValidation();
    });
}

module.exports = flight.component(WithElementValidation, passwordReset, withPrimitiveForm);

var flight = require('flight');

var WithElementValidation = require('../common/WithElementValidation');
var PasswordValidation = require('../share/validation/PasswordValidation');
var withPrimitiveForm = require('../common/withPrimitiveForm');

function accountActivate() {
    /* jshint validthis:true */

    this.attributes({
        submitBtnSelector: "#joinRat",
        codeSelector: "input[name=code]",
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

module.exports = flight.component(WithElementValidation, accountActivate, withPrimitiveForm);

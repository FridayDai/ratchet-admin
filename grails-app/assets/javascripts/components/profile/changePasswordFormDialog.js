
var flight = require('flight');
var withFormDialog = require('../common/withFormDialog');
var WithElementValidation = require('../common/WithElementValidation');
var PasswordValidation = require('../share/validation/PasswordValidation');

function changePasswordFormDialog() {
    /* jshint validthis:true */

    flight.compose.mixin(this, [
        withFormDialog
    ]);

    this.attributes({
        submitBtnSelector: '.create-btn',
        newPassWordFieldSelector: '#new-password'
    });

    this.initPasswordValidation = function () {
        this.setElementValidation(
            this.select('newPassWordFieldSelector'),
            PasswordValidation.rules
        );
    };

    this.onBeforeClose = function() {
        var $password = this.select('newPassWordFieldSelector');
        if ($password.data('tooltipsterNs')) {
            $password.tooltipster('hide');
        }
    };

    this._formSuccessProcess = function () {
        this.hideDialog();
    };

    this.after('initialize', function () {
        this.initPasswordValidation();

        this.on('formSuccess', this._formSuccessProcess);

        this.on('hidden.bs.modal', this.onBeforeClose);
    });
}

module.exports = flight.component(WithElementValidation, changePasswordFormDialog, withFormDialog);

var flight = require('flight');
var withFormDialog = require('../common/withFormDialog');

function addAccountFormDialog() {
    /* jshint validthis:true */

    this.attributes({
        submitBtnSelector: '.create-btn'
    });

    this.onFormSuccess = function (e, data) {
        this.trigger('createAccountSuccess', data);
        this.hideDialog();
    };

    this.after('initialize', function () {
        this.on('hidden.bs.modal', this.clearAllForm);
        this.on('formSuccess', this.onFormSuccess);
    });

    this.clearAllForm = function() {
        this.clearForm();
        $(".group-multiple").select2("val", "");
    };
}

module.exports = flight.component(withFormDialog, addAccountFormDialog);

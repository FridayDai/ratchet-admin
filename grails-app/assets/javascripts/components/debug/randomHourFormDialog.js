require('datetimepicker');
var flight = require('flight');
var withFormDialog = require('../common/withFormDialog');

function debugRandomHourFormDialog() {
    /* jshint validthis:true */

    flight.compose.mixin(this, [
        withFormDialog
    ]);

    this.attributes({
        submitBtnSelector: '.create-btn',
        lastDebugDateDiv: '#lastRandomHour'
    });

    this.fillLastDebugDate = function (event, data) {
        this.select('lastDebugDateDiv').text(data.randomHour);
    };

    this.formSuccessProcess = function () {
        this.hideDialog();
    };

    this.after('initialize', function () {
        this.on('formSuccess', this.formSuccessProcess);
        this.on(document, 'showRandomHourFormDialog', this.fillLastDebugDate);
    });
}

module.exports = flight.component(withFormDialog, debugRandomHourFormDialog);




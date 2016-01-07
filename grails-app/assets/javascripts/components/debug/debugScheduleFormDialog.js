
require('datepicker');
var flight = require('flight');
var withFormDialog = require('../common/withFormDialog');

function debugScheduleFormDialog() {
    /* jshint validthis:true */

    flight.compose.mixin(this, [
        withFormDialog
    ]);

    this.attributes({
        submitBtnSelector: '.create-btn',
        debugBtn: '#debug-schedule',
        lastDebugDateDiv: '#lastDebugDate',
        debugDateInput: '#debugDate'
    });

    this.fillLastDebugDate = function (event, data) {
        this.select('lastDebugDateDiv').text(data.dateForDebug);
    };

    this.formSuccessProcess = function () {
        this.hideDialog();
    };

    this._initDatePicker = function() {
        $(this.attr.debugDateInput).datepicker({
            format: "yyyy-mm-dd",
            orientation: "top left"
        });
    };

    this.initMyForm = function() {
        this._initDatePicker();
    };

    this.after('initialize', function () {
        this.initMyForm();
        this.on('formSuccess', this.formSuccessProcess);
        this.on(document, 'showDebugScheduleFormDialog', this.fillLastDebugDate);
    });
}

module.exports = flight.component(withFormDialog, debugScheduleFormDialog);


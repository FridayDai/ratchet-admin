var flight = require('flight');
var withForm = require('../common/withForm');
var withDialog = require('../common/withDialog');

function addTreatmentFormDialog () {
    /* jshint validthis:true */

    this.attributes({
        submitBtnSelector: '.create-btn',
        absoluteEventTypeSelector: '#AbsoluteEventType',
        archiveWeekSelector: '[name=archiveWeek]',
        archiveDaySelector: '[name=archiveDay]'
    });

    this.onFormSuccess = function (e, data) {
        this.trigger('createTreatmentSuccess', data);

        this.hideDialog();
    };

    //this.onAbsoluteEventTypeChange = function () {
    //    var required = this.select('absoluteEventTypeSelector').val();
    //
    //    if (required === 'DISCHARGE'){
    //        this.select('absoluteEventTypeSelector').val('DISCHARGE');
    //    }else if (required === 'APPOINTMENT'){
    //        this.select('absoluteEventTypeSelector').val('APPOINTMENT');
    //    }else if (required === 'NONE'){
    //        this.select('absoluteEventTypeSelector').val('NONE');
    //    }
    //};

    this.after('initialize', function () {
        this.on('formSuccess', this.onFormSuccess);

        //this.on('change', {
        //    absoluteEventTypeSelector: this.onAbsoluteEventTypeChange
        //});
    });
}

module.exports = flight.component(withForm, withDialog, addTreatmentFormDialog);

var flight = require('flight');
var withForm = require('../common/withForm');
var withDialog = require('../common/withDialog');

function addTreatmentFormDialog () {
    /* jshint validthis:true */

    this.attributes({
        submitBtnSelector: '.create-btn',
        surgeryTimeRequireSelector: '#surgeryTimeRequired',
        archiveWeekSelector: '[name=archiveWeek]',
        archiveDaySelector: '[name=archiveDay]'
    });

    this.onFormSuccess = function (e, data) {
        this.trigger('createTreatmentSuccess', data);

        this.hideDialog();
    };

    this.onSurgeryTimeRequiredChange = function () {
          var required = this.select('surgeryTimeRequireSelector').val();

        if (required === 'false') {
            this.select('archiveWeekSelector').val(0).prop('disabled', true);
            this.select('archiveDaySelector').val(0).prop('disabled', true);
        } else {
            this.select('archiveWeekSelector').prop('disabled', false);
            this.select('archiveDaySelector').prop('disabled', false);
        }
    };

    this.after('initialize', function () {
        this.on('formSuccess', this.onFormSuccess);

        this.on('change', {
            surgeryTimeRequireSelector: this.onSurgeryTimeRequiredChange
        });
    });
}

module.exports = flight.component(withForm, withDialog, addTreatmentFormDialog);

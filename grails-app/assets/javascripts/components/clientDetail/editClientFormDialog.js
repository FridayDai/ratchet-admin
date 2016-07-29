var flight = require('flight');
var withClientFormDialog = require('../share/withClientFormDialog');

function addClientFormDialog () {
    /* jshint validthis:true */

    this.attributes({
        submitBtnSelector: '.update-btn',

        testingFieldSelector: '#isTesting',
        clientNameSelector: '#name',
        clientShortNameSelector: '#shortName',
        clientLegalNameSelector: '#legalName',
        subDomainSelector: '#subDomain',
        portalNameSelector: '#portalName',
        primaryColorSelector: '#primaryColorHex',
        sessionTimeoutSelector: '#sessionTimeout'
    });

    this.onShow = function (event, data) {
        this.select('testingFieldSelector').prop('checked', data.isTesting);
        this.select('clientNameSelector').val(data.clientName);
        this.select('clientShortNameSelector').val(data.clientShortName);
        this.select('clientLegalNameSelector').val(data.clientLegalName);
        this.select('subDomainSelector').val(data.subDomain);
        this.select('portalNameSelector').val(data.portalName);
        this.select('primaryColorSelector').val(data.primaryColor);
        this.select('sessionTimeoutSelector').val(data.sessionTimeout);
    };

    this.onFormSuccess = function () {
        location.reload();
    };

    this.after('initialize', function () {
        this.on(document, 'showEditClientFormDialog', this.onShow);
        this.on('formSuccess', this.onFormSuccess);
    });
}

module.exports = flight.component(withClientFormDialog, addClientFormDialog);

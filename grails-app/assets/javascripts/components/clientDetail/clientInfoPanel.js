var flight = require('flight');
var withPanel = require('../common/withPanel');

function clientInfoPanel() {
    /* jshint validthis:true */

    this.attributes({
        testingSelector: '.testing strong',
        clientNameSelector: '.client-name strong',
        subDomainSelector: '.sub-domain dd',
        portalNameSelector: '.portal-name dd',
        primaryColorSelector: '.primary-color dd',
        editClientBtnSelector: '.edit button'
    });

    this.onEditClientBtnClick = function () {
        this.trigger('showEditClientFormDialog', {
            isTesting: this.get('testing') === 'true',
            clientName: this.get('clientName'),
            subDomain: this.get('subDomain'),
            portalName: this.get('portalName'),
            primaryColor: this.get('primaryColor')
        });
    };

    this.after('initialize', function () {
        this.on('click', {
            'editClientBtnSelector': this.onEditClientBtnClick
        });
    });
}

module.exports = flight.component(withPanel, clientInfoPanel);

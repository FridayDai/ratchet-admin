var flight = require('flight');
var withForm = require('../common/withForm');
var withDialog = require('../common/withDialog');


function agentFormDialog () {
    /* jshint validthis:true */

    this.attributes({
        agentEmailSelector: '#email',
        agentFirstNameSelector: '#firstName',
        agentLastNameSelector: '#lastName',
        modalTitleSelector: '.modal-title',
        submitBtnSelector: '.update-btn',

        createUrlUrl: '/clients/{0}/agents',
        edithUrlUrl: '/clients/{0}/agents/{1}'
    });

    this.onEditModal = function (event, data) {
        this.setValue(data);
        this.clientId = data.clientId;
        this.agentId = data.agentId;

        this.setEditModal();
    };

    this.setValue = function (data) {
        this.select('agentEmailSelector').val(data.agentEmail);
        this.select('agentFirstNameSelector').val(data.agentFirstName);
        this.select('agentLastNameSelector').val(data.agentLastName);
    };

    this.model = '';

    this.setEditModal = function () {
        this.model = 'edit';

        this.select('modalTitleSelector').text('Edit Agent');

        this.select('submitBtnSelector').text('Update');

        this.formEl.attr('action', this.attr.edithUrlUrl.format(this.clientId, this.agentId));
    };

    this.onCreateModal = function (event, data) {
        this.clientId = data.clientId;

        this.setCreateModal();
    };

    this.setCreateModal = function () {
        this.model = 'create';

        this.select('agentEmailSelector').removeAttr('readonly');
        this.select('modalTitleSelector').text('New Agent');

        this.select('submitBtnSelector').text('Create');

        this.formEl.attr('action', this.attr.createUrlUrl.format(this.clientId));
    };

    this.onFormSuccess = function (e, data) {
        data.agentId = data.id;
        data.agentEmail = data.email;
        data.agentFistName = data.firstName;
        data.agentLastName = data.lastName;

        this.trigger('agentInfoChanged', data);

        if (this.model === 'create') {
            this.trigger('createAgentSuccess');
        }

        this.hideDialog();
    };

    this.after('initialize', function () {
        this.on(document, 'showEditAgentFormDialog', this.onEditModal);
        this.on(document, 'showCreateAgentFormDialog', this.onCreateModal);
        this.on('formSuccess', this.onFormSuccess);
    });
}

module.exports = flight.component(withForm, withDialog, agentFormDialog);

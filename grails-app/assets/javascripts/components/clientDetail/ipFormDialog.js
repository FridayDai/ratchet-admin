var flight = require('flight');
var withForm = require('../common/withForm');
var withDialog = require('../common/withDialog');

var ANY_WHERE_IP = '0.0.0.0/0';
var IP_TYPE = {
    anywhere: 'anywhere',
    custom: 'custom'
};

var SUCCESS_EVENT_NAMES = {
    create: 'createIPForClientSuccess',
    edit: 'editIPForClientSuccess'
};

/* jshint ignore:start */
$.validator.addMethod('IP4CIDRCheck', function (value) {
    return /^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])(\/([0-9]|[1-2][0-9]|3[0-2]))$/ig.test(value);
}, 'IP using IP4 CIDR, format should be 255.255.255.255/20.');
/* jshint ignore:end */

function ipFormDialog () {
    /* jshint validthis:true */

    this.attributes({
        typeSelector: '[name=type]',
        ipSelector: '[name=ip]',
        nameSelector: '[name=name]',
        descriptionSelector: '[name=description]',
        modalTitleSelector: '.modal-title',
        submitBtnSelector: '.update-btn',

        createUrlUrl: '/clients/{0}/ips',
        edithUrlUrl: '/clients/{0}/ips/{1}'
    });

    this.onEditModal = function (event, data) {
        this.ipId = data.ip.id;
        this.setValue(data);

        this.setEditModal();

        this.showDialog();
    };

    this.setValue = function (data) {
        if (data.ip === ANY_WHERE_IP) {
            this.select('typeSelector').val(IP_TYPE.anywhere);
            this.setAnywhereIP();
        } else {
            this.select('typeSelector').val(IP_TYPE.custom);
            this.setCustomIP();
            this.select('ipSelector').val(data.ip.ip);
        }

        this.select('nameSelector').val(data.ip.name);
        this.select('descriptionSelector').val(data.ip.description);
    };

    this.model = '';

    this.setEditModal = function () {
        this.model = 'edit';

        this.select('modalTitleSelector').text('Edit IP');

        this.select('submitBtnSelector').text('Update');

        this.formEl.attr('action', this.attr.edithUrlUrl.format(this.$node.data('clientId'), this.ipId));
    };

    this.onCreateModal = function () {
        this.setCreateModal();
    };

    this.setCreateModal = function () {
        this.model = 'create';

        this.select('typeSelector').val('anywhere');
        this.setAnywhereIP();
        this.select('modalTitleSelector').text('New IP');

        this.select('submitBtnSelector').text('Create');

        this.formEl.attr('action', this.attr.createUrlUrl.format(this.$node.data('clientId')));
    };

    this.setAnywhereIP = function () {
        this.select('ipSelector').attr('readOnly', 'readOnly').val(ANY_WHERE_IP);
    };

    this.setCustomIP = function() {
        this.select('ipSelector').removeAttr('readOnly').val('');
    };

    this.onTypeSelectChange = function (e) {
        var $target = $(e.target);

        if ($target.val() === IP_TYPE.anywhere) {
            this.setAnywhereIP();
        } else {
            this.setCustomIP();
        }
    };

    this.initValidation = function () {
        this.formEl.validate({
            rules: {
                ip: {
                    IP4CIDRCheck: true
                }
            }
        });
    };

    this.onFormSuccess = function (e, data) {
        //if (this.model === 'create') {
        //    this.trigger('createIPForClientSuccess', data);
        //}

        this.trigger(SUCCESS_EVENT_NAMES[this.model], data);

        this.hideDialog();
    };

    this.after('initialize', function () {
        this.on(document, 'showEditIPFormDialog', this.onEditModal);
        this.on(document, 'showCreateIPFormDialog', this.onCreateModal);
        this.on('formSuccess', this.onFormSuccess);
        this.on('change', {
            'typeSelector': this.onTypeSelectChange
        });
    });
}

module.exports = flight.component(withForm, withDialog, ipFormDialog);

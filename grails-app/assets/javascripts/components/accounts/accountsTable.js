var flight = require('flight');
var withDataTable = require('../common/withDataTable');

function accountsTable() {
    /* jshint validthis:true */

    var accountStatus = ["Active", "Inactive"];

    this.attributes({

        url: '/getAccounts',

        columns: [
            {
                title: 'ID',
                data: 'id',
                width: '15%',
                orderable: false
            }, {
                title: 'Email Address',
                data: 'email',
                width: '30%',
                className: "email",
                orderable: false
            }, {
                title: 'Status',
                "render": function (data, type, full) {
                    var status = data ? data - 1 : full.status ? full.status - 1 : 1;
                    return accountStatus[status];
                },
                width: '15%',
                orderable: false
            }, {
                title: 'Enabled',
                data: "enabled",
                width: '15%',
                className: "isEnabled",
                orderable: false
            }, {
                title: 'isMFAEnabled',
                data: 'isMFAEnabled',
                width: '10%',
                className: 'isMFAEnabled',
                orderable: false,
                render: function(data, type, full) {
                    if(full.isMFAEnabled === 'null' || !full.isMFAEnabled) {
                        return 'false';
                    } else {
                        return full.isMFAEnabled;
                    }
                }
            }, {
                data: function (row, type, set, meta) {
                    if (meta) {
                        return '<a href="#" class="btn-edit glyphicon glyphicon-pencil" ' +
                            'data-toggle="modal" data-target="#edit-account-modal" ' +
                            'aria-hidden="true" data-row="{0}" data-account-id="{1}"></a>'
                                .format(meta.row, row.id);
                    }
                },
                orderable: false
            },
            {
                data: function (row, type, set, meta) {
                    if (meta) {
                        return '<a href="#" class="btn-remove glyphicon glyphicon-trash" ' +
                            'data-toggle="modal" data-target="#delete-account-modal" ' +
                            'aria-hidden="true" data-row="{0}" data-account-id="{1}"></a>'
                                .format(meta.row, row.id);
                    }
                },
                orderable: false
            },
            {
                title: 'Groups',
                data: 'groups',
                className: "groups",
                orderable: false
            }
        ]
    });

    this.onAccountInfoChanged = function (e, data) {
        data = data || {};
        this.$node.$ele = data.$ele;
        this.$node.$ele.find('.isEnabled').text(data.enabled);
        this.$node.$ele.find('.groups').text(data.groups);
    };

    this.onDeleteAccountSuccess = function (e, data) {
        data.$ele.remove();
    };

    this.onCreateAccountSuccess = function (e, data) {
        this.addRow(data);
    };

    this.after('initialize', function () {
        this.on(document, 'createAccountSuccess', this.onCreateAccountSuccess);
        this.on(document, 'deleteAccountSuccess', this.onDeleteAccountSuccess);
        this.on(document, 'accountInfoChanged', this.onAccountInfoChanged);
    });
}

module.exports = flight.component(withDataTable, accountsTable);

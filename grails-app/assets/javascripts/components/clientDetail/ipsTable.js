var flight = require('flight');
var withDataTable = require('../common/withDataTable');

function ipsTable() {
    /* jshint validthis:true */

    this.attributes({
        serverSide: false,

        columns: [
            {
                title: 'ID',
                data: 'id',
                width: '10%',
                orderable: false
            }, {
                title: 'IP',
                data: 'ip',
                width: '25%',
                orderable: false
            }, {
                title: 'Name',
                data: 'name',
                width: '15%',
                orderable: false
            }, {
                title: 'Description',
                data: "description",
                width: '40%',
                orderable: false
            }, {
                title: '',
                data: function (row, type, set, meta) {
                    if (meta) {
                        return [
                            '<span class="edit-btn glyphicon glyphicon-edit" ',
                            'aria-hidden="true" data-row="{0}"></span>',
                            '&nbsp;',
                            '<span class="delete-btn glyphicon glyphicon-trash" ',
                            'aria-hidden="true" data-row="{0}"></span>'
                        ].join('').format(meta.row);
                    }
                },
                width: '20px',
                orderable: false
            }
        ]
    });

    this.rowCallback = function (rawRow) {
        var me = this;

        $(rawRow)
            .find('.edit-btn')
            .click(function () {
                me.edittingRawRow = rawRow;
                me.trigger('showEditIPFormDialog', {
                    ip: me.getRowData(rawRow)
                });
            });

        $(rawRow)
            .find('.delete-btn')
            .click(function () {
                me.delettingRawRow = rawRow;
                me.trigger('showDeleteIPFormDialog', {
                    ip: me.getRowData(rawRow)
                });
            });
    };

    this.onCreateIPSuccess = function (event, data) {
        this.addRow(data);
    };

    this.onEditIPSuccess = function (event, data) {
        this.updateRow(this.edittingRawRow, data);
    };

    this.onDeleteIPSuccess = function () {
        this.deleteRow(this.delettingRawRow);
    };

    this.after('initialize', function () {
        this.on(document, 'createIPForClientSuccess', this.onCreateIPSuccess);
        this.on(document, 'editIPForClientSuccess', this.onEditIPSuccess);
        this.on(document, 'deleteIPForClientSuccess', this.onDeleteIPSuccess);
    });
}

module.exports = flight.component(withDataTable, ipsTable);

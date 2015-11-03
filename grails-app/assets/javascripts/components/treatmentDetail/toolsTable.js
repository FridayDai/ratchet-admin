require('momentTZ');

var flight = require('flight');
var utility = require('../../utils/utility');
var moment = require('moment');

var withDataTable = require('../common/withDataTable');
var treatmentStorage = require('./treatmentStorage');
var AdminPortalConstants = require('../../constants/AdminPortalConstants');

function toolsTable() {
    this.attributes({
        urlFormatStr: '/clients/{0}/treatments/{1}/tools',
        initWithLoad: true,

        columns: [
            {
                title: 'ID',
                data: 'id'
            }, {
                title: 'Tool Title',
                data: 'title'
            }, {
                title: 'Tool Description',
                data: 'description'
            }, {
                title: 'Tool Type',
                data: function (row) {
                    return AdminPortalConstants.TOOL_TYPE[row.type];
                }
            }, {
                title: 'Last Updated',
                data: function (row) {
                    return moment(row.lastUpdated)
                        .tz("America/Vancouver")
                        .format('MMM DD, YYYY  h:mm A');
                }
            }, {
                title: '',
                data: function (row, type, set, meta) {
                    return [
                        '<span class="edit-btn glyphicon glyphicon-edit" ',
                        'aria-hidden="true" data-row="{0}"></span>',
                        '&nbsp;',
                        '<span class="delete-btn glyphicon glyphicon-trash" ',
                        'aria-hidden="true" data-row="{0}"></span>'
                    ].join('').format(meta.row);
                },
                width: '20px'
            }
        ]
    });

    this.getUrl = function () {
        return this.attr.urlFormatStr
            .format(treatmentStorage.get('clientId'), treatmentStorage.get('treatmentId'));
    };

    this.edittingRawRow = null;
    this.delettingRawRow = null;

    this.rowCallback = function (rawRow, data) {
        if (_.isUndefined(data.defaultDueTimeDay) && data.defaultDueTime) {
            var duration = utility.getTimeInterval(data.defaultDueTime);

            _.extend(data, {
                defaultDueTimeDay: duration.totalDays,
                defaultDueTimeHour: duration.hours
            });
        }

        var me = this;

        $(rawRow)
            .find('.edit-btn')
            .click(function () {
                me.edittingRawRow = rawRow;
                me.trigger('showEditDefinedToolFormDialog', {
                    clientId: treatmentStorage.get('clientId'),
                    treatmentId: treatmentStorage.get('treatmentId'),
                    tool: me.getRowData(rawRow)
                });
            });

        $(rawRow)
            .find('.delete-btn')
            .click(function () {
                me.delettingRawRow = rawRow;
                me.trigger('showDeleteToolDialog', {
                    clientId: treatmentStorage.get('clientId'),
                    treatmentId: treatmentStorage.get('treatmentId'),
                    tool: me.getRowData(rawRow)
                });
            });
    };

    this.getPageSize = function () {
        return 100;
    };

    this.onCreateDefinedToolSuccess = function (event, data) {
        this.addRow(data);
    };

    this.onEdithDefinedToolSuccess = function (event, data) {
        this.updateRow(this.edittingRawRow, data);
    };

    this.onDeleteToolSuccess = function () {
        this.deleteRow(this.delettingRawRow);
    };

    this.after('initialize', function () {
        this.on(document, 'createDefinedToolSuccess', this.onCreateDefinedToolSuccess);
        this.on(document, 'editDefinedToolSuccess', this.onEdithDefinedToolSuccess);
        this.on(document, 'deleteToolSuccess', this.onDeleteToolSuccess);
    });
}

module.exports = flight.component(withDataTable, toolsTable);

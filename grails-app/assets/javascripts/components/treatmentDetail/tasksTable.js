require('momentTZ');

var flight = require('flight');
var utility = require('../../utils/utility');
var moment = require('moment');

var withDataTable = require('../common/withDataTable');
var treatmentStorage = require('./treatmentStorage');
var AdminPortalConstants = require('../../constants/AdminPortalConstants');

function tasksTable() {
    this.attributes({
        urlFormatStr: '/clients/{0}/treatments/{1}/tasks',
        initWithLoad: true,

        columns: [
            {
                title: 'ID',
                data: 'id',
                orderable: false
            }, {
                title: 'Tool Title',
                data: 'toolTitle',
                orderable: false
            }, {
                title: 'Tool Description',
                data: 'toolDescription',
                orderable: false
            }, {
                title: 'Tool Type',
                orderable: false,
                data: function (row) {
                    return AdminPortalConstants.TOOL_TYPE[row.toolType];
                }
            }, {
                title: 'Send Time',
                orderable: false,
                data: function (row) {
                    var sendTime = row.sendTimeOffset,
                        timeStr = '',
                        direction = 0,
                        duration = {};

                    if (row.immediate) {
                        timeStr = 'Immediate';
                    } else if (sendTime === 0) {
                        direction = 1;
                        timeStr = 'At Surgery';
                    } else {
                        duration = utility.getTimeInterval(sendTime);

                        timeStr = '{0}W {1}D {2}H {3}M'.format(
                            duration.weeks,
                            duration.days,
                            duration.hours,
                            duration.minutes
                        );

                        if (sendTime < 0) {
                            direction = -1;
                            timeStr += ' Before Surgery';
                        } else {
                            direction = 1;
                            timeStr += ' After Surgery';
                        }
                    }

                    _.extend(row, {
                        sendTimeDirection: 0,
                        sendTimeWeeks: 0,
                        sendTimeDays: 0,
                        sendTimeHours: 0,
                        sendTimeMinutes: 0
                    }, {
                        sendTimeDirection: direction,
                        sendTimeWeeks: duration.weeks,
                        sendTimeDays: duration.days,
                        sendTimeHours: duration.hours,
                        sendTimeMinutes: duration.minutes,
                        sendTimeStr: timeStr
                    });

                    return timeStr;
                }
            }, {
                title: 'Due Time',
                orderable: false,
                data: function (row) {
                    var dueTime = row.defaultDueTime;
                    var timeStr = '';
                    var duration = {};

                    if (dueTime === 0) {
                        timeStr = 'NA';
                    } else {
                        duration = utility.getTimeInterval(dueTime);

                        timeStr = '{0}D {1}H'.format(
                            duration.totalDays,
                            duration.hours
                        );
                    }

                    return timeStr;
                }
            },
            {
                title: 'Expire Time',
                orderable: false,
                data: function (row) {
                    var expireTime = row.defaultExpireTime;
                    var timeStr = '';
                    var expire = {};

                    if (!expireTime) {
                        timeStr = 'NA';
                    } else {
                        expire = utility.getTimeInterval(expireTime);

                        timeStr = '{0}D {1}H'.format(
                            expire.totalDays,
                            expire.hours
                        );
                    }

                    return timeStr;
                }
            },
            {
                title: 'Last Updated',
                orderable: false,
                data: function (row) {
                    return moment(row.lastUpdated).tz("America/Vancouver").format('MMM DD, YYYY  h:mm A');
                }
            },
            {
                title: '',
                orderable: false,
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

    this.rowCallback = function (rawRow) {
        var me = this;

        $(rawRow)
            .find('.edit-btn')
            .click(function () {
                me.edittingRawRow = rawRow;
                me.trigger('showEditTaskFormDialog', {
                    clientId: treatmentStorage.get('clientId'),
                    treatmentId: treatmentStorage.get('treatmentId'),
                    surgeryTimeRequire: treatmentStorage.get('surgeryTimeRequire'),
                    task: me.getRowData(rawRow)
                });
            });

        $(rawRow)
            .find('.delete-btn')
            .click(function () {
                me.delettingRawRow = rawRow;
                me.trigger('showDeleteTaskDialog', {
                    clientId: treatmentStorage.get('clientId'),
                    treatmentId: treatmentStorage.get('treatmentId'),
                    task: me.getRowData(rawRow)
                });
            });
    };

    this.getPageSize = function () {
        return 100;
    };

    this.onCreateTaskSuccess = function (event, data) {
        this.addRow(data);
    };

    this.onEdithTaskSuccess = function (event, data) {
        this.updateRow(this.edittingRawRow, data);
    };

    this.onDeleteTaskSuccess = function () {
        this.deleteRow(this.delettingRawRow);
    };

    this.onDeleteToolSuccess = function () {
        this.reload();
    };

    this.after('initialize', function () {
        this.on(document, 'createTaskSuccess', this.onCreateTaskSuccess);
        this.on(document, 'editTaskSuccess', this.onEdithTaskSuccess);
        this.on(document, 'deleteTaskSuccess', this.onDeleteTaskSuccess);
        this.on(document, 'deleteToolSuccess', this.onDeleteToolSuccess);
    });
}

module.exports = flight.component(withDataTable, tasksTable);

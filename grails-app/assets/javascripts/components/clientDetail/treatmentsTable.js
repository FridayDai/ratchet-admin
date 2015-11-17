require('momentTZ');

var flight = require('flight');
var withDataTable = require('../common/withDataTable');
var utility = require('../../utils/utility');
var moment = require('moment');

function treatmentsTable() {
    /* jshint validthis:true */

    this.attributes({
        urlFormatStr: '/clients/{0}/treatments',
        rowClickFormatStr: '/clients/{0}/treatments/{1}/{2}',

        columns: [
            {
                title: 'ID',
                data: 'id',
                width: '5%'
            }, {
                title: 'Treatment Title',
                data: 'title',
                width: '10%'
            }, {
                title: 'Template Title',
                data: 'tmpTitle',
                width: '10%'
            }, {
                title: 'Active',
                data: "activePatient",
                width: '5%'
            }, {
                title: 'Description',
                data: "description",
                width: '37%'
            }, {
                title: 'Status',
                data: "active",
                width: '8%',
                render: function (data) {
                    return data === true || data === 'true' ? 'Active' : 'Closed';
                }
            },
            {
                title: 'Last Updated',
                data: 'lastUpdated',
                width: '20%',
                render: function (data) {
                    return moment(parseInt(data, 10))
                        .tz("America/Vancouver")
                        .format('MMM DD, YYYY  h:mm A');
                }
            }, {
                title: '',
                width: '5%',
                data: function (row, type, set, meta) {
                    if (meta) {
                        return '<span class="edit-btn glyphicon glyphicon-copy" ' +
                                'aria-hidden="true" data-row="{0}"></span>'
                                .format(meta.row);
                    }
                }
            }
        ]
    });

    this.drawCallback = function (data) {
        this.$node.parent().show();
        this.trigger('activeTreatmentCountChanged',
            _.filter(data.aoData, function (t) { return t._aData.active === 'true'; }).length
        );
    };

    this.getUrl = function () {
        this.clientId =  this.$node.data('clientId');

        return this.attr.urlFormatStr.format(this.clientId);
    };

    this.getRowClickUrl = function (data) {
        return this.attr.rowClickFormatStr.format(
            this.clientId,
            data.id,
            utility.replaceSlashInTitle(data.title + '_' + data.tmpTitle)
        );
    };

    this.onCreateTreatmentSuccess = function (event, data) {
        this.addRow(data);
    };

    this.after('initialize', function () {
        this.on(document, 'createTreatmentSuccess', this.onCreateTreatmentSuccess);
    });
}

module.exports = flight.component(withDataTable, treatmentsTable);

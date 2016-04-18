var flight = require('flight');
var withDataTable = require('../common/withDataTable');

function BasicToolTable() {
    /* jshint validthis:true */

    this.attributes({
        url: '/basic_tool',

        columns: [
            {
                title: 'ID',
                data: 'id',
                width: '10%',
                searchable: false,
                orderable: false
            }, {
                title: 'Title',
                data: 'title',
                width: '30%',
                orderable: false
            }, {
                title: 'Description',
                data: 'description',
                width: '45%',
                searchable: false,
                orderable: false
            }, {
                title: 'Testing',
                data: 'isTesting',
                width: '10%',
                searchable: false,
                orderable: false
            }
        ]
    });

    this.onRowClick = function (data) {
        this.trigger('showEditBasicToolFormDialog', data);
    };

    this.rowCallback = function (rawRow, data) {
        if (_.isUndefined(data.basicToolTitle)) {
            _.extend(data, {
                basicToolTitle: $(rawRow).data('questionnaireTitle')
            });
        }

        if (_.isUndefined(data.basicToolContent)) {
            _.extend(data, {
                basicToolContent: $(rawRow).data('questionnaireContent')
            });
        }

        if (data.isTesting === 'false') {
            data.isTesting = false;
        }
    };

    this.onCreateClientSuccess = function (e, data) {
        this.addRow(data);
    };

    this.after('initialize', function () {
        this.on(document, 'createBasicToolSuccess', this.onCreateClientSuccess);
    });
}

module.exports = flight.component(withDataTable, BasicToolTable);

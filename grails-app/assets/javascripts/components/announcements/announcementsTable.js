var flight = require('flight');
var withDataTable = require('../common/withDataTable');

function announcementsTable() {
    /* jshint validthis:true */

    var ANNOUNCEMENT_COLOR = {
        "#fdddde": "Red"
    };

    var ANNOUNCEMENT_STATUS = {
        "2": "Inactive",
        "1": "Active",
        "0": "undefined"
    };

    var ANNOUNCEMENT_STATUS_REVERSE = {
        "Inactive": "2",
        "Active": "1"
    };

    this.attributes({
        url: '/getAnnouncements',
        announceDeleteBtn: 'td span.remove-btn',

        columns: [
            {
                title: 'ID',
                data: 'id',
                width: '5%',
                orderable: false
            },
            {
                title: 'Announcement',
                sClass: 'announce-content',
                data: 'content',
                width: '55%',
                orderable: false
            },
            {
                title: 'Status',
                data: 'status',
                width: '15%',
                sClass: 'announce-status',
                orderable: false,
                render: function (data) {
                    return ANNOUNCEMENT_STATUS[data];
                }
            },
            {
                title: 'Background',
                data: 'colorHex',
                width: '15%',
                orderable: false,
                render: function (data) {
                    return ANNOUNCEMENT_COLOR[data];
                }
            },
            {
                title: '',
                orderable: false,
                data: function (data) {

                    if(+data.status === +ANNOUNCEMENT_STATUS_REVERSE.Inactive) {
                        return [
                            '<span class="edit-btn glyphicon glyphicon-edit hide" ',
                            'aria-hidden="true"></span>',
                            '&nbsp;',
                            '<span class="remove-btn glyphicon glyphicon-trash hide" ',
                            'aria-hidden="true" data-toggle="modal" ',
                            'data-target="#announcement-delete-modal" data-announce-id="'+ data.id +'"></span>'
                        ].join('');
                    }
                    else {
                        return [
                            '<span class="edit-btn glyphicon glyphicon-edit hide" ',
                            'aria-hidden="true"></span>',
                            '&nbsp;',
                            '<span class="remove-btn glyphicon glyphicon-trash" ',
                            'aria-hidden="true" data-toggle="modal" ',
                            'data-target="#announcement-delete-modal" data-announce-id="'+ data.id +'"></span>'
                        ].join('');
                    }

                },
                width: '20px'
            }
        ]
    });

    this.onClear = function (event, data) {
        if(data){
            $(data).find('.announce-status').text(ANNOUNCEMENT_STATUS[2]);
            $(data).find('.remove-btn').remove();
        }
    };

    this.onAddRow = function(event,data) {
        this.addRow(data);
    };

    this.after('initialize', function () {
        this.on(document, 'createAnnouncementSuccess', this.onAddRow);
        this.on(document, 'deleteAnnouncementSuccess', this.onClear);
    });
}

module.exports = flight.component(withDataTable, announcementsTable);

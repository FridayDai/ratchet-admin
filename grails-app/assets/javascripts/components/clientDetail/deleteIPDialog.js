var flight = require('flight');
var withDialog = require('../common/withDialog');
var withServerError = require('../common/withServerError');

function deleteIPDialog () {
    /* jshint validthis:true */

    this.attributes({
        submitBtnSelector: '.delete-btn',
        idLabelSelector: 'dd.id',
        ipLabelSelector: 'dd.ip',
        nameLabelSelector: 'dd.name',
        descriptionLabelSelector: 'dd.description',

        loadingState: 'loading',
        resetState: 'reset',

        deleteIPUrl: '/clients/{0}/ips/{1}'
    });

    this.onSubmit = function () {
        var submitBtn = this.select('submitBtnSelector');
        var that = this;

        submitBtn.button(this.attr.loadingState);

        $.ajax({
                url: that.attr.deleteIPUrl.format(that.clientId, that.ipId),
                type: 'DELETE'
            })
            .done(function () {
                that.trigger('deleteIPForClientSuccess');

                that.hideDialog();
            })
            .fail(_.bind(that.serverErrorHandler, that))
            .always(function () {
                submitBtn.button(that.attr.resetState);
            });
    };

    this.onShow = function (event, data) {
        this.ipId = data.ip.id;
        this.clientId = this.$node.data('clientId');

        this.select('idLabelSelector').text(data.ip.id);
        this.select('ipLabelSelector').text(data.ip.ip);
        this.select('nameLabelSelector').text(data.ip.name);
        this.select('descriptionLabelSelector').text(data.ip.description);

        this.showDialog();
    };

    this.after('initialize', function () {
        this.on('click', {
            'submitBtnSelector': this.onSubmit
        });

        this.on(document, 'showDeleteIPFormDialog', this.onShow);
    });
}

module.exports = flight.component(withDialog, withServerError, deleteIPDialog);

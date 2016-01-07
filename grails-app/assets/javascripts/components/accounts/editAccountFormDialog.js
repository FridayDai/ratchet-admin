var flight = require('flight');
var withFormDialog = require('../common/withFormDialog');

function editAccountFormDialog() {
    /* jshint validthis:true */

    this.attributes({
        accountEmailSelector: '#account-email',
        isEnabledSelector: '#isEnabled',
        submitBtnSelector: '.update-btn',

        loadingState: 'Updating',
        updateAccount: '/accounts/{0}/update'
    });

    this.onEditModal = function (event) {
        $(".group-multiple").select2("val", "");
        var editBtnEle = $(event.relatedTarget);
        this.accountId = editBtnEle.data('accountId');
        this.$ele = editBtnEle.closest('tr');
        var data = {
            isEnabled: this.$ele.find('.isEnabled').text(),
            accountEmail: this.$ele.find('.email').text(),
            accountGroup: this.$ele.find('.groups').text()
        };
        this.setValue(data);

        this.formEl.attr('action', this.attr.updateAccount.format(this.accountId));
    };

    this.setValue = function (data) {
        this.select('accountEmailSelector').val(data.accountEmail);

        if (data.isEnabled === "true") {
            this.select('isEnabledSelector').prop("checked", true);
        } else {
            this.select('isEnabledSelector').prop("checked", false);
        }

        if(data.accountGroup) {
            var groups = data.accountGroup.split(",");

            $.each(groups, function(i){
                $('#account-edit-groups > option[value="' + groups[i] + '"]').prop("selected","selected");
                $("#account-edit-groups").trigger("change");
            });
        }
    };

    this.onFormSuccess = function (e, data) {
        data.$ele = this.$ele;
        data.enabled = data.enabled;
        data.groups = data.groups;

        this.trigger('accountInfoChanged', data);

        this.hideDialog();
    };

    this.after('initialize', function () {
        this.on(document, 'show.bs.modal', this.onEditModal);
        this.on('formSuccess', this.onFormSuccess);
    });
}

module.exports = flight.component(withFormDialog, editAccountFormDialog);

require('jquery');
require('bootstrap');
require('../components/common/initSetup');


var ChangePasswordFormDialog = require('../components/profile/changePasswordFormDialog'),
    DebugScheduleFormDialog = require('../components/profile/debugScheduleFormDialog'),
    DebugSchedulePanel = require('../components/profile/debugSchedulePanel'),
    TriggerResendConfirm = require('../components/profile/triggerResendConfirm'),
    TriggerNotification = require('../components/profile/triggerNotification'),
    TriggerExpiredTask = require('../components/profile/triggerExpiredTask');

ChangePasswordFormDialog.attachTo('#change-password-modal');
DebugScheduleFormDialog.attachTo('#change-time-modal');
DebugSchedulePanel.attachTo('#debug-schedule');
TriggerResendConfirm.attachTo('#resend-confirmation');
TriggerNotification.attachTo('#notification');
TriggerExpiredTask.attachTo('#expired-task');

require('jquery');
require('bootstrap');
require('../components/common/initSetup');


var DebugScheduleFormDialog = require('../components/debug/debugScheduleFormDialog'),
    DebugSchedulePanel = require('../components/debug/debugSchedulePanel'),
    TriggerResendConfirm = require('../components/debug/triggerResendConfirm');

DebugScheduleFormDialog.attachTo('#change-time-modal');
DebugSchedulePanel.attachTo('#debug-schedule');
TriggerResendConfirm.attachTo('#resend-confirmation');

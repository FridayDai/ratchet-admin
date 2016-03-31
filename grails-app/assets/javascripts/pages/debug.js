require('jquery');
require('bootstrap');
require('../components/common/initSetup');


var DebugScheduleFormDialog = require('../components/debug/debugScheduleFormDialog'),
    DebugDateTimeFormDialog = require('../components/debug/debugDateTimeFormDialog'),
    DebugSchedulePanel = require('../components/debug/debugSchedulePanel'),
    DebugRandomHourFormDialog = require('../components/debug/randomHourFormDialog'),
    TriggerResendConfirm = require('../components/debug/triggerResendConfirm');

DebugSchedulePanel.attachTo('#btn-group-panel');
DebugScheduleFormDialog.attachTo('#change-time-modal');
DebugDateTimeFormDialog.attachTo('#change-dateTime-modal');
DebugRandomHourFormDialog.attachTo('#random-hour-modal');
TriggerResendConfirm.attachTo('#resend-confirmation');

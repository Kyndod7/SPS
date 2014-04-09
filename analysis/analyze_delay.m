%% INITIALIZE

clear all;
close all;
fclose all;
clc;

% initialize
push_service = 'gcm';
fig1_title = 'App Server to Email Proxy';
fig2_title = 'Email Proxy to Smartphone';
fig3_title = 'Smartphone-App Server Sync';
fig4_title = 'GCM';
steps_figs_scale = [0, 10];
ete_fig_scale = [0, 20];

start_dir = 'C:\Users\Erich\Google Drive\UCF\Thesis\data';

% UI to select log files
[server_filename, server_pathname] = uigetfile('.log', 'Select server log file', start_dir);
[app_filename, app_pathname] = uigetfile('.log', 'Select app log file', server_pathname);

%% PROCESS LOGS

% parse server and app logs
log = func_parse_server_client_logs([server_pathname server_filename], [app_pathname app_filename]);

% app server to push service
step1_delta = [log.notification_sent] - [log.sending_notification];
% push service to app
step2_delta = [log.notification_received] - [log.notification_sent];
% synching data
step3_delta = [log.sync_data_received] - [log.sync_request_sent];
% begining to end
ete_delta = [log.sync_data_received] - [log.sending_notification];

%% FIGURES

fig1 = figure;
plot(step1_delta, 'b.-', 'LineWidth', 2, 'MarkerSize', 15);
ylim(steps_figs_scale);
grid on;
title(fig1_title);
xlabel('Trials');
ylabel('Delay (sec)');
set(gcf, 'Units', 'Inches', 'Position', [1, 3, 3, 3], 'PaperUnits', 'Inches', 'PaperSize', [3, 3])
set(fig1, 'PaperPosition', [0 0 3 3]);
saveas(fig1, [push_service '_delay_step1'], 'png')
display(sprintf('Step 1 average = %f', mean(step1_delta)));

fig2 = figure;
plot(step2_delta, 'b.-', 'LineWidth', 2, 'MarkerSize', 15);
ylim(steps_figs_scale);
grid on;
title(fig2_title);
xlabel('Trials');
ylabel('Delay (sec)');
set(gcf, 'Units', 'Inches', 'Position', [4, 3, 3, 3], 'PaperUnits', 'Inches', 'PaperSize', [3, 3])
set(fig2, 'PaperPosition', [0 0 3 3]);
saveas(fig2, [push_service '_delay_step2'], 'png');
display(sprintf('Step 2 average = %f', mean(step2_delta)));

fig3 = figure;
plot(step3_delta, 'b.-', 'LineWidth', 2, 'MarkerSize', 15);
ylim(steps_figs_scale);
grid on;
title(fig3_title);
xlabel('Trials');
ylabel('Delay (sec)');
set(gcf, 'Units', 'Inches', 'Position', [7, 3, 3, 3], 'PaperUnits', 'Inches', 'PaperSize', [3, 3])
set(fig3, 'PaperPosition', [0 0 3 3]);
saveas(fig3, [push_service '_delay_step3'], 'png');
display(sprintf('Step 3 average = %f', mean(step3_delta)));

fig4 = figure;
plot(ete_delta, 'b.-', 'LineWidth', 2, 'MarkerSize', 15);
ylim(ete_fig_scale);
grid on;
title(fig4_title);
xlabel('Trials');
ylabel('Delay (sec)');
set(gcf, 'Units', 'Inches', 'Position', [10, 3, 3, 3], 'PaperUnits', 'Inches', 'PaperSize', [3, 3])
set(fig4, 'PaperPosition', [0 0 3 3]);
saveas(fig4, [push_service '_delay'], 'png');
display(sprintf('ETE = %f', mean(ete_delta)));
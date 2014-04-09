clear all;
close all;
fclose all;
clc;

start_dir = 'C:\Users\Erich\Google Drive\UCF\Thesis\data';

% UI to select log files
[filename, pathname] = uigetfile('.csv', 'Seltect battery log', start_dir);

% parse logs
battery_log = func_parse_battery_hd_log([pathname filename]);

% normalize battery charge scale
norm_battery_log = func_normalize_battery_charge(battery_log);

fig = figure;
hold on;
plot(norm_battery_log.time, norm_battery_log.charge, 'bx--', 'LineWidth', 2, 'MarkerSize', 10);
datetick('x','HH:MM') 
% flushes x axis
grid on;
title('Battery Charge');
xlabel('Time (hours)');
ylabel('Battery Charge (%)');
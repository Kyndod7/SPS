clear all;
close all;
fclose all;
clc;

start_dir = 'C:\Users\Erich\Google Drive\UCF\Thesis\data';
plot_title = 'Business User with Good Reception';
y_lim = [0, 70];

% UI to select log files
[sps_filename, sps_pathname] = uigetfile('.csv', 'Select SPS battery log', start_dir);
[gcm_filename, gcm_pathname] = uigetfile('.csv', 'Select GCM battery log', start_dir);

% parse logs
sps = func_parse_battery_hd_log([sps_pathname sps_filename]);
gcm = func_parse_battery_hd_log([gcm_pathname gcm_filename]);

% normalize battery charge scale
sps = func_normalize_battery_charge(sps);
gcm = func_normalize_battery_charge(gcm);

% sync x axis
[xmin, xmax] = func_normalize_battery_time(sps, gcm, 3);

fig = figure;
hold on;
plot(sps.time, sps.charge, 'bx--', 'LineWidth', 2, 'MarkerSize', 10);
plot(gcm.time, gcm.charge, 'r.-', 'LineWidth', 2, 'MarkerSize', 15);
% flushes x axis
xlim([xmin, xmax]);
% sets y axis
ylim(y_lim);
datetick('x','HH:MM', 'keeplimits') 
grid on;
title(plot_title);
xlabel('Time (hours)');
ylabel('State of Charge (%)');
legend({'SPS', 'GCM'}, 'Location', 'NorthWest');
set(gcf, 'Units', 'Inches', 'Position', [10, 3, 3, 3], 'PaperUnits', 'Inches', 'PaperSize', [3, 3])
%set(fig, 'PaperPosition', [0 0 3 3]);
%saveas(fig, 'sps_vs_gcm', 'png');

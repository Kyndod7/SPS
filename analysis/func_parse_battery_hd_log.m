function [ log ] = func_parse_battery_hd_log( filename )
%UNTITLED Summary of this function goes here
%   Detailed explanation goes here

fid = fopen(filename, 'r');
raw_data = textscan(fid, '%s%s%s%s%s%s', 'Delimiter', ',');
fclose(fid);

% sort data
log.time = raw_data{1};
log.charge = raw_data{2};
log.voltage = raw_data{3};
log.temperature = raw_data{4};
log.plugged = raw_data{5};
log.screen = raw_data{6};

% remove header
log.time(1) = [];
log.charge(1) = [];
log.voltage(1) = [];
log.temperature(1) = [];
log.plugged(1) = [];
log.screen(1) = [];

% convert raw data format
log.time = datenum(log.time, 'yyyy/mm/dd HH:MM:SS');
log.charge = str2double(log.charge);
log.voltage = str2double(log.voltage);
log.temperature = str2double(log.temperature);

end

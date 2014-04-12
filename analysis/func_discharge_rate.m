function [discharge_rate_hour] = func_discharge_rate(filename)

log = func_log_parser(filename);

delta_charge = log.charge(1) - log.charge(end);
delta_time_sec = ( log.time(end) - log.time(1) ) * 100000;
delta_time_hour = delta_time_sec / 3600;

discharge_rate_hour = delta_charge / delta_time_hour;

end

function [x_min, x_max] = func_normalize_battery_time(input_a, input_b, max_hours)
%UNTITLED3 Summary of this function goes here
%   Detailed explanation goes here

max_hours = max_hours/10;

x_min = min(min(input_a.time), min(input_b.time));
x_max = min(max(input_a.time), max(input_b.time));

if (x_max > max_hours)
    x_max = max_hours;
end

end


function [ output ] = func_normalize_battery_charge(input)

% set charge independent of battery charge
output.charge = input.charge - min(input.charge);
output.charge = abs(output.charge - max(output.charge));
output.time = input.time - min(input.time);

end
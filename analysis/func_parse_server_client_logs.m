function [ output ] = func_parse_server_client_logs(server_filename, app_filename)
%UNTITLED4 Summary of this function goes here
%   Detailed explanation goes here

%% parse server log

fid = fopen(server_filename, 'r');
server_raw_data = textscan(fid, '%s%s%s%s%s%s%s', 'Delimiter', ',');
fclose(fid);

% sort data
% convert timestamp from milliseconds to seconds
server_log.timestamp =  str2double(server_raw_data{2})/1000;
server_log.id =  str2double(server_raw_data{5});
server_log.event = server_raw_data{6};

for idx = 1 : length(server_raw_data{1})
    % extract data from line
    id = server_log.id(idx);
    timestamp = server_log.timestamp(idx);
    event = server_log.event(idx);
    
    output(id).id = id;
    
    if(strcmp(event, 'SENDING_NOTIFICATION'))
        output(id).sending_notification = timestamp;
    elseif(strcmp(event, 'NOTIFICATION_SENT'))
        output(id).notification_sent = timestamp;
    elseif(strcmp(event, 'SYNC_REQUEST_RECEIVED'))
        output(id).sync_request_received = timestamp;
    elseif(strcmp(event, 'SYNC_DATA_SENT'))
        output(id).sync_data_sent = timestamp;
    end
end

%% parse app log

fid = fopen(app_filename, 'r');
app_raw_data = textscan(fid, '%s%s%s%s%s%s%s', 'Delimiter', ',');
fclose(fid);

% sort data
app_log.timestamp =  str2double(app_raw_data{2})/1000;
app_log.id =  str2double(app_raw_data{5});
app_log.event = app_raw_data{6};

for idx = 1 : length(app_raw_data{1})
    id = app_log.id(idx);
    if(~isnan(id))
        % extract data from line
        timestamp = app_log.timestamp(idx);
        event = app_log.event(idx);

        output(id).id = id;

        if(strcmp(event, 'NOTIFICATION_RECEIVED'))
            output(id).notification_received = timestamp;
        elseif(strcmp(event, 'SYNC_REQUEST_SENT'))
            output(id).sync_request_sent = timestamp;
        elseif(strcmp(event, 'SYNC_DATA_RECEIVED'))
            output(id).sync_data_received = timestamp;
        end
    end
end

end
